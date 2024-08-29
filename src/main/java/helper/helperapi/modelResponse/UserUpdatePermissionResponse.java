package helper.helperapi.modelResponse;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePermissionResponse {
    @Id
    private  String id ;
    private String userId;
    private List<String> modules;


}
