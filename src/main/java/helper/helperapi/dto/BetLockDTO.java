package helper.helperapi.dto;

import lombok.Data;

@Data
public class BetLockDTO {
    private String marketid ;
    private  int eventid ;
    private  Boolean betlock;
}
