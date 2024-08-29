package helper.helperapi.modelResponse;

import helper.helperapi.dto.GetDisplayMessageDTO;
import lombok.Data;

@Data
public class GetDisplayMessageRes {
    private int eventid ;
    private String marketid ;
    private String display_message ;
    public GetDisplayMessageRes (int eventid , String marketid ,String display_message){
        this.display_message = display_message ;
        this.eventid = eventid ;
        this.marketid = marketid ;
    }
}
