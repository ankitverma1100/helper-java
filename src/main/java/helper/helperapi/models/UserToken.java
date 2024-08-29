package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_usertoken")
public class UserToken {
    @Id
    String id;
    private  String accessToken ;
    private Boolean status;
    private String userid;
    private ZonedDateTime time;
}
