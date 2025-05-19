package sk.teammate.domain.skill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);

    List<Skill> findAllByDeletedAtIsNull();

    Optional<Skill> findByIdAndDeletedAtIsNull(Long id);
}
