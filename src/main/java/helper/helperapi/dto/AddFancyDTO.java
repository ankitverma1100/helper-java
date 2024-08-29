package helper.helperapi.dto;

import lombok.Data;

@Data
public class AddFancyDTO {
    private  int sportid ;
    private  int eventid ;
    private String marketid ;
    private int maxbet ;
    private int minbet ;
}
