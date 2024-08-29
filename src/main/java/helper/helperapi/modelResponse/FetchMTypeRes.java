package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class FetchMTypeRes {
    private  int eventid ;
    private  String name ;
    private  String fancyid ;
    private  String mtype;
    private int maxbet ;
    private  int minbet ;
    private int betdelay;
    private Boolean is_show;
    private String matchname;

    public FetchMTypeRes (int eventid,String name,String fancyid,String mtype,int maxbet,int minbet ,int betdelay ,boolean is_show,String matchname){
          this.eventid =eventid;
          this.name = name;
          this.fancyid = fancyid;
          this.mtype = mtype ;
          this.maxbet = maxbet;
          this.minbet = minbet;
          this.betdelay= betdelay;
          this.is_show = is_show;
          this.matchname = matchname;
    }

}
