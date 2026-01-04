package com.example.backend.notice.entity;

import com.example.backend.notice.enums.NoticeAttachmentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "notice_attachments",
        indexes = {
                @Index(name = "idx_notice_attachments_notice_id", columnList = "notice_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NoticeAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 소속 공지
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NoticeAttachmentType type; // IMAGE / FILE

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "file_url", nullable = false, length = 2048)
    private String fileUrl;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Column(name = "content_type", length = 100)
    private String contentType;

    /* package-private */
    void setNotice(Notice notice) {
        this.notice = notice;
    }
}
