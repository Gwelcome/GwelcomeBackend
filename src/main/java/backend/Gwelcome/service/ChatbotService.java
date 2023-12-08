package backend.Gwelcome.service;

import backend.Gwelcome.dto.chat.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatbotService {
    @Value("${ai.server}")
    private String aiServerUrl;

    @Value("${ai.chat}")
    private String aiChatUrl;

    @Value("${ai.buttonChat}")
    private String aiButtonChatUrl;

    public SimilarQuestionDto similarQuestion(SimilarChatDto similarChatDto) throws JSONException, JsonProcessingException {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("policy_id",similarChatDto.getPolicy_id());
        jsonObject.put("question",similarChatDto.getQuestion());

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = rt.exchange(
                aiServerUrl,
                HttpMethod.POST,
                stringHttpEntity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        SimilarQuestionDto similarQuestionDto = objectMapper.readValue(response.getBody(), SimilarQuestionDto.class);
        return similarQuestionDto;
    }

    public ChatResponseDTO question(ChatRequestDTO chatRequestDTO) throws JSONException, JsonProcessingException {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("question",chatRequestDTO.getQuestion());

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = rt.exchange(
                aiChatUrl,
                HttpMethod.POST,
                stringHttpEntity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        ChatResponseDTO chatResponseDTO = objectMapper.readValue(response.getBody(), ChatResponseDTO.class);
        return chatResponseDTO;
    }

    public ButtonChatResponseDTO buttonQuestion(ButtonChatRequestDTO buttonChatRequestDTO) throws JSONException, JsonProcessingException {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("policy_name",buttonChatRequestDTO.getPolicy_name());
        jsonObject.put("question",buttonChatRequestDTO.getQuestion());

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = rt.exchange(
                aiButtonChatUrl,
                HttpMethod.POST,
                stringHttpEntity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        ButtonChatResponseDTO buttonChatResponseDTO = objectMapper.readValue(response.getBody(), ButtonChatResponseDTO.class);
        return buttonChatResponseDTO;
    }
}
