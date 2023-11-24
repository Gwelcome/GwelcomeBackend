package backend.Gwelcome.dto.naverlogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverUserDTO {
    private String resultcode;
    private String message;
    private response response;
}