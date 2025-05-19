package sk.teammate.domain.skill.dto;

import sk.teammate.entity.Skill;

public record SkillResponse(
        Long id,
        String name
) {
    public static SkillResponse from(Skill skill) {
        return new SkillResponse(skill.getId(), skill.getName());
    }
}
