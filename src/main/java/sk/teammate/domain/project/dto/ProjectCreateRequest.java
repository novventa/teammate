package sk.teammate.domain.project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectCreateRequest(
        @NotBlank(message = "프로젝트명을 입력해주세요.")
        String title,

        String description,
        String objectives,

        @NotNull(message = "예산을 입력해주세요.")
        @Min(value = 0, message = "예산은 0 이상이어야 합니다.")
        Long budget,

        @NotNull(message = "예상 기간을 입력해주세요.")
        Integer expectedDurationMonths
) {}
