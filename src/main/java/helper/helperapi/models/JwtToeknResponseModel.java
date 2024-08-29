package helper.helperapi.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtToeknResponseModel implements Serializable {

    private static final long serialVersionUID = -6918982347802336760L;

    private String userid;
    private long expireTime;
    private int userType, id;
    private boolean isFirstLogin;
}