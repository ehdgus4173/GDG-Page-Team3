package com.example.backend.notice.repository;

import com.example.backend.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @EntityGraph(attributePaths = "files")
    Optional<Notice> findWithFilesById(Long id);

    @Override
    Page<Notice> findAll(Pageable pageable);
}
