package backend.Gwelcome.model;

import backend.Gwelcome.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="policy_id")
    private Policy policy;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

}