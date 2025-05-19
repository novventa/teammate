package sk.teammate.domain.experience.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Experience;
import sk.teammate.entity.Member;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByMemberAndDeletedAtIsNull(Member member);
}