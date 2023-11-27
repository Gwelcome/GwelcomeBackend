package backend.Gwelcome.controller;

import backend.Gwelcome.dto.policy.PolicyRegisterDto;
import backend.Gwelcome.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "정책", description = "정책 관련 api입니다.")
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("/policy")
    @Operation(summary = "청년 정책 추가", description = "청년 정책을 등록하는 api 입니다.")
    public String makePolicy(@RequestBody PolicyRegisterDto policyRegisterDto){
        policyService.uploadPolicy(policyRegisterDto);
        return "정책 추가 완료";
    }
}