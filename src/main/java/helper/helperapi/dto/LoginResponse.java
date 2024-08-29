package helper.helperapi.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private int   id;
    private String   userid;
    private String   password;
    private Boolean   accountlock;
    private Boolean   active;
    private int   usertype;

}

