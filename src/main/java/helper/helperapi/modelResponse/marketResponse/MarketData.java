package helper.helperapi.modelResponse.marketResponse;

import helper.helperapi.modelResponse.eventResponse.EventClass;
import lombok.Data;

import java.util.List;

@Data
public class MarketData {
    private List<MarketClass> gameList;
}
