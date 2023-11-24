package backend.Gwelcome.model;

import javax.persistence.Entity;
import backend.Gwelcome.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "member_id")
    private String id;

    @Column(name = "name",length = 255)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    private String provider;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @Enumerated(EnumType.STRING)
    private LivingArea livingArea;
}