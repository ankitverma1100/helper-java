package helper.helperapi.dto;

import lombok.Data;

@Data
public class UpdateMarketDTO {
    private String marketid;
    private int eventid;
    private int max_bet_rate;
    private int maxbet;
    private int min_bet_rate ;
    private int minbet;
    private int maxLiabilityPerMarket ;
    private Boolean betlock ;
}
