package backend.Gwelcome.dto.kakaologin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private String is_default_image;
}
