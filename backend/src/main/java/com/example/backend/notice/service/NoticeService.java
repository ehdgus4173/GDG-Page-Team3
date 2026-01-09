package com.example.backend.notice.service;

import com.example.backend.global.user.UserContext;
import com.example.backend.notice.dto.CreateNoticeRequest;
import com.example.backend.notice.dto.NoticeDetailResponse;
import com.example.backend.notice.dto.NoticeSummaryResponse;
import com.example.backend.notice.entity.Notice;
import com.example.backend.notice.entity.NoticeFile;
import com.example.backend.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import com.example.backend.notice.exception.NoticeErrorCode;
import com.example.backend.notice.exception.NoticeException;
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
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserContext userContext;

    public List<NoticeSummaryResponse> getNoticeList(int page, int size) {
        if (page < 0) {
            throw new NoticeException(NoticeErrorCode.INVALID_PAGE);
        }
        if (size < 1) { // Max size policy is optional
            throw new NoticeException(NoticeErrorCode.INVALID_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Notice> notices = noticeRepository.findAll(pageable);

        return notices.stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    @Transactional
    public NoticeDetailResponse getNotice(Long id) {
        Notice notice = noticeRepository.findWithFilesById(id)
                .orElseThrow(() -> new NoticeException(NoticeErrorCode.NOTICE_NOT_FOUND));

        notice.increaseViewCount();

        return toDetailResponse(notice);
    }

    @Transactional
    public Long createNotice(CreateNoticeRequest request) {
        Long authorId = userContext.currentUserId();

        if (authorId == null || authorId <= 0) {
            throw new NoticeException(NoticeErrorCode.AUTHOR_ID_INVALID);
        }

        if (request.title() == null || request.title().isBlank()) {
            throw new NoticeException(NoticeErrorCode.TITLE_REQUIRED);
        }
        if (request.title().length() > 200) {
            throw new NoticeException(NoticeErrorCode.TITLE_TOO_LONG);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new NoticeException(NoticeErrorCode.CONTENT_REQUIRED);
        }

        String thumbnailUrl = null;
        if (request.files() != null) {
            thumbnailUrl = request.files().stream()
                    .filter(f -> f.fileType() == com.example.backend.notice.enums.NoticeFileType.IMAGE)
                    .map(com.example.backend.notice.dto.CreateNoticeRequest.FileDto::fileUrl)
                    .findFirst()
                    .orElse(null);
        }

        Notice notice = Notice.builder()
                .title(request.title())
                .content(request.content())
                .thumbnailUrl(thumbnailUrl)
                .authorId(authorId)
                .viewCount(0)
                .build();

        if (request.files() != null) {
            request.files().forEach(fileDto -> {
                if (fileDto.fileUrl() == null || fileDto.fileUrl().isBlank()) {
                    throw new NoticeException(NoticeErrorCode.INVALID_FILE_URL);
                }
                if (fileDto.fileType() == null) {
                    throw new NoticeException(NoticeErrorCode.INVALID_FILE_TYPE);
                }

                NoticeFile file = NoticeFile.builder()
                        .fileUrl(fileDto.fileUrl())
                        .fileType(fileDto.fileType())
                        .notice(notice)
                        .build();
                notice.getFiles().add(file);
            });
        }

        Notice saved = noticeRepository.save(notice);
        return saved.getId();
    }

    private NoticeSummaryResponse toSummaryResponse(Notice notice) {
        return new NoticeSummaryResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getThumbnailUrl(),
                notice.getViewCount(),
                notice.getCreatedAt());
    }

    private NoticeDetailResponse toDetailResponse(Notice notice) {
        List<NoticeDetailResponse.FileDto> files = notice.getFiles().stream()
                .map(this::toFileDto)
                .toList();

        return new NoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getViewCount(),
                notice.getCreatedAt(),
                files,
                notice.getAuthorId());
    }

    private NoticeDetailResponse.FileDto toFileDto(NoticeFile file) {
        // NoticeFile: id, fileUrl, fileType 구조
        return new NoticeDetailResponse.FileDto(
                file.getId(),
                file.getFileUrl(),
                file.getFileType());
    }
}
