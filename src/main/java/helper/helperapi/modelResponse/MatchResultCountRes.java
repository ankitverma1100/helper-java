package helper.helperapi.modelResponse;

import lombok.Data;

import java.util.Date;

@Data
public class MatchResultCountRes {
    private int betdelay;
    private Date createdon;
    private int eventid;
    private Boolean inplay = false;
    private Boolean is_redis_updated = false;
    private Boolean isactive = false;
    private String marketid;
    private String marketname;
    private String matchname;
    private int max_bet_rate = 0;
    private int maxbet = 0;
    private String min_bet_rate = "0";
    private int minbet = 0;
    private Date opendate;
    private int seriesid;
    private int sportid;
    private int count ;
    private  int count1;
}
