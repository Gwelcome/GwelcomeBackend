package backend.Gwelcome.model;

import javax.persistence.Entity;
import backend.Gwelcome.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
@Entity
@Getter
@Table(name="MEMBERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "member_id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    private String provider;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @Enumerated(EnumType.STRING)
    private LivingArea livingArea;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String interest;

    public void addInfo(String gender, int age, String livingArea, String interest) {
        this.gender = Gender.valueOf(gender);
        this.age = age;
        this.livingArea = LivingArea.valueOf(livingArea);
        this.interest = interest;
    }
}