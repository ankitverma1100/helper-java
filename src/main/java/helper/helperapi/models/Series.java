package helper.helperapi.models;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "t_series")
public class Series {
    @Id
    String id;
    private String adminid = "null";
    private String appid = "null";
    private Boolean isactive = true;
    private Date createdon;
    private Date updatedon;
    private String marketcount= "0";
    private int seriesid;
    private String seriesname;
    private int sportid;
    private Boolean status;

    public Series(String adminid, String appid, Boolean isactive, Date createdon, Date updatedon, String marketcount,
                  int seriesid,
                  String seriesname, int sportid, Boolean status) {
        this.adminid = adminid;
        this.appid = appid;
        this.isactive = isactive;
        this.createdon = createdon;
        this.updatedon = updatedon;
        this.marketcount = marketcount;
        this.seriesid = seriesid;
        this.seriesname = seriesname;
        this.status = status;
        this.sportid = sportid;

    }
}

