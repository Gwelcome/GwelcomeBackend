package backend.Gwelcome.dto.toss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyDonateResponseDTO {
    private String username;
    private Long myDonateTotal;
    private List<MyDonate> myDonates;

}
