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
    @Lob
    @Column(name = "policy_summary")
    private String policy_summary;
    @Column(name = "city")
    private String city;
    @Column(name = "organization")
    private String organization;
    @Column(name = "operation_period")
    private String operation_period;
    @Column(name = "age")
    private String age;
    @Lob
    @Column(name = "introduction")
    private String introduction;
    @Lob
    @Column(name = "income")
    private String income;
    @Column(name = "job_state")
    private String job_state;
    @Column(name = "restrict")
    private String restrict;
    @Lob
    @Column(name = "apply_method")
    private String apply_method;
    @Column(name = "judge_presentation")
    private String judge_presentation;
    @Column(name = "website")
    private String website;
    @Column(name = "extra_info")
    private String extra_info;
    @Column(name = "cs")
    private String cs;
    @Column(name = "policy_field")
    private String policy_field;

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