package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.dto.toss.DonateAmount;
import backend.Gwelcome.dto.toss.MemberPayDTO;
import backend.Gwelcome.service.TossService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Toss 결제 시스템", description = "Toss 결제시스템 관련 api 입니다.")
public class TossController {

    private final TossService tossService;

    @GetMapping("/totalDonate")
    @Operation(summary = "후원금 총 금액",description = "후원금 총 금액을 확인하는 api 입니다.")
    public ResponseDTO<DonateAmount> totalDonate(){
        DonateAmount donateAmount = tossService.totalDonate();
        return new ResponseDTO<>(HttpStatus.OK.value(),donateAmount);
    }

    @PostMapping("/success")
    @Operation(summary = "토스 결제 시스템 회원정보 추가",description = "토스 결제 시스템 회원 정보 추가 api 입니다.")
    public ResponseDTO<String> successPay(@AuthenticationPrincipal String userId, @RequestBody MemberPayDTO memberPayDTO){
        tossService.payUpdate(userId, memberPayDTO);
        return new ResponseDTO<>(HttpStatus.OK.value(),"결제 완료");
    }

    @GetMapping("/success")
    @Operation(summary = "토스 결제 시스템",description = "토스 결제 시스템 구축 api 입니다.")
    public ResponseDTO<String> successPay(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam long amount) throws JSONException, JsonProcessingException, UnsupportedEncodingException {
        tossService.payment(orderId, paymentKey, amount);
        return new ResponseDTO<>(HttpStatus.OK.value(),"결제 완료");
    }
}
