package sk.teammate.domain.auth.dto;

import sk.teammate.entity.Member;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Member.Role role
) {}