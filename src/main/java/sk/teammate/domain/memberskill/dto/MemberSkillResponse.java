package sk.teammate.domain.memberskill.dto;

import sk.teammate.entity.MemberSkill;
import sk.teammate.entity.Skill;

public record MemberSkillResponse(
        Long id,
        String skillName,
        MemberSkill.ProficiencyLevel proficiencyLevel,
        int yearsOfExperience
) {
    public static MemberSkillResponse from(MemberSkill memberSkill) {
        Skill skill = memberSkill.getSkill();
        return new MemberSkillResponse(
                memberSkill.getId(),
                skill.getName(),
                memberSkill.getProficiencyLevel(),
                memberSkill.getYearsOfExperience()
        );
    }
}