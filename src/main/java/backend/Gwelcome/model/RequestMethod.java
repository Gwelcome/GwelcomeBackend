package backend.Gwelcome.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("R")
public class RequestMethod extends Policy{
    @Id @GeneratedValue
    @Column(name = "request_method_id")
    private Long id;

    @Column(name = "request_procedure")
    private String request_procedure;
    @Column(name = "judge_presentation")
    private String judge_presentation;
    @Column(name = "application_site")
    private String application_site;
    @Column(name = "documents")
    private String documents;
}