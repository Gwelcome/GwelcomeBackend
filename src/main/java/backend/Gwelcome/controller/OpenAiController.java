package backend.Gwelcome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "챗봇", description = "Open ai 서버 chatbot 관련 api입니다.")
public class OpenAiController {
    private final OpenAiRequestEntity openAiRequestEntity;
    private final ResponseEntityByWebClient responseEntityByWebClient;

    private String chatRequest = "";

    @PostMapping("/chat")
    @Operation(summary = "chatbot",description = "질문과 응답을 이어붙여 세션을 유지하는 방식의 질문하기 api 입니다.")
    public ResponseEntity<String> chat(@RequestBody String request) throws JsonProcessingException {
        chatRequest = chatRequest + request;
        HttpEntity<String> openAiRequest = openAiRequestEntity.chatParsed(chatRequest);
        ResponseEntity<String> openAiResponseEntity = responseEntityByWebClient.chatParsed(openAiRequest);
        chatRequest = chatRequest + openAiResponseEntity.getBody();
        return openAiResponseEntity;
    }

    @GetMapping("/chat/reset")
    @Operation(summary = "질문하기 reset",description = "사용자의 질문을 reset하여 질문 세션을 초기화 하는 api 입니다.")
    public ResponseEntity<String> chatQuestionReset() {
        chatRequest = "";
        return ResponseEntity.ok("success");
    }
}