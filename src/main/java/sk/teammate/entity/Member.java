package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    private String id; // 사번

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String department;
    private String position;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDate joinDate;
    private String bio;
    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberSkill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @Builder
    public Member(String id, String name, String email, String passwordHash, Role role,
                  String department, String position, MemberStatus status,
                  LocalDate joinDate, String bio, LocalDateTime lastLoginAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.department = department;
        this.position = position;
        this.status = status;
        this.joinDate = joinDate;
        this.bio = bio;
        this.lastLoginAt = lastLoginAt;
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updatePosition(String position) {
        this.position = position;
    }

    public void updateStatus(MemberStatus status) {
        this.status = status;
    }

    public void updateLastLoginAt(LocalDateTime time) {
        this.lastLoginAt = time;
    }

    public enum Role {
        HR, MANAGER
    }

    public enum MemberStatus {
        AVAILABLE, ASSIGNED
    }
}
