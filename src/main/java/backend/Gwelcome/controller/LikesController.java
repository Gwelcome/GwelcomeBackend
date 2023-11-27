package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikesController {

    private final LikesService likesService;

    @Operation(summary = "좋아요 기능", description = "좋아요 기능 이미 했으면 취소 안했으면 추가를 실행한다.")
    @PostMapping("/likes/{policyId}")
    public ResponseDTO<String> clickLikes(@AuthenticationPrincipal String userId, @PathVariable Long policyId) {
        return new ResponseDTO<>(HttpStatus.OK.value(), likesService.clickLikes(userId, policyId));
    }
}