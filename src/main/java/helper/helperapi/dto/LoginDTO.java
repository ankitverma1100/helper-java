package helper.helperapi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDTO {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
