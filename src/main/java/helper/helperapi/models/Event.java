package helper.helperapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "t_event")
public class Event {
    @Id
    String id;
    private Boolean betlock = false ;
    private Boolean fancylock =false ;
    private int eventid;
    private String eventname ;
    private  Boolean is_redis_updated=false ;
    private  Boolean isactive  = true ;
    private  Boolean fancypause  =false ;
    private  Boolean livetv ;
    private String open_date ;
    private String matchstartdate ;
    private  int seriesid ;
    private int sportid ;
    private Boolean status ;
    private  Boolean in_play ;
    private  Boolean bookmaker =true;
    private  Boolean fancy ;
    private String channel_id ;
    private ArrayList<String> marketIds;
    private  Date updatedon ;
    private Date createdon;

    public Event (Boolean betlock,Boolean fancylock,Date createdon ,int eventid ,String eventname,Boolean is_redis_updated,
                  Boolean isactive,Boolean fancypause,Boolean livetv,String open_date,String matchstartdate,int seriesid,int sportid,
                  Boolean status, Date updatedon,Boolean in_play,boolean fancy,Boolean bookmaker,String channel_id){
        this.betlock = betlock ;
        this.createdon = createdon ;
        this.eventname = eventname ;
        this.isactive = isactive ;
        this.fancylock = fancylock ;
        this.fancypause = fancypause ;
        this.is_redis_updated = is_redis_updated;
        this.livetv = livetv ;
        this.open_date = open_date ;
        this.matchstartdate = matchstartdate ;
        this.seriesid = seriesid ;
        this.sportid = sportid ;
        this.status = status ;
        this.updatedon = updatedon ;
        this.channel_id= channel_id;
        this.in_play = in_play ;
        this.fancy = fancy ;
        this.eventid = eventid ;
        this.bookmaker = bookmaker ;

    }


}
