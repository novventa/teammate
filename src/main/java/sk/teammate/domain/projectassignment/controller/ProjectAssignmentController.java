package sk.teammate.domain.projectassignment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.teammate.domain.projectassignment.dto.ProjectAssignmentRequest;
import sk.teammate.domain.projectassignment.dto.ProjectAssignmentResponse;
import sk.teammate.domain.projectassignment.service.ProjectAssignmentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/assignments")
@RequiredArgsConstructor
public class ProjectAssignmentController {

    private final ProjectAssignmentService projectAssignmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<ProjectAssignmentResponse>> getAssignments(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectAssignmentService.getAssignments(projectId));
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> assign(
            @PathVariable Long projectId,
            @AuthenticationPrincipal String assignerId,
            @RequestBody @Valid ProjectAssignmentRequest request
    ) {
        projectAssignmentService.assign(projectId, assignerId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{assignmentId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> unassign(@PathVariable Long assignmentId) {
        projectAssignmentService.unassign(assignmentId);
        return ResponseEntity.noContent().build();
    }
}
