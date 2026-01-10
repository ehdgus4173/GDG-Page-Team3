package com.example.backend.profile.service;

import com.example.backend.member.entity.TechStack;
import com.example.backend.member.entity.User;
import com.example.backend.member.entity.UserSnsLink;
import com.example.backend.member.entity.UserTechStack;
import com.example.backend.member.repository.TechStackRepository;
import com.example.backend.member.repository.UserRepository;
import com.example.backend.profile.dto.*;
import com.example.backend.profile.entity.Profile;
import com.example.backend.profile.exception.ProfileErrorCode;
import com.example.backend.profile.exception.ProfileException;
import com.example.backend.profile.repository.ProfileRepository;
import com.example.backend.profile.repository.UserSnsLinkRepository;
import com.example.backend.profile.repository.UserTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final UserTechStackRepository userTechStackRepository;
    private final UserSnsLinkRepository userSnsLinkRepository;
    private final TechStackRepository techStackRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 내 프로필 조회
     */
    @Transactional(readOnly = true)
    public MyProfileResponse getMyProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        List<TechStackResponse> techStacks = userTechStackRepository.findByUserId(userId)
                .stream()
                .map(userTechStack -> {
                    TechStack tech = userTechStack.getTechStack();
                    return new TechStackResponse(
                            tech.getId(),
                            tech.getName(),
                            tech.getIconUrl()
                    );
                })
                .collect(Collectors.toList());

        List<UserSnsLink> snsLinkList = userSnsLinkRepository.findByUserId(userId);

        return new MyProfileResponse(
                profile.getUserId(),
                user.getName(),
                user.getGeneration(),
                user.getPart(),
                profile.getProfileImageUrl(),
                profile.getBio(),
                techStacks,
                snsLinkList.isEmpty() ? null : UserSnsLink.toSnsLinks(snsLinkList)
        );
    }

    /**
     * 내 프로필 수정 (bio, 기술스택, SNS)
     */
    public void updateMyProfile(Long userId, MyProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        // bio 수정
        profile.updateBio(request.getBio());

        // 기술 스택 전체 교체
        userTechStackRepository.deleteByUserId(userId);

        if (request.getTechStacks() != null && !request.getTechStacks().isEmpty()) {
            List<UserTechStack> techStacks = request.getTechStacks().stream()
                    .map(techStackId -> {
                        TechStack techStack = techStackRepository.findById(Long.parseLong(techStackId))
                                .orElseThrow(() -> new ProfileException(ProfileErrorCode.TECH_STACK_NOT_FOUND));
                        return new UserTechStack(user, techStack);
                    })
                    .collect(Collectors.toList());
            userTechStackRepository.saveAll(techStacks);
        }

        // SNS 링크 수정
        if (request.getSnsLinks() != null) {
            userSnsLinkRepository.deleteByUserId(userId);

            List<UserSnsLink> newSnsLinks = UserSnsLink.fromSnsLinks(user, request.getSnsLinks());
            if (!newSnsLinks.isEmpty()) {
                userSnsLinkRepository.saveAll(newSnsLinks);
            }
        }
    }

    /**
     * 프로필 이미지 수정
     */
    public ProfileImageUrlResponse updateProfileImage(Long userId, String imageUrl) {

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        profile.updateProfileImage(imageUrl);

        return new ProfileImageUrlResponse(imageUrl);
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(Long userId, PasswordUpdateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new ProfileException(ProfileErrorCode.INVALID_CURRENT_PASSWORD);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            throw new ProfileException(ProfileErrorCode.SAME_AS_CURRENT_PASSWORD);
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
    }
}