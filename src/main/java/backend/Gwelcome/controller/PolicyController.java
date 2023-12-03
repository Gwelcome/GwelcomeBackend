package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.dto.policy.PolicyOneResponseDTO;
import backend.Gwelcome.dto.policy.PolicyRegisterDto;
import backend.Gwelcome.dto.policy.PolicyResponseDTO;
import backend.Gwelcome.dto.policy.ReplySaveRequestDto;
import backend.Gwelcome.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "정책", description = "정책 관련 api입니다.")
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/policy/{policyId}")
    @Operation(summary = "청년 정책 상세보기", description = "청년 정책 상세 보기 api 입니다.")
    public ResponseDTO<PolicyOneResponseDTO> policyOne(@PathVariable Long policyId){
        PolicyOneResponseDTO policyOneResponseDTO = policyService.readOne(policyId);
        return new ResponseDTO<>(HttpStatus.OK.value(),policyOneResponseDTO);
    }

    @GetMapping("/policy")
    @Operation(summary = "청년 정책 목록", description = "청년 정책 목록을 제공하는 api 입니다. (페이징 5개씩)")
    public ResponseDTO<Page<PolicyResponseDTO>> policy(@PageableDefault(size = 5) Pageable pageable){
        Page<PolicyResponseDTO> list = policyService.list(pageable);
        return new ResponseDTO<>(HttpStatus.OK.value(),list);
    }

    @PostMapping("/policy")
    @Operation(summary = "청년 정책 추가", description = "청년 정책을 등록하는 api 입니다.")
    public String makePolicy(@RequestBody PolicyRegisterDto policyRegisterDto){
        policyService.uploadPolicy(policyRegisterDto);
        return "정책 추가 완료";
    }

    @PostMapping("/reply/{policyId}")
    @Operation(summary = "청년 정책 댓글 작성", description = "청년 정책을 댓글 등록하는 api 입니다.")
    public String replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto, @AuthenticationPrincipal String userId,
                            @PathVariable Long policyId){
        policyService.replyWrite(replySaveRequestDto,userId,policyId);
        return "댓글 작성 완료";
    }
}