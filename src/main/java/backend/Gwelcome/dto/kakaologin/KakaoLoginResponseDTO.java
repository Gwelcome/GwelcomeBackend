package backend.Gwelcome.dto.kakaologin;

import lombok.*;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginResponseDTO {
    private String email;
    private String atk;
    private String rtk;
}
