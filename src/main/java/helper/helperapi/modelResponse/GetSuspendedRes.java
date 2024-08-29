package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class GetSuspendedRes {
    private Boolean issuspended;
    private String marketid;

    public GetSuspendedRes(Boolean issuspended, String marketid) {
        this.issuspended = issuspended;
        this.marketid = marketid ;
    }
}
