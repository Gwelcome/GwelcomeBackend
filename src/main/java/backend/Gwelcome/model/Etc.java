package backend.Gwelcome.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("E")

public class Etc extends Policy {
    @Id @GeneratedValue
    @Column(name = "etc_id")
    private Long id;

    @Column(name = "useful_info")
    private String useful_info;
    @Column(name = "host_organization")
    private String host_organization;
    @Column(name = "operating_organization")
    private String operating_organization;
    @Column(name = "reference_site")
    private String reference_site;
}
