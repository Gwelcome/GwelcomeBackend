package backend.Gwelcome.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EasyPay {
    private String provider;
    private int amount;
    private int discountAmount;
}