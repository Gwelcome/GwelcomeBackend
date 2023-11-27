package backend.Gwelcome.dto.naverlogin;

import lombok.*;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverLoginResponseDTO {
    private String email;
    private String atk;
    private String rtk;
}
