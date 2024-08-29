package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_market")
public class Market {
    @Id
    private String id;
    private int betdelay;
    private int eventid;
    private Boolean inplay = false;
    private Boolean is_redis_updated = false;
    private Boolean isactive = true;
    private String marketid;
    private String marketname;
    private String matchname;
    private int max_bet_rate = 0;
    private int maxbet = 0;
    private int min_bet_rate = 0;
    private int minbet = 0;
    private int seriesid;
    private int sportid;
    private String startdate;
    private String opendate;
    private Boolean status = true;
    private String display_message = "null";
    private Boolean issuspended = false;
    private Boolean is_rolled_back = false;
    private int maxLiabilityPerMarket = 0;
    private Boolean betlock = false;
    private int count;
    private int count1;
    private Date createdon;
    private Date updatedon;

    public Market(int betdelay, Date createdon, int eventid, Boolean inplay, Boolean is_redis_updated,
                  Boolean isactive, String marketid, String marketname, String matchname, int max_bet_rate, int maxbet
            , int min_bet_rate, int minbet, String opendate, Date updatedon, int sportid, int seriesid, Boolean status,
                  String display_message, Boolean is_rolled_back, int maxLiabilityPerMarket, Boolean betlock,String startdate,
                  Boolean issuspended
    ) {
      this.betdelay = betdelay ;
      this.createdon = createdon ;
      this.eventid = eventid ;
      this.inplay = inplay ;
      this.is_redis_updated = is_redis_updated ;
      this.isactive = isactive ;
      this.marketid = marketid ;
      this.marketname = marketname ;
      this.matchname = matchname ;
      this.max_bet_rate = max_bet_rate;
      this.minbet = minbet ;
      this.opendate = opendate ;
      this.seriesid = seriesid ;
      this.sportid = sportid ;
      this.updatedon = updatedon ;
      this.min_bet_rate =min_bet_rate;
      this.display_message = display_message ;
      this.is_rolled_back = is_rolled_back ;
      this.maxLiabilityPerMarket = maxLiabilityPerMarket ;
      this.betlock = betlock ;
      this.maxbet = maxbet ;
      this.status = status ;
      this.startdate = startdate ;
      this.issuspended = issuspended ;
    }
}
