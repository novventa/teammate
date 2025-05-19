package sk.teammate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experiences")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Experience extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String title;
    private String companyName;
    private int durationMonths;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Builder
    public Experience(Member member, String title, String companyName, int durationMonths, String summary) {
        this.member = member;
        this.title = title;
        this.companyName = companyName;
        this.durationMonths = durationMonths;
        this.summary = summary;
    }

    public void update(String title, String companyName, int durationMonths, String summary) {
        this.title = title;
        this.companyName = companyName;
        this.durationMonths = durationMonths;
        this.summary = summary;
    }
}
