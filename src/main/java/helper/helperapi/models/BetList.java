package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "t_betlist")
public class BetList {
// private  String marketid ;
// private  String matchid ;
// private  String isactive ;
// private String userid ;
// private  int pricevalue ;
// private  String status ;
// private int odds ;
// private String selectionname;
// private Date createdon ;
// private Boolean isback ;
// private int pnl ;
// private int liability ;
// private int stake ;
// private String date ;
// //
 private String id;
 private String createdon;
 private String userid;
 private double pricevalue;
 private  String status ;
 private double odds;
 private String selectionname;
 private String marketname;
 private double stake;
 private boolean isback;
 private double pnl;
 private double liability;
 private String matchid;
 private String marketid;
 private boolean isactive;
 private String date;

}
