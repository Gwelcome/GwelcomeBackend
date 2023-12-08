package backend.Gwelcome.dto.policy;

import lombok.Data;

@Data
public class PolicyRegisterDto {

    private String name;
    private String photo_url;
    private String policy_summary;
    private String city;
    private String organization;
    private String operation_period;
    private String age;
    private String introduction;
    private String income;
    private String job_state;
    private String restrict;
    private String apply_method;
    private String judge_presentation;
    private String website;
    private String extra_info;
    private String cs;
    private String policy_field;
}