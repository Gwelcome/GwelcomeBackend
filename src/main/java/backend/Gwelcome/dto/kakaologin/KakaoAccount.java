package backend.Gwelcome.dto.kakaologin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccount {
    private String profile_nickname_needs_agreement;
    private String profile_image_needs_agreement;
    private Profile profile;
    private String has_email;
    private String email_needs_agreement;
    private String is_email_valid;
    private String is_email_verified;
    private String email;

}