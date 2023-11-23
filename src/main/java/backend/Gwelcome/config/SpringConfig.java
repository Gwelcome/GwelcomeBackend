package backend.Gwelcome.config;

import backend.Gwelcome.Controller.ResponseEntityByWebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpringConfig {

    @Value("${open-ai.key}")
    private String key;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeaders(
                        headers -> {
                            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                            headers.setBearerAuth(key);
                        }
                )
                .build();
    }

    @Bean
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(key);
        return headers;
    }


    @Bean
    public ResponseEntityByWebClient openAiResponse() {
        return new ResponseEntityByWebClient(objectMapper(), webClient());
    }
}