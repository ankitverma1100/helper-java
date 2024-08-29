package helper.helperapi.modelResponse.addMarketRespone;
import lombok.Data;

import java.util.List;

@Data
public class MarketList {
    private MatchOdd match_odd;
    private CompletedMatch completed_match;
    private TiedMatch tied_match;
    private List<BookmakerResponse> bookmaker ;
}
