package sk.teammate.domain.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Project;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findAllByDeletedAtIsNull(Pageable pageable);

    Optional<Project> findByIdAndDeletedAtIsNull(Long id);

}
