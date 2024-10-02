package helper.helperapi.modelResponse;

import lombok.Data;

import java.util.Date;

@Data
public class AllReadRes {
    private  int sportid;
//    private String createdon;

    public AllReadRes(int sportid) {
        this.sportid= sportid;
//        this.createdon = createdon;
    }
}
