package helper.helperapi.modelResponse.sportResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetSportResponse {
    private  int sportId ;
    private  String name ;
    private  String sportname ;


    public  GetSportResponse (int sportId , String name, String sportsName ){
         this.sportId = sportId ;
         this.name = name ;
         this.sportname=sportsName;
    }
}
