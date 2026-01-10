package com.example.backend.profile.service;

import com.example.backend.member.dto.MemberSnsLinkRequest;
import com.example.backend.member.dto.MemberSnsLinkResponse;
import com.example.backend.member.entity.User;
import com.example.backend.member.entity.UserSnsLink;
import com.example.backend.member.entity.UserTechStack;
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
    private final PasswordEncoder passwordEncoder;

    /**
     * 내 프로필 조회
     */
    @Transactional(readOnly = true)
    public MyProfileResponse getMyProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        List<String> techStacks = userTechStackRepository.findByUserId(userId)
                .stream()
                .map(UserTechStack::getStackName)
                .collect(Collectors.toList());

        List<UserSnsLink> snsLinkList = userSnsLinkRepository.findByUserId(userId);

        return new MyProfileResponse(
                user.getId(),
                user.getName(),
                user.getRole(),
                user.getGeneration(),
                user.getPart(),
                user.getStudentId(),
                user.getDepartment(),
                user.getProfile() != null ? user.getProfile().getProfileImageUrl() : null,
                user.getProfile() != null ? user.getProfile().getBio() : null,
                techStacks,
                snsLinkList.stream()
                        .map(link -> new MemberSnsLinkResponse(link.getType(), link.getUrl()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * 내 프로필 수정 (bio, 기술스택, SNS)
     */
    public void updateMyProfile(Long userId, MyProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        // ⭐ User 정보 업데이트 (필요한 경우)
        if (request.getName() != null) {
            user.updateName(request.getName());
        }
        if (request.getPart() != null) {
            user.updatePart(request.getPart());
        }
        if (request.getGeneration() > 0) {
            user.updateGeneration(request.getGeneration());
        }
        if (request.getRole() != null) {
            user.updateRole(request.getRole());
        }
        if (request.getDepartment() != null) {
            user.updateDepartment(request.getDepartment());
        }
        if (request.getStudentId() != null) {
            user.updateStudentId(request.getStudentId());
        }
        // generation, role은 보통 수정 불가능하므로 제외

        // User 기준으로 우선 조회해 동일 영속성 그래프 유지
        Profile profile = user.getProfile();

        if (profile == null) {
            // 혹시 DB에 이미 존재하는 프로필이 있으면 재연결
            profile = profileRepository.findByUser(user).orElse(null);
            if (profile != null) {
                user.setProfile(profile);
                if (profile.getUser() == null) {
                    profile.setUser(user);
                }
            }
        }

        // 그래도 없으면 새 프로필을 User 기준으로 생성
        if (profile == null) {
            profile = new Profile(user);
            profile.setUser(user);
            user.setProfile(profile);
        }

        if (request.getBio() != null) {
            profile.updateBio(request.getBio());
        }

        // 새로 생성된 경우에만 저장; 기존 영속 엔티티는 dirty checking으로 처리
        if (profile.getUserId() == null || !profileRepository.existsById(profile.getUserId())) {
            profileRepository.save(profile);
        }

        // 기술 스택 전체 교체
        userTechStackRepository.deleteByUserId(userId);

        if (request.getTechStacks() != null && !request.getTechStacks().isEmpty()) {
            List<UserTechStack> techStacks = request.getTechStacks().stream()
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .map(stackName -> new UserTechStack(user, stackName))
                    .collect(Collectors.toList());
            if (!techStacks.isEmpty()) {
                userTechStackRepository.saveAll(techStacks);
            }
        }

        // SNS 링크 수정
        if (request.getSnsLinks() != null) {
            userSnsLinkRepository.deleteByUserId(userId);
            List<UserSnsLink> newSnsLinks = toUserSnsLinks(user, request.getSnsLinks());
            if (!newSnsLinks.isEmpty()) {
                userSnsLinkRepository.saveAll(newSnsLinks);
            }
        }
    }

    public MyProfileResponse updateMyProfileWithResponse(Long userId, MyProfileRequest request) {
        updateMyProfile(userId, request);
        return getMyProfile(userId);
    }

    private List<UserSnsLink> toUserSnsLinks(User user, List<MemberSnsLinkRequest> snsLinks) {
        return snsLinks.stream()
                .map(link -> UserSnsLink.create(user, link.getType(), link.getUrl()))
                .collect(Collectors.toList());
    }

    /**
     * 프로필 이미지 수정
     */
    public ProfileImageUrlResponse updateProfileImage(Long userId, String imageUrl) {

        if (imageUrl == null || imageUrl.isBlank()) {
            throw new ProfileException(ProfileErrorCode.INVALID_IMAGE_URL);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        Profile profile = user.getProfile();

        if (profile == null) {
            profile = profileRepository.findByUser(user).orElse(null);
            if (profile != null) {
                user.setProfile(profile);
                if (profile.getUser() == null) {
                    profile.setUser(user);
                }
            }
        }

        if (profile == null) {
            profile = new Profile(user);
            profile.setUser(user);
            user.setProfile(profile);
        }

        profile.updateProfileImage(imageUrl);

        if (profile.getUserId() == null || !profileRepository.existsById(profile.getUserId())) {
            profileRepository.save(profile);
        }

        return new ProfileImageUrlResponse(imageUrl);
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(Long userId, PasswordUpdateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND));

        validateNewPassword(request.getNewPassword());

        // 새 비밀번호가 기존 비밀번호와 일치한지 체크
        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            throw new ProfileException(ProfileErrorCode.SAME_AS_CURRENT_PASSWORD);
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
    }

    private void validateNewPassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new ProfileException(ProfileErrorCode.INVALID_NEW_PASSWORD);
        }
        boolean hasMinLength = newPassword.length() >= 8;
        boolean hasSpecial = newPassword.matches(".*[!@#$%^&*()\\-_=+\\[{\\]}|;:'\",<.>/?].*");
        if (!hasMinLength || !hasSpecial) {
            throw new ProfileException(ProfileErrorCode.INVALID_NEW_PASSWORD);
        }
    }
}