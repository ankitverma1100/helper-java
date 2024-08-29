package helper.helperapi.modelResponse.GetFancyResponse;

import lombok.Data;

import java.util.Date;

@Data
public class SessionRes {
    private  int id ;
    private  int sid ;
    private  int eid ;
    private String marketId ;
    private String title ;
    private String info ;
    private  int game_over ;
    private int status;
    private String result;
    private int bet_allowed;
    private int suspended;
    private int ball_running;
    private  String updated_on;
    private String created_on;
    private int  is_commission;
    private int  com_suspended;
    private int  displayId;
    private String  mid;
    private int  min_stack;
    private int  max_stack;
    private int  max_profit_limit;
    private int  max_odd_limit;
    private int  bet_delay;
    private int  max_run_diff;
    private int  min_run_diff;
    private int  multiplier;
    private String  marketType;
    private String  dbStatus;
    private String  statusUpdatedAt;
    private int   __v;
}
