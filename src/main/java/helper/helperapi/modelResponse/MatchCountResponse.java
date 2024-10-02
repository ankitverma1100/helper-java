package helper.helperapi.modelResponse;

import lombok.Data;
@Data
public class MatchCountResponse {
    private Boolean betlock ;
    private Boolean fancylock  ;
    private long eventid;
    public  MatchCountResponse(Boolean betlock , Boolean fancylock , long eventid){
        this.betlock = betlock ;
        this.fancylock = fancylock ;
        this.eventid = eventid ;
    }


}
