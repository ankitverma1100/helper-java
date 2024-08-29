package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class AllReadSuspendedRes {
    private String marketid;
    public AllReadSuspendedRes (String marketid){
        this.marketid = marketid;

    }
}
