package backend.Gwelcome.controller;

import backend.Gwelcome.dto.ResponseDTO;
import backend.Gwelcome.dto.toss.PaymentResponseDTO;
import backend.Gwelcome.service.TossService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Toss 결제 시스템", description = "Toss 결제시스템 관련 api 입니다.")
public class TossController {

    private final TossService tossService;

    @GetMapping("/success")
    @Operation(summary = "토스 결제 시스템",description = "토스 결제 시스템 구축 api 입니다.")
    public ResponseDTO<PaymentResponseDTO> successPay(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam long amount) throws JSONException, JsonProcessingException {
        PaymentResponseDTO payment = tossService.payment(orderId, paymentKey, amount);
        return new ResponseDTO<>(HttpStatus.OK.value(),payment);
    }
}
