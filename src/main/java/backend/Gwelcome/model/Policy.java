package backend.Gwelcome.model;

import backend.Gwelcome.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policy extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "policy_id")
    private Long id;

    @OneToMany(mappedBy = "policy",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "policy", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Reply> reply;

    @Column(name = "name")
    private String name;
    @Column(name = "photo_url")
    private String photo_url;
    @Column(name = "policy_field")
    private String policy_field;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "business_operation_period")
    private String business_operation_period;
    @Column(name = "business_application_period")
    private String business_application_period;
    @Column(name = "support_scale")
    private int support_scale;
    @Column(name = "extraInfo")
    private String extraInfo;
    @Column(name = "request_procedure")
    private String request_procedure;
    @Column(name = "judge_presentation")
    private String judge_presentation;
    @Column(name = "application_site")
    private String application_site;
    @Column(name = "documents")
    private String documents;
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
    @Column(name = "useful_info")
    private String useful_info;
    @Column(name = "host_organization")
    private String host_organization;
    @Column(name = "operating_organization")
    private String operating_organization;
    @Column(name = "reference_site")
    private String reference_site;

    public void addLike(Likes likes){
        this.likes.add(likes);
    }

    public void deleteLike(Likes likes){
        this.likes.remove(likes);
    }

    public int getTotalLikes(){
        return likes.size();
    }
}