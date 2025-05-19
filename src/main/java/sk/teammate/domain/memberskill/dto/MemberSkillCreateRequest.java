package sk.teammate.domain.memberskill.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import sk.teammate.entity.MemberSkill;

public record MemberSkillCreateRequest(
        @NotNull(message = "스킬 ID는 필수입니다.")
        Long skillId,

        @NotNull(message = "숙련도는 필수입니다.")
        MemberSkill.ProficiencyLevel proficiencyLevel,

        @Min(value = 0, message = "경력은 0년 이상이어야 합니다.")
        int yearsOfExperience
) {}