package com.example.backend.member.entity;

import com.example.backend.common.SnsLinks;
import com.example.backend.member.enums.SnsType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_sns_links")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSnsLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SnsType type;

    @Column(nullable = false)
    private String url;

    // 생성 메서드
    public static UserSnsLink create(User user, SnsType type, String url) {
        UserSnsLink snsLink = new UserSnsLink();
        snsLink.user = user;
        snsLink.type = type;
        snsLink.url = url;
        return snsLink;
    }

    // 여러 개의 UserSnsLink를 SnsLinks DTO로 변환
    public static SnsLinks toSnsLinks(List<UserSnsLink> userSnsLinks) {
        String github = null;
        String blog = null;
        String linkedin = null;
        String instagram = null;
        String behance = null;
        String etc = null;

        for (UserSnsLink link : userSnsLinks) {
            switch (link.getType()) {
                case GITHUB -> github = link.getUrl();
                case BLOG -> blog = link.getUrl();
                case LINKEDIN -> linkedin = link.getUrl();
                case INSTAGRAM -> instagram = link.getUrl();
                case BEHANCE -> behance = link.getUrl();
                case ETC -> etc = link.getUrl();
            }
        }

        return new SnsLinks(github, blog, linkedin, instagram, behance, etc);
    }

    // SnsLinks DTO를 UserSnsLink 엔티티 리스트로 변환
    public static List<UserSnsLink> fromSnsLinks(User user, SnsLinks snsLinks) {
        return List.of(
                        snsLinks.getGithub() != null ? create(user, SnsType.GITHUB, snsLinks.getGithub()) : null,
                        snsLinks.getBlog() != null ? create(user, SnsType.BLOG, snsLinks.getBlog()) : null,
                        snsLinks.getLinkedin() != null ? create(user, SnsType.LINKEDIN, snsLinks.getLinkedin()) : null,
                        snsLinks.getInstagram() != null ? create(user, SnsType.INSTAGRAM, snsLinks.getInstagram()) : null,
                        snsLinks.getBehance() != null ? create(user, SnsType.BEHANCE, snsLinks.getBehance()) : null,
                        snsLinks.getEtc() != null ? create(user, SnsType.ETC, snsLinks.getEtc()) : null
                ).stream()
                .filter(link -> link != null)
                .collect(Collectors.toList());
    }
}