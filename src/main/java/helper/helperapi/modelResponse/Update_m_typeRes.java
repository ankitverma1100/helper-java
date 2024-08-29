package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class Update_m_typeRes {
    private  int eventId ;
    private  String  fancyId ;
    private String mType;

    public Update_m_typeRes(int eventId,String fancyId,String mType){
        this.eventId=eventId;
        this.fancyId = fancyId ;
        this.mType = mType ;
    }
}
