package sk.teammate.domain.projectrole.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRoleCreateRequest(
        @NotNull(message = "역할 ID는 필수입니다.")
        Long roleId,

        @NotBlank(message = "필요 기술 정보를 입력해주세요.")
        String requiredSkills
) {}
