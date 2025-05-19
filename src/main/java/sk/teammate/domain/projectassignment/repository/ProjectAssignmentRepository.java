package sk.teammate.domain.projectassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Project;
import sk.teammate.entity.ProjectAssignment;

import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Long> {

    List<ProjectAssignment> findByProject(Project project);

}