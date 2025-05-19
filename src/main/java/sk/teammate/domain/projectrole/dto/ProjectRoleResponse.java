package sk.teammate.domain.projectrole.dto;

import sk.teammate.entity.ProjectRole;

public record ProjectRoleResponse(
        Long id,
        String roleName,
        String description,
        String requiredSkills
) {
    public static ProjectRoleResponse from(ProjectRole projectRole) {
        return new ProjectRoleResponse(
                projectRole.getId(),
                projectRole.getRole().getName(),
                projectRole.getRole().getDescription(),
                projectRole.getRequiredSkills()
        );
    }
}
