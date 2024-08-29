package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class FetchtMarketRes {
    private int eventid;
    private String marketid;
    private String marketname;

    public FetchtMarketRes (int eventid
     , String marketid , String marketname){
        this.eventid = eventid;
        this.marketid= marketid;
        this.marketname = marketname;
    }
}
