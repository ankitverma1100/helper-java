package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class GetEventResponse {
    private  int sportId ;
    private int eventId ;
    private  int seriesId ;

   public  GetEventResponse (int sportId , int eventId ,int seriesId){
        this.sportId = sportId ;
        this.eventId = eventId ;
        this.seriesId = seriesId ;
   }
}
