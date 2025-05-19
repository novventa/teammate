package sk.teammate.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sk.teammate.entity.Member;

public record MemberCreateRequest(

        @NotBlank(message = "사번을 입력해주세요.")
        String id,

        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "유효한 이메일 형식이어야 합니다.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotNull(message = "역할을 지정해주세요.")
        Member.Role role,

        String department,
        String position,
        String bio
) {}
