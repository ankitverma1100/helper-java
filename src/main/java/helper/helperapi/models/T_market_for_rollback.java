package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "t_marketid_for_rollback")
public class T_market_for_rollback {
    @Id
    String id;
    private  String marketid;
    private  String markettype="MARKET";
    private String status ="ROLLBACK";
}
