package helper.helperapi.modelResponse;

import lombok.Data;
@Data
public class MatchCountResponse {
    private Boolean betlock ;
    private Boolean fancylock  ;
    private int eventid;
    public  MatchCountResponse(Boolean betlock , Boolean fancylock , int eventid){
        this.betlock = betlock ;
        this.fancylock = fancylock ;
        this.eventid = eventid ;
    }


}
