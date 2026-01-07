package com.example.backend.notice.entity;

import com.example.backend.notice.enums.NoticeFileType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NoticeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // BIGINT (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice; // BIGINT (FK)

    @Column(name = "file_url", nullable = false, length = 255)
    private String fileUrl; // VARCHAR(255)

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false, length = 50)
    private NoticeFileType fileType; // VARCHAR(50)

    void setNotice(Notice notice) {
        this.notice = notice;
    }
}
