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

    // 이전 글 (현재 ID보다 작으면서 가장 큰 ID)
    Optional<Notice> findFirstByIdLessThanOrderByIdDesc(Long id);

    // 다음 글 (현재 ID보다 크면서 가장 작은 ID)
    Optional<Notice> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
