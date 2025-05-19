package sk.teammate.domain.experience.dto;

import sk.teammate.entity.Experience;

public record ExperienceResponse(
        Long id,
        String title,
        String companyName,
        int durationMonths,
        String summary
) {
    public static ExperienceResponse from(Experience experience) {
        return new ExperienceResponse(
                experience.getId(),
                experience.getTitle(),
                experience.getCompanyName(),
                experience.getDurationMonths(),
                experience.getSummary()
        );
    }
}
