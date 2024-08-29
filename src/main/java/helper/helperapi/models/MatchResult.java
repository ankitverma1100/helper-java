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
@Document(collection = "t_matchresult")
public class MatchResult {
    @Id
    String id;
    private Boolean isresult ;
    private Boolean Ismysqlupdated ;
    private  String marketid ;
    private String marketname;
    private  String markettype;
    private int matchid ;
    private String matchname ;
    private int result ;
    private Boolean resultstatuscron;
    private  String resultstatus = "OPEN";
    private int selectionid;
    private String selectionname;
    private int sportid;
    private String sportname;
    private Boolean status= true ;
    private String type ="result";
    private String declared_by ;
    private String date ;

}
