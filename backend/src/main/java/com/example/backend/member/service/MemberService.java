package com.example.backend.member.service;

import com.example.backend.member.dto.MemberDetailResponse;
import com.example.backend.member.dto.MemberListResponse;
import com.example.backend.member.dto.MemberSnsLinkResponse;
import com.example.backend.member.entity.User;
import com.example.backend.member.entity.UserTechStack;
import com.example.backend.member.exception.MemberErrorCode;
import com.example.backend.member.exception.MemberException;
import com.example.backend.member.repository.UserRepository;
import com.example.backend.profile.repository.UserSnsLinkRepository;
import com.example.backend.profile.repository.UserTechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.backend.member.support.PartNormalizer.normalize;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final UserRepository userRepository;
    private final UserSnsLinkRepository userSnsLinkRepository;
    private final UserTechStackRepository userTechStackRepository;

    /**
     * 6.1 멤버 목록 조회
     * - 기수(generation), 파트(part) 필터링
     */
    public List<MemberListResponse> getMemberList(Integer generation, String part, Integer page) {
        // 프론트는 1 기반 페이지를 전달하므로 0 기반으로 보정
        int pageIndex = (page != null && page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(pageIndex, 16);

        String trimmedPart = part != null ? part.trim() : null;

        List<String> partCandidates = new ArrayList<>();
        if (trimmedPart != null && !trimmedPart.isEmpty()) {
            partCandidates.add(trimmedPart.toLowerCase(Locale.ROOT));
            String canonical = normalize(trimmedPart).toLowerCase(Locale.ROOT);
            if (!canonical.equals(trimmedPart.toLowerCase(Locale.ROOT))) {
                partCandidates.add(canonical);
            }
        }
        boolean partsEmpty = partCandidates.isEmpty();

        Page<User> userPage;
        userPage = userRepository.searchMembers(generation, partCandidates, partsEmpty, pageable);

        if (userPage.isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_LIST_EMPTY);
        }

        return userPage.stream()
                .map(user -> {
                    var profile = user.getProfile();
                    List<String> techStackNames = getTechStackNames(user.getId());
                    return new MemberListResponse(
                            user.getId(),                                         // userId
                            profile != null ? profile.getUserId() : null,        // profileId
                            user.getName(),
                            user.getRole(),
                            user.getPart(),
                            user.getDepartment(),
                            profile != null ? profile.getBio() : null,
                            user.getGeneration(),
                            profile != null ? profile.getProfileImageUrl() : null,
                            techStackNames
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
        Long profileId = profile != null ? profile.getUserId() : null;
        List<String> techStackNames = getTechStackNames(memberId);
        var snsLinks = userSnsLinkRepository.findByUserId(memberId).stream()
                .map(link -> new MemberSnsLinkResponse(link.getType(), link.getUrl()))
                .toList();

        return new MemberDetailResponse(
                profileId,
                user.getName(),
                user.getRole(),
                user.getGeneration(),
                user.getPart(),
                user.getStudentId(),
                user.getDepartment(),
                profile != null ? profile.getProfileImageUrl() : null,
                profile != null ? profile.getBio() : null,
                techStackNames,
                snsLinks
        );
    }

    private List<String> getTechStackNames(Long userId) {
        return userTechStackRepository.findByUserId(userId).stream()
                .map(UserTechStack::getStackName)
                .toList();
    }
}

