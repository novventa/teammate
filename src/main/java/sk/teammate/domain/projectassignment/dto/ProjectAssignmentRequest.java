package sk.teammate.domain.projectassignment.dto;

public record ProjectAssignmentRequest(
        String memberId,
        Long roleId,
        boolean assignedByAi
) {}
