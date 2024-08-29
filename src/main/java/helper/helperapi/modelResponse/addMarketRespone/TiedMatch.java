package helper.helperapi.modelResponse.addMarketRespone;

import com.fasterxml.jackson.core.Base64Variant;
import lombok.Data;

import java.util.List;

@Data
public class TiedMatch {
    private  int sid ;
    private int id ;
    private long eid;
    private String marketId;
    private String title;
    private String info;
    private int game_over;
    private String result;
    private String slug;
    private int bet_allowed;
    private int suspended;
//    private  String runners ;
    private int ball_running;
    private String created_on;
    private String updated_on;
    private int status;
    private String market_time;
    private String mid;
    private int min_stack;
    private int max_stack;
    private int max_profit_limit;
    private int max_odd_limit;
    private int bet_delay;
    private int max_run_diff;
    private int min_run_diff;
    private int tNum;
    private  String runners ;

}
