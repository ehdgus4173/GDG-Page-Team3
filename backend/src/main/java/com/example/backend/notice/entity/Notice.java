package com.example.backend.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // BIGINT (PK)

    @Column(nullable = false, length = 200)
    private String title; // VARCHAR(200)

    @Lob
    @Column(nullable = false)
    private String content; // TEXT

    @Column(name = "author_id", nullable = false)
    private Long authorId; // BIGINT (FK) - 작성자

    @Column(name = "view_count", nullable = false)
    private int viewCount; // INT

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // TIMESTAMP

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // TIMESTAMP

    @OneToMany(
            mappedBy = "notice",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<NoticeFile> files = new ArrayList<>(); // notice_files

    public void increaseViewCount() {
        this.viewCount++;
    }
}
