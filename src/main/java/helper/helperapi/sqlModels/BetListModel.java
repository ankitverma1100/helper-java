package helper.helperapi.sqlModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_betlist")
public class BetListModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String createdon;
    private String userid;
    private double pricevalue;
    private  String status ;
    private double odds;
    private String selectionname;
    private double stake;
    private boolean isback;
    private double pnl;
    private double liability;
    private String matchid;
    private String marketid;
    private boolean isactive;
    private String date;

}
