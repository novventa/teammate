package sk.teammate.domain.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sk.teammate.domain.project.dto.ProjectCreateRequest;
import sk.teammate.domain.project.dto.ProjectResponse;
import sk.teammate.domain.project.service.ProjectService;
import sk.teammate.entity.Project;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<Page<ProjectResponse>> getAll(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(projectService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<ProjectResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> create(
            @AuthenticationPrincipal String creatorId,
            @RequestBody @Valid ProjectCreateRequest request) {
        projectService.create(creatorId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam Project.Status status) {
        projectService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
