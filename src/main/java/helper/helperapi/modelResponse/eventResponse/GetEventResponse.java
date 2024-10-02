package helper.helperapi.modelResponse.eventResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GetEventResponse {
    private  long eventId ;
    private  String name ;
    private  String league ;
    private int sportId;
    private int compId ;
    private  Boolean status ;
    private  Boolean isTv ;
    private Boolean fancy;
    private String type ;
    private String created_on;


    public  GetEventResponse (long eventId ,String created_on ,String type,  String name , String league,int sportId, int compId,boolean status ,boolean isTv,
     boolean fancy){
         this.eventId = eventId ;
         this.name = name ;
         this.league = league ;
         this.sportId =sportId;
         this.compId = compId;
         this.status = status ;
         this.isTv = isTv ;
         this.fancy=fancy;
         this.type =type ;
         this.created_on = created_on;
    }
}
