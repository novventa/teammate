package sk.teammate.domain.project.dto;

import sk.teammate.entity.Project;

import java.time.LocalDate;

public record ProjectResponse(
        Long id,
        String title,
        String description,
        String objectives,
        Project.Status status,
        Long budget,
        Integer expectedDurationMonths,
        LocalDate startDate,
        LocalDate endDate,
        String createdBy
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getObjectives(),
                project.getStatus(),
                project.getBudget(),
                project.getExpectedDurationMonths(),
                project.getStartDate(),
                project.getEndDate(),
                project.getCreatedBy().getId()
        );
    }
}
