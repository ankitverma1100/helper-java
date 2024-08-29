package helper.helperapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFancyDTO {
    private  int eventid ;
    private String oddstype ;
    private  String  fancyid ;
    private int maxbet ;
    private int minbet ;
    private int betdelay ;
}
