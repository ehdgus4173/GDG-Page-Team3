package com.example.backend.common;

import com.example.backend.member.enums.SnsType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SnsLinks {

    @Pattern(regexp = "^https://github\\.com/.*", message = "올바른 GitHub URL 형식이 아닙니다")
    private String github;

    private String blog;

    @Pattern(regexp = "^https://(www\\.)?linkedin\\.com/.*", message = "올바른 LinkedIn URL 형식이 아닙니다")
    private String linkedin;

    @Pattern(regexp = "^https://(www\\.)?instagram\\.com/.*", message = "올바른 Instagram URL 형식이 아닙니다")
    private String instagram;

    @Pattern(regexp = "^https://(www\\.)?behance\\.net/.*", message = "올바른 Behance URL 형식이 아닙니다")
    private String behance;

    private String etc;
}
