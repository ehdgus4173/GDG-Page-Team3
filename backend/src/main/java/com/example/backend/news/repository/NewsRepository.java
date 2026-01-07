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
}
