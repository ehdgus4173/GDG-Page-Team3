package com.example.backend.news.entity;

import com.example.backend.news.enums.NewsFileType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NewsFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Column(name = "file_url", nullable = false, length = 255)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false, length = 50)
    private NewsFileType fileType;

    void setNews(News news) {
        this.news = news;
    }
}
