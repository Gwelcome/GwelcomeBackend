package backend.Gwelcome.dto.policy;

import backend.Gwelcome.model.Policy;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PolicyResponseDTO {
    private Long id;
    private String title;
    private String intro;
    private String image_url;
}
