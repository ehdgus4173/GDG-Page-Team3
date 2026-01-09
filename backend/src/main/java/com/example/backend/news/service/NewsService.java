package com.example.backend.news.service;

import com.example.backend.news.dto.CreateNewsRequest;
import com.example.backend.news.dto.NewsDetailResponse;
import com.example.backend.news.dto.NewsSummaryResponse;
import com.example.backend.news.entity.News;
import com.example.backend.news.entity.NewsFile;
import com.example.backend.global.user.UserContext;
import com.example.backend.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import com.example.backend.news.exception.NewsErrorCode;
import com.example.backend.news.exception.NewsException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserContext userContext; // 임시서비스 교체 지점

    public List<NewsSummaryResponse> getNewsList(Integer generation, int page, int size) {
        if (generation != null && generation < 0) {
            throw new NewsException(NewsErrorCode.INVALID_GENERATION_FILTER);
        }
        if (page < 0) {
            throw new NewsException(NewsErrorCode.INVALID_PAGE);
        }
        if (size < 1) { // Max size policy is optional
            throw new NewsException(NewsErrorCode.INVALID_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<News> newsPage;

        if (generation == null) {
            newsPage = newsRepository.findAll(pageable);
        } else {
            newsPage = newsRepository.findAllByGeneration(generation, pageable);
        }

        return newsPage.stream()
                .map(this::toSummary)
                .toList();
    }

    @Transactional
    public NewsDetailResponse getNews(Long id) {
        News news = newsRepository.findWithFilesById(id)
                .orElseThrow(() -> new NewsException(NewsErrorCode.NEWS_NOT_FOUND));

        // 조회수 증가 (엔티티 메서드 사용)
        news.increaseViewCount();

        return toDetail(news);
    }

    @Transactional
    public Long createNews(CreateNewsRequest request) {
        int generation = userContext.currentGeneration();
        Long authorId = userContext.currentUserId();

        if (authorId == null || authorId <= 0) {
            throw new NewsException(NewsErrorCode.AUTHOR_ID_INVALID);
        }

        if (request.title() == null || request.title().isBlank()) {
            throw new NewsException(NewsErrorCode.TITLE_REQUIRED);
        }
        if (request.title().length() > 200) {
            throw new NewsException(NewsErrorCode.TITLE_TOO_LONG);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new NewsException(NewsErrorCode.CONTENT_REQUIRED);
        }

        String thumbnailUrl = null;
        if (request.files() != null) {
            thumbnailUrl = request.files().stream()
                    .filter(f -> f.fileType() == com.example.backend.news.enums.NewsFileType.IMAGE)
                    .map(com.example.backend.news.dto.CreateNewsRequest.FileDto::fileUrl)
                    .findFirst()
                    .orElse(null);
        }

        News news = News.builder()
                .title(request.title())
                .content(request.content())
                .thumbnailUrl(thumbnailUrl)
                .generation(generation)
                .authorId(authorId)
                .viewCount(0)
                .build();

        if (request.files() != null) {
            request.files().forEach(fileDto -> {
                if (fileDto.fileUrl() == null || fileDto.fileUrl().isBlank()) {
                    throw new NewsException(NewsErrorCode.INVALID_FILE_URL);
                }
                if (fileDto.fileType() == null) {
                    throw new NewsException(NewsErrorCode.INVALID_FILE_TYPE);
                }

                NewsFile file = NewsFile.builder()
                        .fileUrl(fileDto.fileUrl())
                        .fileType(fileDto.fileType())
                        .news(news)
                        .build();
                news.getFiles().add(file);
            });
        }

        return newsRepository.save(news).getId();
    }

    private NewsSummaryResponse toSummary(News news) {
        // NOTE: DTO 시그니처는 “네가 업데이트한 버전”에 맞춰 필드명/타입만 맞추면 됨
        // 현재 업로드된 DTO는 generation이 Integer (NewsSummaryResponse.java L5–10)
        return new NewsSummaryResponse(
                news.getId(),
                news.getTitle(),
                news.getThumbnailUrl(),
                news.getCreatedAt(),
                news.getGeneration());
    }

    private NewsDetailResponse toDetail(News news) {
        List<NewsDetailResponse.FileDto> files = news.getFiles().stream()
                .map(this::toFileDto)
                .toList();

        // 현재 업로드된 DTO는 views 필드명 사용 (NewsDetailResponse.java L9–17)
        return new NewsDetailResponse(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getCreatedAt(),
                news.getViewCount(),
                news.getThumbnailUrl(),
                news.getGeneration(),
                files,
                news.getAuthorId());
    }

    private NewsDetailResponse.FileDto toFileDto(NewsFile f) {
        return new NewsDetailResponse.FileDto(
                f.getId(),
                f.getFileUrl(),
                f.getFileType());
    }
}
