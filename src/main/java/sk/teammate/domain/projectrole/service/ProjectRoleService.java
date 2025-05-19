package sk.teammate.domain.projectrole.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.teammate.domain.project.repository.ProjectRepository;
import sk.teammate.domain.projectrole.dto.ProjectRoleCreateRequest;
import sk.teammate.domain.projectrole.dto.ProjectRoleResponse;
import sk.teammate.domain.projectrole.repository.ProjectRoleRepository;
import sk.teammate.domain.role.repository.RoleRepository;
import sk.teammate.entity.Project;
import sk.teammate.entity.ProjectRole;
import sk.teammate.entity.Role;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectRoleService {

    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public List<ProjectRoleResponse> getRolesByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));

        return projectRoleRepository.findByProjectAndDeletedAtIsNull(project).stream()
                .map(ProjectRoleResponse::from)
                .toList();
    }

    @Transactional
    public void addRoleToProject(Long projectId, ProjectRoleCreateRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));

        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new EntityNotFoundException("역할을 찾을 수 없습니다."));

        ProjectRole projectRole = ProjectRole.builder()
                .project(project)
                .role(role)
                .requiredSkills(request.requiredSkills())
                .build();

        projectRoleRepository.save(projectRole);
    }

    @Transactional
    public void delete(Long projectRoleId) {
        ProjectRole pr = projectRoleRepository.findById(projectRoleId)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트 역할을 찾을 수 없습니다."));
        pr.markDeleted();
    }
}
