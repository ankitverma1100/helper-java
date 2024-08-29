package helper.helperapi.sqlModels;

import helper.helperapi.models.MatchFancy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_matchfancy")
public class MatchFancyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Boolean is_show = true;
    private Boolean is_rolled_back = false;
    private String createdon;
    private String updatedon;

    public MatchFancyModel(
            String addby,
            String createdon,
            String updatedon,
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
            int minbet
    ) {
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

    public MatchFancyModel(MatchFancy matchFancy) {

    }
}
