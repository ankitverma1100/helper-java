package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class MarketBetLockRes {
    private String marketName;
    private String matchid;

    public MarketBetLockRes(String marketName,String matchid){
        this.marketName= marketName;
        this.matchid = matchid;
    }
}
