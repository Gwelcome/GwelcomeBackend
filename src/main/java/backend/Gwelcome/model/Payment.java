package backend.Gwelcome.model;

import backend.Gwelcome.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
    private Long Money;
    private String orderId;

    public void addInfo(Member member){
        this.member = member;
    }
}