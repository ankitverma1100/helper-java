package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class Fancy_mTypeRes {
    private  int eventId ;
    private  String  fancyId ;
    private String mType ;
    private String name ;
    private  int maxBet ;
    private  int minBet ;
    private Boolean isShow ;
    private int betDelay ;

    public Fancy_mTypeRes (int eventId , String fancyId ,String name , String mType ,int maxBet , int minBet ,boolean isShow ,int betDelay){
        this.eventId = eventId ;
        this.fancyId = fancyId ;
        this.maxBet = maxBet ;
        this.minBet = minBet ;
        this.isShow = isShow ;
        this.betDelay = betDelay ;
        this.name = name ;
        this.mType = mType ;
    }
}
