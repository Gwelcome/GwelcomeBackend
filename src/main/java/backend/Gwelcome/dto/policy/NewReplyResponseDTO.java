package backend.Gwelcome.dto.policy;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewReplyResponseDTO<T> {
    private int totalReplies;
    private List<ReplyResponseDTO> replyResponseDTO;
}