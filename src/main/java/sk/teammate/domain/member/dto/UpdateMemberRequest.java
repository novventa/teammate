package sk.teammate.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import sk.teammate.entity.Member;

public record UpdateMemberRequest(

        @NotBlank(message = "직급을 입력해주세요.")
        String position,

        @NotBlank(message = "소개를 입력해주세요.")
        String bio,

        @NotNull(message = "상태를 지정해주세요.")
        Member.MemberStatus status
) {}