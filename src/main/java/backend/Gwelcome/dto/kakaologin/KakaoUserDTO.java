package backend.Gwelcome.dto.kakaologin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserDTO {

    private String id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;
}