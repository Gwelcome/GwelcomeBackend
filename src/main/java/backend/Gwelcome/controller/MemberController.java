package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.dto.kakaologin.KakaoLoginResponseDTO;
import backend.Gwelcome.dto.kakaologin.KakaoOauthToken;
import backend.Gwelcome.dto.kakaologin.KakaoUserDTO;
import backend.Gwelcome.dto.login.TokensResponseDTO;
import backend.Gwelcome.dto.login.signUpRequestDTO;
import backend.Gwelcome.dto.naverlogin.NaverLoginResponseDTO;
import backend.Gwelcome.dto.naverlogin.NaverOauthToken;
import backend.Gwelcome.dto.naverlogin.NaverUserDTO;
import backend.Gwelcome.jwt.JwtProvider;
import backend.Gwelcome.model.Member;
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
    private final JwtProvider jwtProvider;

    @GetMapping("/auth/kakao/callback")
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 api 입니다.")
    public Mono<?> kakaoCallback(@RequestParam String code) {
        WebClient webClient = WebClient.builder().build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", kakao_client_id);
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        return webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(tokenResponse -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    KakaoOauthToken oauthToken;
                    try {
                        oauthToken = objectMapper.readValue(tokenResponse, KakaoOauthToken.class);
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }

                    WebClient webClient2 = WebClient.builder().build();

                    return webClient2.post()
                            .uri("https://kapi.kakao.com/v2/user/me")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + oauthToken.getAccess_token())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .retrieve()
                            .bodyToMono(String.class)
                            .flatMap(profileResponse -> {
                                try {
                                    KakaoUserDTO kakaoUserDTO = objectMapper.readValue(profileResponse, KakaoUserDTO.class);
                                    Member member = memberService.memberFind(kakaoUserDTO.getKakao_account().getEmail());
                                    if (member.getUsername() == null) {
                                        memberService.kakaoSignUp(kakaoUserDTO);
                                        String id = memberService.memberFindId(kakaoUserDTO.getKakao_account().getEmail());
                                        TokensResponseDTO tokens = jwtProvider.createTokensByLogin(id);
                                        KakaoLoginResponseDTO resultDTO = KakaoLoginResponseDTO.builder()
                                                .email(kakaoUserDTO.getKakao_account().getEmail())
                                                .atk(tokens.getAtk())
                                                .rtk(tokens.getRtk())
                                                .build();
                                        return Mono.just(new ResponseDTO<>(HttpStatus.OK.value(), resultDTO));
                                    }
                                    String id = memberService.memberFindId(kakaoUserDTO.getKakao_account().getEmail());
                                    TokensResponseDTO tokens = jwtProvider.createTokensByLogin(id);
                                    return Mono.just(new ResponseDTO<>(HttpStatus.OK.value(),tokens));
                                } catch (JsonProcessingException e) {
                                    return Mono.error(e);
                                }
                            });
                });
    }

    @GetMapping("/login/oauth2/code/naver")
    @Operation(summary = "네이버 로그인", description = "네이버 로그인 api 입니다.")
    public Mono<?> naverCallback(@RequestParam String code) {
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
                                    Member member = memberService.memberFind(naverUserDTO.getResponse().getEmail());

                                    if (member.getUsername() == null){
                                        memberService.naverSignUp(naverUserDTO);
                                        String id = memberService.memberFindId(naverUserDTO.getResponse().getEmail());
                                        TokensResponseDTO tokens = jwtProvider.createTokensByLogin(id);
                                        NaverLoginResponseDTO resultDTO = NaverLoginResponseDTO.builder()
                                                .email(naverUserDTO.getResponse().getEmail())
                                                .atk(tokens.getAtk())
                                                .rtk(tokens.getRtk()).build();
                                        return Mono.just(new ResponseDTO<>(HttpStatus.OK.value(),resultDTO));
                                    }

                                    String id = memberService.memberFindId(naverUserDTO.getResponse().getEmail());
                                    TokensResponseDTO tokens = jwtProvider.createTokensByLogin(id);
                                    return Mono.just(new ResponseDTO<>(HttpStatus.OK.value(),tokens));
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