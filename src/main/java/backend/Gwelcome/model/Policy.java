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
@DiscriminatorColumn(name = "parent")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Policy extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "policy_id")
    private Long id;

    @OneToMany(mappedBy = "policy",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @Column(name = "name")
    private String name;
    @Column(name = "photo_url")
    private String photo_url;
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