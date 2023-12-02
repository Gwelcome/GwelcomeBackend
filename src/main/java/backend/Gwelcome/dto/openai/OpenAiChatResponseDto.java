package backend.Gwelcome.dto.openai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiChatResponseDto {

    private String id;
    private String object;
    private long created;
    private String model;
    private ChatUsageDto usage;
    private List<ChatChoiceDto> choices;
    private String system_fingerprint;
}