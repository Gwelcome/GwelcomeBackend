package backend.Gwelcome.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimilarChatDto {
    private int policy_id;
    private String question;
}