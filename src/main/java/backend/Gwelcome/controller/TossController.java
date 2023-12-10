package backend.Gwelcome.controller;

import backend.Gwelcome.service.TossService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
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
    public void successPay(@RequestParam String orderId, @RequestParam String paymentKey, @RequestParam long amount) throws JSONException {
        tossService.payment(orderId,paymentKey,amount);


        return ;
    }
}
