package backend.Gwelcome.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ButtonChatRequestDTO {
    private String policy_name;
    private String question;
}
