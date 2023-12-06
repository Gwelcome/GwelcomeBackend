package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.dto.chat.SimilarChatDto;
import backend.Gwelcome.dto.chat.SimilarQuestionDto;
import backend.Gwelcome.service.ChatbotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "챗봇", description = "chatbot 관련 api입니다.")
public class OpenAiController {
    private final OpenAiRequestEntity openAiRequestEntity;
    private final ResponseEntityByWebClient responseEntityByWebClient;
    private final ChatbotService chatbotService;

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

    @PostMapping("/chat/top3")
    @Operation(summary = "관련 질문 유사도 top3",description = "사용자의 질문과 유사도가 높은 질문 3가지를 제공하는 api 입니다.")
    public ResponseDTO<SimilarQuestionDto> top3Question(@RequestBody SimilarChatDto similarChatDto) throws JSONException, JsonProcessingException {
        SimilarQuestionDto similarQuestionDto = chatbotService.similarQuestion(similarChatDto);
        return new ResponseDTO<>(HttpStatus.OK.value(), similarQuestionDto);
    }
}