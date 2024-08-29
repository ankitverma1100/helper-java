package helper.helperapi.modelResponse.GetFancyResponse;

import lombok.Data;

import java.util.List;
@Data
public class FancyMarketList{
    private List<Other_marketRes> other_market ;
    private List<SessionRes>session ;
    private List<Odd_evenRes> odd_even ;
}
