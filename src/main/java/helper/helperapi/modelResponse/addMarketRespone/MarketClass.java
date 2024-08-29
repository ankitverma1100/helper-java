package helper.helperapi.modelResponse.addMarketRespone;
import lombok.Data;

import java.util.List;

@Data
public class MarketClass {
    private List<MarketDataClass> eventList;
}
