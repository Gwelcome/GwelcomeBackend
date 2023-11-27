package backend.Gwelcome.dto.policy;

import backend.Gwelcome.model.Policy;
import lombok.Data;

@Data
public class MyPolicyResponseDTO {
    private Long id;
    private String title;
    private String intro;
    private String image_url;
    private int likesCount;

    public MyPolicyResponseDTO(Policy policy){
        id = policy.getId();
        title = policy.getName();
        intro = policy.getIntroduction();
        image_url = policy.getPhoto_url();
        likesCount = policy.getTotalLikes();
    }
}