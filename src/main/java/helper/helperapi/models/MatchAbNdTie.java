package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "t_matchabondendtie")
public class MatchAbNdTie {
  private  String marketid;
  private String marketname;
  private  int matchid ;
  private  String matchname;
  private int sportid ;
  private String sportname ;
  private String result ;
  private String declared_by;
  private Boolean status;
  private String date ;
}
