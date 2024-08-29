package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class Max_min_fancyRes {
    private int maxBet ;
    private int minBet ;

    private  int eventId ;

    private String oddsType ;

    private int betDelay ;


    public  Max_min_fancyRes ( int minBet , int maxBet , int eventId , String oddsType , int betDelay){
        this.minBet = minBet ;
        this.maxBet = maxBet ;
        this.eventId = eventId ;
        this.oddsType = oddsType ;
        this.betDelay = betDelay ;

    }
}
