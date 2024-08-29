package helper.helperapi.modelResponse.addMarketRespone;
import helper.helperapi.modelResponse.eventResponse.EventDataClass;
import lombok.Data;

@Data
public class MarketDataClass extends EventDataClass {
    private MarketList marketList;
}
