package helper.helperapi.modelResponse;

import helper.helperapi.models.BetList;
import lombok.Data;

import java.util.List;

@Data
public class BetDataRes {
    private List<BetList> getbets;
    private int totalentry;

    public BetDataRes(List<BetList> getbets, int totalentry) {
        this.getbets = getbets;
        this.totalentry = totalentry ;

    }
}
