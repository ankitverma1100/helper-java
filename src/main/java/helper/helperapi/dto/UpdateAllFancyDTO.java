package helper.helperapi.dto;

import lombok.Data;

@Data
public class UpdateAllFancyDTO {
    private  int eventid ;
    private String oddstype ;
    private  String  fancyid ;
    private int maxbet ;
    private int minbet ;
    private int betdelay ;
}
