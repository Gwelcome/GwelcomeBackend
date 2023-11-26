package backend.Gwelcome.model;

import backend.Gwelcome.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Likes extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public void addLike(){
        this.policy.addLike(this);
    }

    public void deleteLike(){
        this.policy.deleteLike(this);
    }
}