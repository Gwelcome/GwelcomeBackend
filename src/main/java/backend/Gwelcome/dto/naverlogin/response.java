package backend.Gwelcome.dto.naverlogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class response {
    private String id;
    private String profile_image;
    private String email;
    private String name;
}
