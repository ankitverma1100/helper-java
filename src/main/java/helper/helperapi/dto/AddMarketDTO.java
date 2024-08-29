package helper.helperapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddMarketDTO {
    @NotEmpty(message = "The sporid is required.")
    private  int sportid ;
    @NotEmpty(message = "The eventid is required.")
    private  int eventid ;
    @NotEmpty(message = "The marketId is required.")
    private String marketid ;
    @NotEmpty(message = "The maxbet is required.")
    private int maxbet = 0;
    @NotEmpty(message = "The minbet is required.")
    private int minbet = 0;
    @NotEmpty(message = "The maxLiabilityPerMarket is required.")
    private int maxLiabilityPerMarket = 0;
    @NotEmpty(message = "The max_bet_rate is required.")
    private int max_bet_rate = 0;
    @NotEmpty(message = "The min_bet_rate is required.")
    private int min_bet_rate = 0;
}
