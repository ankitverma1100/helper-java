package helper.helperapi.sqlModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_matchabondendtie")
public class MatchAbNdTieModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
