package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class GetAllUpdateFancyRes {
    private  int eventid ;
    private String oddstype ;
    private  String  fancyid ;
    private int maxbet ;
    private int minbet ;
    private int betdelay ;

    public GetAllUpdateFancyRes(int eventid ,String fancyid , String oddstype , int minbet , int maxbet , int betdelay){
        this.eventid = eventid ;
        this.fancyid = fancyid ;
        this.betdelay = betdelay ;
        this.minbet = minbet ;
        this.maxbet = maxbet ;
        this.oddstype = oddstype ;
    }
}
