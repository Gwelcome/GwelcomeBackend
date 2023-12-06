package backend.Gwelcome.service;

import backend.Gwelcome.dto.chat.SimilarChatDto;
import backend.Gwelcome.dto.chat.SimilarQuestionDto;
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
}
