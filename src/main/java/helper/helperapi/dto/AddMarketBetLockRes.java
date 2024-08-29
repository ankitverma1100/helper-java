package helper.helperapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddMarketBetLockRes {
    @NotNull(message = "marketName must not be null")
    private String marketname ;
    @NotNull(message = "matchId must not be null")
    private int matchid;

}
