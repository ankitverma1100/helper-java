package helper.helperapi.dto;

import lombok.Data;

@Data
public class AddMessageDTO {
    private  int is_display_message =1;
    private String marketId ="TOP_MESSAGE";
    private String market_name ="TOP_MESSAGE";
    private  int matchId ;
    private String message ;
}
