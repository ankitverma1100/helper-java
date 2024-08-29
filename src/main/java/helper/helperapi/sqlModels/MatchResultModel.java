package helper.helperapi.sqlModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_matchresult")
public class MatchResultModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Boolean isresult ;
    private Boolean ismysqlupdated ;
    @Column(name = "marketid")
    private  String marketid ;
    @Column(name = "marketname")
    private String marketname;
    @Column(name = "markettype")
    private  String markettype;
    @Column(name = "matchid")
    private int matchid ;
    @Column(name = "matchname")
    private String matchname ;
    @Column(name = "result")
    private int result ;
    @Column(name = "resultstatuscron")
    private Boolean resultstatuscron;
    @Column(name = "resultstatus")
    private  String resultstatus = "OPEN";
    @Column(name = "selectionid")
    private int selectionid;
    @Column(name = "selectionname")
    private String selectionname;
    @Column(name = "sportid")
    private int sportid;
//    @Column(name = "sportname")
//    private String sportname;
    @Column(name = "status")
    private Boolean status= true ;
    @Column(name = "type")
    private String type ="result";
    @Column(name = "declared_by")
    private String declared_by ;
    @Column(name = "date")
    private String date ;
}
