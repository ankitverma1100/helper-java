package helper.helperapi.modelResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
public class UpdatedAccountActiveResponse {
    @Id
    private  String id ;
    private  String userId ;
    private  Boolean active ;


    public UpdatedAccountActiveResponse(String id, String userId, Boolean active) {
        this.id = id ;
        this.active =active ;
        this.userId = userId ;
    }
}
