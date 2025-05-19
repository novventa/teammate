package sk.teammate.domain.member.dto;

import sk.teammate.entity.Member;

import java.time.LocalDate;

public record MemberResponse(
        String id,
        String name,
        String email,
        String department,
        String position,
        String bio,
        Member.MemberStatus status,
        Member.Role role,
        LocalDate joinDate
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getDepartment(),
                member.getPosition(),
                member.getBio(),
                member.getStatus(),
                member.getRole(),
                member.getJoinDate()
        );
    }
}