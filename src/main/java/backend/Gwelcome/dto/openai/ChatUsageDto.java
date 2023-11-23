package backend.Gwelcome.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatUsageDto {

    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;
}