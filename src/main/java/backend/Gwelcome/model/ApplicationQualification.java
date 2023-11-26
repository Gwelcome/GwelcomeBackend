package backend.Gwelcome.model;

import lombok.*;
import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("A")
public class ApplicationQualification extends Policy{
    @Id @GeneratedValue
    @Column(name = "application_qualification_id")
    private Long id;

    @Column(name = "age")
    private String age;
    @Column(name = "living_income")
    private String living_income;
    @Column(name = "university")
    private String university;
    @Column(name = "major")
    private String major;
    @Column(name = "job_state")
    private String job_state;
    @Column(name = "specialization")
    private String specialization;
    @Column(name = "additional_clues")
    private String additional_clues;
    @Column(name = "partition_restrict_apply")
    private String partition_restrict_apply;
}