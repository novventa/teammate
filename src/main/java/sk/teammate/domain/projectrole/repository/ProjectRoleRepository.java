package sk.teammate.domain.projectrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Project;
import sk.teammate.entity.ProjectRole;

import java.util.List;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
    List<ProjectRole> findByProjectAndDeletedAtIsNull(Project project);
}
