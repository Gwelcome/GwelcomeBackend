package backend.Gwelcome.dto.toss;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDTO {
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int taxExemptionAmount;
    private String status;
    private Date requestedAt;
    private Date approvedAt;
    private boolean useEscrow;
    private boolean cultureExpense;
    private Card card;
    private String secret;
    private String type;
    private EasyPay easyPay;
    private String country;
    private Receipt receipt;
    private Checkout checkout;
    private String currency;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;
    private String method;
    private String version;
}