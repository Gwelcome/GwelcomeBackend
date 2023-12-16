package backend.Gwelcome.dto.policy;

import backend.Gwelcome.model.Policy;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MyPolicyResponseDTO {
    private Long id;
    private String title;
    private String intro;
    private String image_url;
    private String policy_field;
    private String d_day;

    public MyPolicyResponseDTO(Policy policy){
        id = policy.getId();
        title = policy.getName();
        intro = policy.getIntroduction();
        image_url = policy.getPhoto_url();
        policy_field = policy.getPolicy_field();
        d_day = D_DAY(policy.getOperation_period());
    }

    private static String D_DAY(String finalDay) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(finalDay);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date now = new Date();
        long remainDate;
        if (date.getTime() < now.getTime()) {
            remainDate = 0;
        }
        long diffSec = (date.getTime() - now.getTime()) / 1000;
        remainDate = diffSec / (24 * 60 * 60);
        return "D-"+String.valueOf(remainDate);
    }
}