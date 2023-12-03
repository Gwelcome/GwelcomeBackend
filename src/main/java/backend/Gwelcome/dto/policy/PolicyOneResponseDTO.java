package backend.Gwelcome.dto.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyOneResponseDTO {
    private Long id;
    private String title;
    private String intro;
    private String image_url;
    private int likesCount;
    private String policy_field;
    private String d_day;
    private String living_income;
    private String age;
    private String business_application_period;
}
