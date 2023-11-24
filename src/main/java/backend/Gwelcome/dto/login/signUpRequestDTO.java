package backend.Gwelcome.dto.login;

import lombok.Data;

@Data
public class signUpRequestDTO {
    private String email;
    private String gender;
    private int age;
    private String livingArea;
}