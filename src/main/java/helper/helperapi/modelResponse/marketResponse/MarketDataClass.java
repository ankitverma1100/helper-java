package helper.helperapi.modelResponse.marketResponse;

import helper.helperapi.modelResponse.eventResponse.EventDataClass;
import helper.helperapi.modelResponse.eventResponse.GetEventResponse;
import lombok.Data;

@Data
public class MarketDataClass  extends EventDataClass {
    private MarketList marketList;
}
