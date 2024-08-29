package helper.helperapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginTokenDTO {
    private String userId;
//    private List<String> modules;
    private String accessToken;
    private String refreshToken;
}
