package backend.Gwelcome.service;

import backend.Gwelcome.dto.toss.DonateAmount;
import backend.Gwelcome.dto.toss.MemberPayDTO;
import backend.Gwelcome.exception.ErrorCode;
import backend.Gwelcome.exception.GwelcomeException;
import backend.Gwelcome.model.Member;
import backend.Gwelcome.model.Payment;
import backend.Gwelcome.repository.MemberRepository;
import backend.Gwelcome.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TossService {

    @Value("${toss.secret_key}")
    private String secret_key;

    private static final String tossPaymentSuccessUrl = "https://api.tosspayments.com/v1/payments/confirm";

    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void payment(String orderId, String paymentKey, long amount) throws JSONException, JsonProcessingException, UnsupportedEncodingException {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(Base64.getEncoder().encode((secret_key + ":").getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",orderId);
        jsonObject.put("paymentKey",paymentKey);
        jsonObject.put("amount",amount);

        HttpEntity<String> stringHttpEntity = new HttpEntity<>(jsonObject.toString(), headers);

        ResponseEntity<String> response = rt.exchange(
                tossPaymentSuccessUrl,
                HttpMethod.POST,
                stringHttpEntity,
                String.class
        );
        Payment payment = Payment.builder()
                .Money(amount)
                .orderId(orderId)
                .build();
        paymentRepository.save(payment);
    }

    @Transactional
    public void payUpdate(String userId, MemberPayDTO memberPayDTO) {
        Member member = memberRepository.findById(userId).orElseThrow(()-> new GwelcomeException(ErrorCode.MEMBER_NOT_FOUND));
        Payment payments = paymentRepository.findOrderId(memberPayDTO.getOrderId());
        payments.addInfo(member);
    }

    public DonateAmount totalDonate() {
        Long totalDonateAmount = paymentRepository.getTotalDonateAmount();
        DonateAmount donateAmount = DonateAmount.builder()
                .totalDonateAmount(totalDonateAmount)
                .build();
        return donateAmount;
    }
}
