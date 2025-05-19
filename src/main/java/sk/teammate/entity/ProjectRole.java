package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "required_skills", columnDefinition = "TEXT")
    private String requiredSkills;

    @Builder
    public ProjectRole(Project project, Role role, String requiredSkills) {
        this.project = project;
        this.role = role;
        this.requiredSkills = requiredSkills;
    }

    public void updateRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
}
