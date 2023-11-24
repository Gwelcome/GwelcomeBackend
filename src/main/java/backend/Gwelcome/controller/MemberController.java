package backend.Gwelcome.controller;

import backend.Gwelcome.dto.kakaologin.KakaoOauthToken;
import backend.Gwelcome.dto.login.signUpRequestDTO;
import backend.Gwelcome.dto.naverlogin.NaverOauthToken;
import backend.Gwelcome.dto.naverlogin.NaverUserDTO;
import backend.Gwelcome.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입 및 로그인", description = "카카오,네이버 로그인 관련 api입니다.")
public class MemberController {

    @Value("${kakao.client-id}")
    private String kakao_client_id;
    @Value("${naver.client-id}")
    private String naver_client_id;
    @Value("${naver.client_secret}")
    private String naver_client_secret;

    private final MemberService memberService;

    @GetMapping("/auth/kakao/callback")
    @Operation(summary = "카카오로그인", description = "카카오 로그인을 진행합니다.")
    public Mono<String> kakaoCallback(@RequestParam String code) {

        WebClient webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
        tokenParams.add("grant_type", "authorization_code");
        tokenParams.add("client_id", naver_client_id);
        tokenParams.add("client_secret", naver_client_secret);
        tokenParams.add("redirect_uri", "http://localhost:8080/login/oauth2/code/naver");
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

    @GetMapping("/login/oauth2/code/naver")
    public Mono<String> naverCallback(@RequestParam String code) {
        WebClient.Builder webClientBuilder = WebClient.builder();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();

        tokenParams.add("grant_type", "authorization_code");
        tokenParams.add("client_id", naver_client_id);
        tokenParams.add("client_secret", naver_client_secret);
        tokenParams.add("redirect_uri", "http://localhost:8080/login/oauth2/code/naver");
        tokenParams.add("code", code);

        WebClient.RequestBodySpec request = (WebClient.RequestBodySpec) webClientBuilder.build().post()
                .uri("https://nid.naver.com/oauth2.0/token")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(tokenParams));

        return request.retrieve().bodyToMono(String.class)
                .flatMap(response -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    NaverOauthToken oauthToken;
                    try {
                        oauthToken = objectMapper.readValue(response, NaverOauthToken.class);
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }

                    HttpHeaders profileHeaders = new HttpHeaders();
                    profileHeaders.add("Authorization", "Bearer " + oauthToken.getAccess_token());
                    profileHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

                    WebClient.RequestBodySpec profileRequest = (WebClient.RequestBodySpec) webClientBuilder.build().get()
                            .uri("https://openapi.naver.com/v1/nid/me")
                            .headers(httpHeaders -> httpHeaders.addAll(profileHeaders));

                    return profileRequest.retrieve().bodyToMono(String.class)
                            .flatMap(profileResponse -> {
                                try {
                                    NaverUserDTO naverUserDTO = objectMapper.readValue(profileResponse, NaverUserDTO.class);
                                    memberService.naverSignUp(naverUserDTO);
                                    return Mono.just(profileResponse);
                                } catch (JsonProcessingException e) {
                                    return Mono.error(e);
                                }
                            });
                });
    }

    @PostMapping("/api/sign-up")
    @Operation(summary = "회원가입",description = "추가적인 정보를 입력받아 회원가입을 진행합니다.")
    public String signUp(@RequestBody @Valid signUpRequestDTO signUpRequestDTO){
        memberService.signUp(signUpRequestDTO);
        return "회원가입 완료";
    }
}