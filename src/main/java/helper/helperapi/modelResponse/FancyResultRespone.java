package helper.helperapi.modelResponse;

import lombok.Data;

import java.util.Date;

@Data
public class FancyResultRespone {
    private int matchid;
    private String matchname ;
    private Boolean isresult =false;
    private Boolean isprofitlossclear =false ;
    private  String fancyid;
    private String fancyname ;
    private  int result ;
    private Boolean resultstatuscron =false ;
    private String resultdeclareby ;
    private  int sportid = 4 ;
    private  String sportname = "CRICKET";
    private  String resultstatus = "OPEN";
    private String fancytype ;
    private String donby;
    private Date createdon ;
}
