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
@Table(name = "t_fancyresult")
public class FancyResultsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int matchid;
    @Column
    private String matchname ;
    @Column
    private Boolean isresult =false;
    @Column
    private Boolean isprofitlossclear =false ;
    @Column
    private  String fancyid;
    @Column
     private String fancyname ;
    @Column
    private  int result ;
    @Column
    private Boolean resultstatuscron =false ;
    @Column
    private String resultdeclareby ;
    @Column
    private  int sportid = 4 ;
    @Column
    private  String sportname = "CRICKET";
    @Column
    private  String resultstatus = "OPEN";
    @Column
    private String fancytype ;
    @Column
    private Date createdon ;

    public FancyResultsModel (int matchid ,String matchname ,boolean isresult ,boolean isprofitlossclear  ,String fancyid ,int result,
    boolean resultstatuscron ,String resultdeclareby ,int sportid ,String fancytype ,String  fancyname, Date createdon,String sportname ,String resultstatus){
        this.matchid = matchid ;
        this.matchname = matchname ;
        this.isresult = isresult ;
        this.isprofitlossclear = isprofitlossclear;
        this.fancyid =fancyid ;
         this.fancyname = fancyname ;
        this.result = result ;
        this.resultstatuscron = resultstatuscron ;
        this.resultdeclareby = resultdeclareby ;
        this.sportid = sportid ;
        this.sportname = sportname;
        this.resultstatus =resultstatus ;
        this.fancytype = fancytype ;
        this.createdon = createdon ;

    }
}
