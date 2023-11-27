package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.dto.policy.MyPolicyResponseDTO;
import backend.Gwelcome.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "좋아요", description = "좋아요 관련 api입니다.")
public class LikesController {

    private final LikesService likesService;

    @Operation(summary = "좋아요 기능", description = "좋아요 기능 이미 했으면 취소 안했으면 추가를 실행한다.")
    @PostMapping("/likes/{policyId}")
    public ResponseDTO<String> clickLikes(@AuthenticationPrincipal String userId, @PathVariable Long policyId) {
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.clickLikes(userId, policyId));
    }

    @Operation(summary = "나의 관심 목록", description = "내가 좋아요 한 청년 정책들의 목록을 보여줍니다.")
    @GetMapping("/likes")
    public ResponseDTO<List<MyPolicyResponseDTO>> myLikePolicy(@AuthenticationPrincipal String userId){
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.likeItems(userId));
    }
}