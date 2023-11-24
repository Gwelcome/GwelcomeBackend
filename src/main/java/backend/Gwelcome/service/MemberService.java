package backend.Gwelcome.service;

import backend.Gwelcome.dto.kakaologin.KakaoUserDTO;
import backend.Gwelcome.dto.login.signUpRequestDTO;
import backend.Gwelcome.dto.naverlogin.NaverUserDTO;
import backend.Gwelcome.exception.ErrorCode;
import backend.Gwelcome.exception.GwelcomeException;
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

        boolean existEmail = memberRepository.existsByEmail(kakaoUserDTO.getKakao_account().getEmail());
        if (existEmail) {
            throw new GwelcomeException(ErrorCode.DUPLICATE_EMAIL);
        }

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

        boolean existEmail = memberRepository.existsByEmail(naverUserDTO.getResponse().getEmail());
        if (existEmail) {
            throw new GwelcomeException(ErrorCode.DUPLICATE_EMAIL);
        }

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
        Member existMember = memberRepository.findByEmail(signUpRequest.getEmail());
        existMember.addInfo(signUpRequest.getGender(),signUpRequest.getAge(),signUpRequest.getLivingArea());
    }
}