package backend.Gwelcome.controller;

import backend.Gwelcome.dto.RequestDTO;
import backend.Gwelcome.service.FirebaseCloudMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "알람기능", description = "firebase 연결 알람기능 api입니다.")
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/fcm")
    @Operation(summary = "알람 기능", description = "알람 서비스를 제공한다")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
        firebaseCloudMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }
}
