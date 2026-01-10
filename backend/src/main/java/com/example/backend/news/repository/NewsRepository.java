package com.example.backend.news.repository;

import com.example.backend.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    @EntityGraph(attributePaths = "files")
    Optional<News> findWithFilesById(Long id);

    Page<News> findAllByGeneration(int generation, Pageable pageable);

    @Override
    Page<News> findAll(Pageable pageable);

    // 이전 글 (현재 ID보다 작으면서 가장 큰 ID)
    Optional<News> findFirstByGenerationAndIdLessThanOrderByIdDesc(int generation, Long id);

    // 다음 글 (현재 ID보다 크면서 가장 작은 ID)
    Optional<News> findFirstByGenerationAndIdGreaterThanOrderByIdAsc(int generation, Long id);
}
