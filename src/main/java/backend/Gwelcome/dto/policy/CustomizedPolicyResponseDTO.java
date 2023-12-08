package backend.Gwelcome.dto.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CustomizedPolicyResponseDTO {
    private Long id;
    private String image_url;
    private String title;
    private String policy_summary;
}