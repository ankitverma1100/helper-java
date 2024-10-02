package helper.helperapi.sqlModels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BetLockResponse {

    private String marketname;
    private int matchid;
}
