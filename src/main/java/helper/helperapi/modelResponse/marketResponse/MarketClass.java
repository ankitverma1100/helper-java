package helper.helperapi.modelResponse.marketResponse;

import helper.helperapi.modelResponse.eventResponse.EventDataClass;
import lombok.Data;

import java.util.List;

@Data
public class MarketClass {
    private List<MarketDataClass> eventList;
}
