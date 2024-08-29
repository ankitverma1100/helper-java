package helper.helperapi.modelResponse;

import lombok.Data;

import java.util.Date;

@Data
public class AllReadRes {
    private  int sportid;
    private String createdon;

    public AllReadRes(int sportid, String createdon) {
        this.sportid= sportid;
        this.createdon = createdon;
    }
}
