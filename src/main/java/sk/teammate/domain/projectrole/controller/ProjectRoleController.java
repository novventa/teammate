package sk.teammate.domain.projectrole.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.teammate.domain.projectrole.dto.ProjectRoleCreateRequest;
import sk.teammate.domain.projectrole.dto.ProjectRoleResponse;
import sk.teammate.domain.projectrole.service.ProjectRoleService;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/roles")
@RequiredArgsConstructor
public class ProjectRoleController {

    private final ProjectRoleService projectRoleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<ProjectRoleResponse>> getAll(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectRoleService.getRolesByProject(projectId));
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> add(
            @PathVariable Long projectId,
            @RequestBody @Valid ProjectRoleCreateRequest request
    ) {
        projectRoleService.addRoleToProject(projectId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectRoleId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long projectRoleId) {
        projectRoleService.delete(projectRoleId);
        return ResponseEntity.noContent().build();
    }
}
