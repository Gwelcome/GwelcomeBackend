package backend.Gwelcome.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyPageDTO {
    private String id;
    private String username;
    private String image_url;
    private long myPolicies;
    private long myLikes;
    private long myReplies;
}
