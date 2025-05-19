package sk.teammate.domain.projectassignment.dto;

import sk.teammate.entity.ProjectAssignment;

import java.time.LocalDateTime;

public record ProjectAssignmentResponse(
        Long id,
        String memberId,
        String memberName,
        String roleName,
        boolean assignedByAi,
        LocalDateTime assignedAt
) {
    public static ProjectAssignmentResponse from(ProjectAssignment assignment) {
        return new ProjectAssignmentResponse(
                assignment.getId(),
                assignment.getMember().getId(),
                assignment.getMember().getName(),
                assignment.getRole().getName(),
                assignment.isAssignedByAi(),
                assignment.getAssignedAt()
        );
    }
}
