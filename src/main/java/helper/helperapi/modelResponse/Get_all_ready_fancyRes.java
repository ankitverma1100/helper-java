package helper.helperapi.modelResponse;

import lombok.Data;

@Data
public class Get_all_ready_fancyRes {
    private  String  fancyid ;

    public Get_all_ready_fancyRes(String fancyid) {
        this.fancyid = fancyid;
    }
}
