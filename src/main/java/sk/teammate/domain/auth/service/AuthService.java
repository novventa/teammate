package sk.teammate.domain.auth.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.teammate.domain.auth.TokenProvider;
import sk.teammate.domain.auth.dto.LoginResponse;
import sk.teammate.domain.auth.repository.AuthTokenRepository;
import sk.teammate.domain.member.repository.MemberRepository;
import sk.teammate.domain.member.service.MemberService;
import sk.teammate.entity.AuthToken;
import sk.teammate.entity.Member;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthTokenRepository authTokenRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    public LoginResponse login(String id, String password) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid member ID"));

        // 비밀번호 해싱 없이 문자열 비교
        if (!password.equals(member.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken(member);

        AuthToken authToken = AuthToken.builder()
                .member(member)
                .refreshToken(refreshToken)
                .expiresAt(LocalDateTime.now().plusWeeks(2))
                .createdAt(LocalDateTime.now())
                .build();

        authTokenRepository.save(authToken);
        memberService.updateLastLoginAt(id);

        return new LoginResponse(accessToken, refreshToken, member.getRole());
    }

    public void logout(String memberId) {
        authTokenRepository.deleteByMemberId(memberId);
    }

    public LoginResponse refreshToken(String refreshToken) {
        Claims claims = tokenProvider.validateRefreshToken(refreshToken);
        String memberId = claims.getSubject();

        AuthToken authToken = authTokenRepository.findAll().stream()
                .filter(token -> token.getMember().getId().equals(memberId) && token.getRefreshToken().equals(refreshToken))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("유효하지 않은 리프레시 토큰입니다."));

        if (authToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("리프레시 토큰이 만료되었습니다.");
        }

        Member member = authToken.getMember();
        String newAccessToken = tokenProvider.createAccessToken(member);

        return new LoginResponse(newAccessToken, refreshToken, member.getRole());
    }

}
