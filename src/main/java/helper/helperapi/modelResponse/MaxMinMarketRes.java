package helper.helperapi.modelResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MaxMinMarketRes {
    private String marketname;
    private String marketid;
    private int max_bet_rate;
    private int min_bet_rate ;
    private int betdelay;
    private int maxbet ;
    private int minbet ;
    private Boolean issusended ;
    private String display_message ;
    private Boolean isactive ;
    private Boolean status ;
    private String matchname ;
    private Boolean betlock ;


    public MaxMinMarketRes(String marketname, String marketid, Boolean betlock, String displayMessage, int maxbet, int min_bet_rate, int betdelay, int minbet,String matchname) {
        this.marketname = marketname ;
        this.marketid = marketid ;
        this.display_message = displayMessage ;
        this.maxbet = maxbet ;
        this.min_bet_rate = min_bet_rate;
        this.betlock = betlock ;
        this.betdelay = betdelay ;
        this.minbet = minbet ;
        this.matchname = matchname ;

    }
}
