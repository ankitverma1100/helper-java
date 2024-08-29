package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "t_rsfancy_updated_result")
public class T_rsFancy_updated_result {
    @Id
    String id;
    private   int matchid ;
    private  String fancyid;
    private  String result;
    private Date createdon ;
}
