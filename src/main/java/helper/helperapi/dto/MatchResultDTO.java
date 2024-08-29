package helper.helperapi.dto;

import lombok.Data;

@Data
public class MatchResultDTO {
    private int sportid ;
    private int eventid ;
    private String marketid ;
    private String selectionid;
    private String result ;
    private String markettype;
}
