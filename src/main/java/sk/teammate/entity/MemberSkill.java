package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_skills")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberSkill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private ProficiencyLevel proficiencyLevel;

    private int yearsOfExperience;

    public enum ProficiencyLevel {
        BEGINNER,
        INTERMEDIATE,
        EXPERT
    }

    @Builder
    public MemberSkill(Member member, Skill skill, ProficiencyLevel proficiencyLevel, int yearsOfExperience) {
        this.member = member;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
        this.yearsOfExperience = yearsOfExperience;
    }

    public void updateProficiency(ProficiencyLevel level, int years) {
        this.proficiencyLevel = level;
        this.yearsOfExperience = years;
    }
}
