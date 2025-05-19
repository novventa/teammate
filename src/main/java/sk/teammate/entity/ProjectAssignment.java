package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_assignments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectAssignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private Member assignedBy;

    private boolean assignedByAi = false;

    private LocalDateTime assignedAt;

    @Builder
    public ProjectAssignment(Member member, Project project, Role role, Member assignedBy, boolean assignedByAi, LocalDateTime assignedAt) {
        this.member = member;
        this.project = project;
        this.role = role;
        this.assignedBy = assignedBy;
        this.assignedByAi = assignedByAi;
        this.assignedAt = assignedAt;
    }
}
