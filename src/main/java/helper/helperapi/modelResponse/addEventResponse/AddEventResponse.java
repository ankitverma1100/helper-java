package helper.helperapi.modelResponse.addEventResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AddEventResponse {
    private int compId ;
    private  int sportId ;
    private  String league ;
    private  Boolean status ;
    private  Boolean is_redis_updated ;
    private  Boolean isTv ;
    private int eventId ;
    private  String name ;
    private Boolean betLock;
    private Boolean fancyLock;
    private  Boolean in_play ;
    private  Boolean bookmaker ;
    private  int fancy ;
    private Date open_date ;
    private  String eventName ;


    public AddEventResponse(String league, int compId , int sportId , Boolean status , Boolean isTv, int eventId,String name
    ,Boolean betLock ,Boolean fancyLock,Boolean bookmaker,int fancy,Boolean is_redis_updated,Date open_date,String eventName){
        this.compId = compId ;
        this.league = league ;
        this .status = status ;
        this.isTv = isTv ;
        this.sportId = sportId ;
        this.name = name ;
        this.betLock = betLock ;
        this.fancyLock = fancyLock ;
        this.bookmaker = bookmaker ;
        this.is_redis_updated = is_redis_updated ;
        this.open_date = open_date ;
        this.eventName = eventName ;


    }

}
