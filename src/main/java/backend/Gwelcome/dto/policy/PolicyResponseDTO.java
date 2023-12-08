package backend.Gwelcome.dto.policy;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PolicyResponseDTO {
    private Long id;
    private String title;
    private String intro;
    private String image_url;
    private String policy_field;
    private String d_day;
}
