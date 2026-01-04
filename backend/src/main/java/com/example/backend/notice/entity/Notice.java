package com.example.backend.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "notices",
        indexes = {
                @Index(name = "idx_notices_created_at", columnList = "created_at DESC")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private long viewCount = 0L;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp")
    private OffsetDateTime updatedAt;

    /**
     * 4.3 공지 작성 - 사진/파일 첨부
     */
    @OneToMany(
            mappedBy = "notice",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<NoticeAttachment> attachments = new ArrayList<>();

    public void increaseViewCount() {
        this.viewCount += 1;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addAttachment(NoticeAttachment attachment) {
        attachments.add(attachment);
        attachment.setNotice(this);
    }

    public void removeAttachment(NoticeAttachment attachment) {
        attachments.remove(attachment);
        attachment.setNotice(null);
    }
}
