package backend.Gwelcome.controller;

import backend.Gwelcome.dto.kakaologin.UserResponse;
import backend.Gwelcome.dto.openai.OpenAiChatResponseDto;
import backend.Gwelcome.model.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class ResponseEntityByWebClient {

    private final ObjectMapper objectMapper;
    @Value("${open-ai.key}")
    private String key;

    public ResponseEntity<String> chatParsed(HttpEntity<String> openAiRequest) throws JsonProcessingException {
        String openAiResponseBody = getOpenAiStringResponseBody(openAiRequest.getBody(), Chat.CHAT_ENDPOINT.data());

        OpenAiChatResponseDto openAiChatResponseDto = objectMapper.readValue(openAiResponseBody, OpenAiChatResponseDto.class);
        String openAiMessage = openAiChatResponseDto.getChoices().get(0).getMessage().getContent().trim();

        return getUserResponseEntity(openAiMessage);
    }

    private String getOpenAiStringResponseBody(String openAiRequestBody, String url) {

        WebClient webClient = WebClient.builder().build();
        Mono<ResponseEntity<String>> responseMono = webClient
                .method(HttpMethod.POST)
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION ,"Bearer "+key)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(openAiRequestBody))
                .retrieve()
                .toEntity(String.class);

        ResponseEntity<String> response = responseMono.block();
        return Objects.requireNonNull(response).getBody();
    }


    private ResponseEntity<String> getUserResponseEntity(String openAiMessage) {
        UserResponse userResponse = UserResponse.of(openAiMessage);
        return ResponseEntity.ok(userResponse.answer());
    }
}