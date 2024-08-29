package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class UpdateOneFancyRes {
    private  int eventid ;
    private  String  fancyid ;
    private int maxbet ;
    private int minbet ;
    private int betdelay ;

    public UpdateOneFancyRes(String fancyid, int eventid, int betdelay, int minbet, int maxbet) {
        this.betdelay = betdelay;
        this.fancyid = fancyid ;
        this.eventid = eventid ;
        this.maxbet = maxbet ;
        this.minbet = minbet ;
    }
}
