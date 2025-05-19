package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String objectives;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PLANNED;

    private Long budget;

    private Integer expectedDurationMonths;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Member createdBy;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectRole> projectRoles = new ArrayList<>();

    public enum Status {
        PLANNED,
        ONGOING,
        COMPLETED
    }

    @Builder
    public Project(String title, String description, String objectives, Long budget,
                   Integer expectedDurationMonths, LocalDate startDate, LocalDate endDate,
                   Member createdBy) {
        this.title = title;
        this.description = description;
        this.objectives = objectives;
        this.budget = budget;
        this.expectedDurationMonths = expectedDurationMonths;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.status = Status.PLANNED;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
