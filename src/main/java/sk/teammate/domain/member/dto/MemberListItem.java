package sk.teammate.domain.member.dto;

import sk.teammate.entity.Member;

public record MemberListItem(
        String id,
        String name,
        String department,
        String position,
        Member.MemberStatus status
) {
    public static MemberListItem from(Member member) {
        return new MemberListItem(
                member.getId(),
                member.getName(),
                member.getDepartment(),
                member.getPosition(),
                member.getStatus()
        );
    }
}
