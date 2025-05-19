package sk.teammate.domain.memberskill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.teammate.entity.Member;
import sk.teammate.entity.MemberSkill;
import sk.teammate.entity.Skill;

import java.util.List;

public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {

    List<MemberSkill> findByMember(Member member);

    boolean existsByMemberAndSkill(Member member, Skill skill);

}