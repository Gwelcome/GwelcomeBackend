package backend.Gwelcome.service;

import backend.Gwelcome.dto.kakaologin.KakaoUserDTO;
import backend.Gwelcome.dto.login.signUpRequestDTO;
import backend.Gwelcome.dto.naverlogin.NaverUserDTO;
import backend.Gwelcome.model.Gender;
import backend.Gwelcome.model.LivingArea;
import backend.Gwelcome.model.Member;
import backend.Gwelcome.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void kakaoSignUp(KakaoUserDTO kakaoUserDTO){
        Member member = Member.builder()
                .name(kakaoUserDTO.getProperties().getNickname())
                .email(kakaoUserDTO.getKakao_account().getEmail())
                .profile_image_url(kakaoUserDTO.getProperties().getProfile_image())
                .provider("카카오로그인")
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void naverSignUp(NaverUserDTO naverUserDTO){
        Member member = Member.builder()
                .name(naverUserDTO.getResponse().getName())
                .email(naverUserDTO.getResponse().getEmail())
                .profile_image_url(naverUserDTO.getResponse().getProfile_image())
                .provider("네이버로그인")
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void signUp(signUpRequestDTO signUpRequest) {

        Member member = Member.builder()
                .gender(Gender.valueOf(signUpRequest.getGender()))
                .age(signUpRequest.getAge())
                .livingArea(LivingArea.valueOf(signUpRequest.getLivingArea()))
                .build();

        memberRepository.save(member);
    }
}