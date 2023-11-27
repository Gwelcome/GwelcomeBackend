package backend.Gwelcome.service;

import backend.Gwelcome.dto.policy.MyPolicyResponseDTO;
import backend.Gwelcome.exception.ErrorCode;
import backend.Gwelcome.exception.GwelcomeException;
import backend.Gwelcome.model.Likes;
import backend.Gwelcome.model.Member;
import backend.Gwelcome.model.Policy;
import backend.Gwelcome.repository.LikesRepository;
import backend.Gwelcome.repository.MemberRepository;
import backend.Gwelcome.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private final MemberRepository memberRepository;
    private final PolicyRepository policyRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public String clickLikes(String userId, Long policyId){
        Member member = memberRepository.findById(userId).orElseThrow(()->new GwelcomeException(ErrorCode.MEMBER_NOT_FOUND));
        Policy policy = policyRepository.findById(policyId).orElseThrow(()->new GwelcomeException(ErrorCode.POLICY_NOT_FOUND));

        Likes likes = likesRepository.findByPolicyAndMember(policy,member);
        if(likes != null){
            likes.deleteLike();
            likesRepository.delete(likes);
            return "좋아요 취소";
        }
        else{
            Likes like = Likes.builder()
                    .member(member)
                    .policy(policy)
                    .build();
            likesRepository.save(like);
            like.addLike();
            return "좋아요 완료";
        }
    }

    public List<MyPolicyResponseDTO> likeItems(String userId) {
        Member member = memberRepository.findById(userId).orElseThrow(()->new GwelcomeException(ErrorCode.MEMBER_NOT_FOUND));
        List<Likes> likes = likesRepository.findByMember(member);
        List<Policy> policies = policyRepository.findByLikesIn(likes);
        List<MyPolicyResponseDTO> result = policies.stream()
                .map(b->new MyPolicyResponseDTO(b))
                .collect(Collectors.toList());
        return result;
    }
}
