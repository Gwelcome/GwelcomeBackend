package backend.Gwelcome.controller;

import backend.Gwelcome.dto.kakaologin.KakaoOauthToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입 및 로그인", description = "카카오,네이버 로그인 관련 api입니다.")
public class MemberController {

    @Value("${kakao.client-id}")
    private String client_id;
    @GetMapping("/auth/kakao/callback")
    @Operation(
            summary = "카카오로그인",
            description = "카카오 로그인을 진행합니다."
    )
    public Mono<String> kakaoCallback(@RequestParam String code) {

        WebClient webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
        tokenParams.add("grant_type", "authorization_code");
        tokenParams.add("client_id", client_id);
        tokenParams.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        tokenParams.add("code", code);

        return webClient.post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData(tokenParams))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(tokenResponse -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        KakaoOauthToken oauthToken = objectMapper.readValue(tokenResponse, KakaoOauthToken.class);

                        WebClient profileWebClient = WebClient.builder()
                                .baseUrl("https://kapi.kakao.com")
                                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken.getAccess_token())
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .build();

                        return profileWebClient.post()
                                .uri("/v2/user/me")
                                .retrieve()
                                .bodyToMono(String.class);
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }
                });
    }
}