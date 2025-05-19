package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 512, nullable = false)
    private String refreshToken;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;

    @Builder
    public AuthToken(Member member, String refreshToken, LocalDateTime expiresAt, LocalDateTime createdAt) {
        this.member = member;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
    }
}
