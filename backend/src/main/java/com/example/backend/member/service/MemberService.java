package com.example.backend.member.service;

import com.example.backend.member.dto.MemberDetailResponse;
import com.example.backend.member.dto.MemberListResponse;
import com.example.backend.member.entity.User;
import com.example.backend.member.exception.MemberErrorCode;
import com.example.backend.member.exception.MemberException;
import com.example.backend.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final UserRepository userRepository;

    /**
     * 6.1 멤버 목록 조회
     * - 기수(generation), 파트(part) 필터링
     */
    public List<MemberListResponse> getMemberList(Integer generation, String part) {
        List<User> users;

        if (generation != null && part != null) {
            users = userRepository.findByGenerationAndPart(generation, part);
        } else if (generation != null) {
            users = userRepository.findByGeneration(generation);
        } else if (part != null) {
            users = userRepository.findByPart(part);
        } else {
            users = userRepository.findAll();
        }

        if (users.isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_LIST_EMPTY);
        }

        return users.stream()
                .map(user -> {
                    // Profile 정보 가져오기 (null 체크)
                    var profile = user.getProfile();
                    return new MemberListResponse(
                            user.getId(),
                            user.getName(),
                            user.getRole(),
                            user.getPart(),
                            user.getDepartment(),
                            profile != null ? profile.getBio() : null,
                            user.getGeneration(),
                            profile != null ? profile.getProfileImageUrl() : null,
                            profile != null ? profile.getTechStacks() : null
                    );
                })
                .toList();
    }

    /**
     * 6.2 멤버 상세 조회
     */
    public MemberDetailResponse getMemberDetail(Long memberId) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        var profile = user.getProfile();

        return new MemberDetailResponse(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getGeneration(),
                user.getPart(),
                profile != null ? profile.getProfileImageUrl() : null,
                profile != null ? profile.getBio() : null,
                profile != null ? profile.getTechStacks() : null,
                profile != null ? profile.getSnsLinks() : null
        );
    }
}

