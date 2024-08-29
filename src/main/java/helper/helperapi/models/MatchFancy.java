package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_matchfancy")
public class MatchFancy {
    @Id
    private String id;
    private String addby = "API";
    private int eventid;
    private String fancyid;
    private Boolean isactive = true;
    private String matchname;
    private String mtype = "player";
    private String name;
    private String oddstype;
    private String provider = "RS";
    private String remarks;
    private String runnerid;
    private String status = "true";
    private int sportid;
    private String suspendedby;
    private int betdelay = 0;
    private int maxbet;
    private int minbet;
    private Boolean is_show= true;
    private Boolean is_rolled_back = false;
    private Date createdon;
    private Date updatedon;

    public MatchFancy (  String addby,
                         Date createdon,
                         Date updatedon,
                         int eventid,
                         Boolean is_show,
                         String fancyid,
                         Boolean isactive,
                         String matchname,
                         String mtype,
                         String name,
                         String oddstype,
                         String provider,
                         String remarks,
                         String runnerid,
                         String status,
                         int sportid,
                         String suspendedby,
                         int betdelay,
                         int maxbet,
                         int minbet){
        this.addby = addby;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.eventid = eventid;
        this.is_show = is_show;
        this.fancyid = fancyid;
        this.isactive = isactive;
        this.matchname = matchname;
        this.mtype = mtype;
        this.name = name;
        this.oddstype = oddstype;
        this.provider = provider;
        this.remarks = remarks;
        this.runnerid = runnerid;
        this.status = status;
        this.sportid = sportid;
        this.suspendedby = suspendedby;
        this.betdelay = betdelay;
        this.maxbet = maxbet;
        this.minbet = minbet;
    }

}
