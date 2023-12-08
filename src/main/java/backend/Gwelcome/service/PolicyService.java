package backend.Gwelcome.service;

import backend.Gwelcome.dto.policy.PolicyOneResponseDTO;
import backend.Gwelcome.dto.policy.PolicyRegisterDto;
import backend.Gwelcome.dto.policy.PolicyResponseDTO;
import backend.Gwelcome.dto.policy.ReplySaveRequestDto;
import backend.Gwelcome.exception.ErrorCode;
import backend.Gwelcome.exception.GwelcomeException;
import backend.Gwelcome.model.Member;
import backend.Gwelcome.model.Policy;
import backend.Gwelcome.model.Reply;
import backend.Gwelcome.repository.MemberRepository;
import backend.Gwelcome.repository.PolicyRepository;
import backend.Gwelcome.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void uploadPolicy(PolicyRegisterDto policyRegisterDto) {
        Policy policy = Policy.builder()
                .name(policyRegisterDto.getName())
                .photo_url(policyRegisterDto.getPhoto_url())
                .policy_summary(policyRegisterDto.getPolicy_summary())
                .city(policyRegisterDto.getCity())
                .organization(policyRegisterDto.getOrganization())
                .operation_period(policyRegisterDto.getOperation_period())
                .age(policyRegisterDto.getAge())
                .introduction(policyRegisterDto.getIntroduction())
                .income(policyRegisterDto.getIncome())
                .job_state(policyRegisterDto.getJob_state())
                .restrict(policyRegisterDto.getRestrict())
                .apply_method(policyRegisterDto.getApply_method())
                .judge_presentation(policyRegisterDto.getJudge_presentation())
                .website(policyRegisterDto.getWebsite())
                .extra_info(policyRegisterDto.getExtra_info())
                .cs(policyRegisterDto.getCs())
                .policy_field(policyRegisterDto.getPolicy_field())
                .build();
        policyRepository.save(policy);
    }

    @Transactional
    public void replyWrite(ReplySaveRequestDto replySaveRequestDto, String userId,Long policyId) {
        Member member = memberRepository.findById(userId).orElseThrow(()-> new GwelcomeException(ErrorCode.MEMBER_NOT_FOUND));
        Policy policy = policyRepository.findById(policyId).orElseThrow(()-> new GwelcomeException(ErrorCode.POLICY_NOT_FOUND));
        Reply reply = Reply.builder()
                .content(replySaveRequestDto.getContent())
                .member(member)
                .policy(policy)
                .build();
        replyRepository.save(reply);
    }

    public Page<PolicyResponseDTO> list(Pageable pageable) {
        Page<Policy> policyList = policyRepository.findAll(pageable);

        Page<PolicyResponseDTO> result = policyList.map(m->PolicyResponseDTO.builder()
                .id(m.getId())
                .title(m.getName())
                .intro(m.getIntroduction())
                .image_url(m.getPhoto_url())
                .d_day(D_DAY(m.getOperation_period()))
                .policy_field(m.getPolicy_field())
                .build());
        return result;
    }

    private static String D_DAY(String finalDay) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(finalDay);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date now = new Date();
        long remainDate;
        if (date.getTime() < now.getTime()) {
            remainDate = 0;
        }
        long diffSec = (date.getTime() - now.getTime()) / 1000;
        remainDate = diffSec / (24 * 60 * 60);
        return "D-"+String.valueOf(remainDate);
    }

    public PolicyOneResponseDTO readOne(Long policyId) {
        Policy policy = policyRepository.findById(policyId).orElseThrow(() -> new GwelcomeException(ErrorCode.POLICY_NOT_FOUND));
        PolicyOneResponseDTO policyOne = PolicyOneResponseDTO.builder()
                .id(policy.getId())
                .title(policy.getName())
                .intro(policy.getIntroduction())
                .image_url(policy.getPhoto_url())
                .likesCount(policy.getTotalLikes())
                .d_day(D_DAY(policy.getOperation_period()))
                .living_income(policy.getIncome())
                .age(policy.getAge())
                .business_application_period(policy.getOperation_period())
                .policy_field(policy.getPolicy_field())
                .build();
        return policyOne;
    }
}