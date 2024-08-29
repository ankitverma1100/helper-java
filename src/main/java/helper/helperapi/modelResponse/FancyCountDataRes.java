package helper.helperapi.modelResponse;

import helper.helperapi.entity.EXMatchBet;
import helper.helperapi.models.BetList;
import lombok.Data;

import java.util.List;

@Data
public class FancyCountDataRes {
    private List<EXMatchBet> OddEvencount;
    private int totalentry;
}
