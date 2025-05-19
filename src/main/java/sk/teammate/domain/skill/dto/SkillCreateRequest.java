package sk.teammate.domain.skill.dto;

import jakarta.validation.constraints.NotBlank;

public record SkillCreateRequest(
        @NotBlank(message = "스킬 이름은 필수입니다.")
        String name
) {}