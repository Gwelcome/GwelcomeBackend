package backend.Gwelcome.dto.login;

import backend.Gwelcome.model.Member;
import lombok.Data;

@Data
public class MemberRefreshTokenDTO {
    private String userId;

    public MemberRefreshTokenDTO(String userId) {
        this.userId = userId;
    }

    public static MemberRefreshTokenDTO of(Member member){
        return new MemberRefreshTokenDTO(
                member.getId()
        );
    }
}