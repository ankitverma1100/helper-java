package helper.helperapi.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_marketbetlock")
public class MarketBetLock {
    private String marketName;
    private String matchid ;
    private String userid ;
}
