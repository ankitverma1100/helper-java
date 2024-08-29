package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "t_message_market_wise")
public class Message_market {
    @Id
    String id;
    private  int is_display_message =1;
    private String marketId ="TOP_MESSAGE";
    private String market_name ="TOP_MESSAGE";
    private  int matchId ;
    private String message ;

    public Message_market (int is_display_message ,String marketId ,String market_name , String message , int matchId){
        this.is_display_message = is_display_message ;
        this.market_name = market_name ;
        this.marketId = marketId ;
        this.message = message ;
        this.matchId = matchId;

    }
}
