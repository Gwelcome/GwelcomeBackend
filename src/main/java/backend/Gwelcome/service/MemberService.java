package backend.Gwelcome.service;

import backend.Gwelcome.dto.kakaologin.KakaoUserDTO;
import backend.Gwelcome.dto.login.MemberRefreshTokenDTO;
import backend.Gwelcome.dto.login.MyPageDTO;
import backend.Gwelcome.dto.login.TokensResponseDTO;
import backend.Gwelcome.dto.login.signUpRequestDTO;
import backend.Gwelcome.dto.naverlogin.NaverUserDTO;
import backend.Gwelcome.exception.ErrorCode;
import backend.Gwelcome.exception.GwelcomeException;
import backend.Gwelcome.jwt.JwtProvider;
import backend.Gwelcome.model.Member;
import backend.Gwelcome.model.Role;
import backend.Gwelcome.repository.LikesRepository;
import backend.Gwelcome.repository.MemberRepository;
import backend.Gwelcome.repository.PolicyRepository;
import backend.Gwelcome.repository.ReplyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    @Value(("${tmax.taba}"))
    private String password;
    private final MemberRepository memberRepository;
    private final PolicyRepository policyRepository;
    private final LikesRepository likesRepository;
    private final ReplyRepository replyRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Transactional
    public void kakaoSignUp(KakaoUserDTO kakaoUserDTO){

        boolean existEmail = memberRepository.existsByEmail(kakaoUserDTO.getKakao_account().getEmail());
        if (existEmail) {
            throw new GwelcomeException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .username(kakaoUserDTO.getProperties().getNickname())
                .email(kakaoUserDTO.getKakao_account().getEmail())
                .password(passwordEncoder.encode( password))
                .profile_image_url(kakaoUserDTO.getProperties().getProfile_image())
                .provider("카카오로그인")
                .role(Role.USER)
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
                .username(naverUserDTO.getResponse().getName())
                .email(naverUserDTO.getResponse().getEmail())
                .password(passwordEncoder.encode(password))
                .profile_image_url(naverUserDTO.getResponse().getProfile_image())
                .provider("네이버로그인")
                .role(Role.USER)
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public void signUp(signUpRequestDTO signUpRequest) {
        Member existMember = memberRepository.findByEmail(signUpRequest.getEmail()).get();
        existMember.addInfo(signUpRequest.getGender(),signUpRequest.getAge(),signUpRequest.getLivingArea(),signUpRequest.getInterests());
    }

    public Member memberFind(String email) {
        Member member = memberRepository.findByEmail(email).orElseGet(()-> {
            return new Member();
        });
        return member;
    }

    public String memberFindId(String email) {
        Member member = memberRepository.findByEmail(email).get();
        return member.getId();
    }

    public TokensResponseDTO reissueToken(String userId) throws JsonProcessingException {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new GwelcomeException(ErrorCode.MEMBER_NOT_FOUND));
        MemberRefreshTokenDTO memberRefreshTokenDTO = MemberRefreshTokenDTO.of(member);
        TokensResponseDTO tokens = jwtProvider.reissueAtk(memberRefreshTokenDTO);
        return tokens;
    }

    public MyPageDTO myPage(String userId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new GwelcomeException(ErrorCode.MEMBER_NOT_FOUND));
        long countPoliciesByMember = policyRepository.countPoliciesByMember(String.valueOf(member.getInterest()));
        long countLikesByMember = likesRepository.countLikesByMember(userId);
        long countRepliesByMember = replyRepository.countRepliesByMember(userId);

        MyPageDTO myPageDTO = MyPageDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .image_url(member.getProfile_image_url())
                .myPolicies(countPoliciesByMember)
                .myLikes(countLikesByMember)
                .myReplies(countRepliesByMember)
                .build();

        return myPageDTO;
    }
}