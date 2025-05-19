package sk.teammate.domain.experience.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ExperienceCreateRequest(
        @NotBlank(message = "경력 제목은 필수입니다.")
        String title,

        @NotBlank(message = "회사명은 필수입니다.")
        String companyName,

        @Min(value = 1, message = "근무 기간은 1개월 이상이어야 합니다.")
        int durationMonths,

        String summary
) {}
