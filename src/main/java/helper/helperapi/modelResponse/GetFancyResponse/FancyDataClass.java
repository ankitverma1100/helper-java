package helper.helperapi.modelResponse.GetFancyResponse;

import helper.helperapi.modelResponse.eventResponse.EventDataClass;
import lombok.Data;

@Data
public class FancyDataClass extends EventDataClass {
    private FancyMarketList marketList;
}
