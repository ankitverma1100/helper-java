package helper.helperapi.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_fancyresult")
public class FancyResult {
    @Id
    String id;
    private int matchid;
    private String matchname ;
    private Boolean isresult =false;
    private Boolean isprofitlossclear =false ;
    private  String fancyid;
    private String fancyname ;
    private  int result ;
    private Boolean resultstatuscron =false ;
    private String resultdeclareby ;
    private  int sportid = 4 ;
    private  String sportname = "CRICKET";
    private  String resultstatus = "OPEN";
    private String fancytype ;
    private Date createdon ;
}
