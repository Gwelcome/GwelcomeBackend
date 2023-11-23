package backend.Gwelcome.Controller;

import java.util.Collections;

import backend.Gwelcome.dto.openai.ChatMessageDto;
import backend.Gwelcome.dto.openai.ChatParsedRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.chatgpt.data.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class OpenAiRequestEntity {

    private final ObjectMapper objectMapper;
    private final HttpHeaders headers;

    public HttpEntity<String> chatParsed(String content) throws JsonProcessingException {
        ChatMessageDto chatMessageDto = new ChatMessageDto(Chat.ROLE.data(), content);

        String chatOpenAiBody = objectMapper.
                writeValueAsString(
                        new ChatParsedRequestDto(
                                Chat.MODEL.data(),
                                Collections.singletonList(chatMessageDto)
                        ));
        return new HttpEntity<>(chatOpenAiBody, headers);
    }
}