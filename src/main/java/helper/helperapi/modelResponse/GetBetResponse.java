package helper.helperapi.modelResponse;
import lombok.Data;
@Data
public class GetBetResponse {
    private String id;
    private String createdon;
    private String userid;
    private double pricevalue;
    private  String status ;
    private double odds;
    private String selectionname;
    private double stake;
    private boolean isback;
    private double pnl;
    private double liability;
    private String matchid;
    private String marketid;
    private boolean isactive;
    private String date;
    public GetBetResponse(String createdon , String userid , double pricevalue , String status , double odds , String selectionname
    , double stake , boolean isback , double pnl , double liability , String matchid , String marketid , boolean isactive, String date, String dataStatus){
        this.createdon =createdon ;
        this.userid = userid ;
        this.pricevalue = pricevalue ;
        this.status = status ;
        this.odds = odds;
        this.selectionname = selectionname ;
        this.isback = isback ;
        this.pnl = pnl ;
        this.liability = liability ;
        this.marketid = marketid;
        this.matchid = matchid ;
        this.isactive = isactive ;
        this.date= date ;
        this.stake =stake ;

    }


}
