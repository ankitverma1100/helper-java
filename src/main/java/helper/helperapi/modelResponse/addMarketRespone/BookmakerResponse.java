package helper.helperapi.modelResponse.addMarketRespone;

import lombok.Data;

import java.util.List;

@Data
public class BookmakerResponse {
    public int id;
    public int sid;
    public int eid;
    public String marketId;
    public String title;
    public String info;
    public int game_over;
    public String result;
    public int bet_allowed;
    public int suspended;
    public int ball_running;
    public String created_on;
    public String updated_on;
    public int status;
    public int zero_suspended;
    public int is_commission;
    public int is_manual;
    public int is_special;
    public String mid;
    public int min_stack;
    public int max_stack;
    public int max_profit_limit;
    public int max_odd_limit;
    public int bet_delay;
    public int max_run_diff;
    public int min_run_diff;
    private String runners;
}
