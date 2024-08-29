package helper.helperapi.modelResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
public class UpdateAccountLockResponse {
    @Id
  private  String id ;
  private  String userId ;
  private  Boolean accountLock ;

    public UpdateAccountLockResponse(String id, String userId, Boolean accountLock) {
      this.id = id ;
      this.accountLock =accountLock ;
      this.userId = userId ;
    }
}
