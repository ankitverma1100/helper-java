package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class FancyCountRes {
    private String id;
    private String createdon;
    private String userid;
    private double pricevalue;
    private  String status ;
    private double odds;
    private String selectionname;
    private String marketname;
    private double stake;
    private boolean isback;
    private double pnl;
    private double liability;
    private String matchid;
    private String marketid;
    private boolean isactive;
    private String date;
}
