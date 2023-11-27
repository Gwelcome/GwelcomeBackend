package backend.Gwelcome.service;

import backend.Gwelcome.dto.policy.PolicyRegisterDto;
import backend.Gwelcome.model.Policy;
import backend.Gwelcome.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final PolicyRepository policyRepository;

    @Transactional
    public void uploadPolicy(PolicyRegisterDto policyRegisterDto) {
        Policy policy = Policy.builder()
                .additional_clues(policyRegisterDto.getAdditional_clues())
                .age(policyRegisterDto.getAge())
                .application_site(policyRegisterDto.getApplication_site())
                .business_application_period(policyRegisterDto.getBusiness_application_period())
                .business_operation_period(policyRegisterDto.getBusiness_operation_period())
                .documents(policyRegisterDto.getDocuments())
                .extraInfo(policyRegisterDto.getExtraInfo())
                .host_organization(policyRegisterDto.getHost_organization())
                .job_state(policyRegisterDto.getJob_state())
                .judge_presentation(policyRegisterDto.getJudge_presentation())
                .living_income(policyRegisterDto.getLiving_income())
                .major(policyRegisterDto.getMajor())
                .operating_organization(policyRegisterDto.getOperating_organization())
                .partition_restrict_apply(policyRegisterDto.getPartition_restrict_apply())
                .introduction(policyRegisterDto.getIntroduction())
                .name(policyRegisterDto.getName())
                .reference_site(policyRegisterDto.getReference_site())
                .request_procedure(policyRegisterDto.getRequest_procedure())
                .specialization(policyRegisterDto.getSpecialization())
                .university(policyRegisterDto.getUniversity())
                .useful_info(policyRegisterDto.getUseful_info())
                .photo_url(policyRegisterDto.getPhoto_url())
                .support_scale(policyRegisterDto.getSupport_scale())
                .build();
        policyRepository.save(policy);
    }
}