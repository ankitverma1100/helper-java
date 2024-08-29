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
@Document(collection = "t_sport")
public class Sport {
    @Id
    String id;
    private  String name;
    private  int sportid  ;
    private  Boolean status ;
    private String adminid ;
    private String betlock ;

    private   Boolean isnew ;
    private  Boolean ismysqlupdated ;

    private  Boolean marketcount ;
    private  String type ;
    private Date createdon ;

    private  Date updatedon ;

     public  Sport (int sportid , Sport name ,Boolean status ,String betlock  ,String adminid ,Date createdon,Date updatedon ,Boolean ismysqlupdated , Boolean isnew ,Boolean marketcount,String type){
           this.name = String.valueOf(name);
           this.sportid = sportid ;
           this.status = status ;
           this.adminid=adminid ;
           this.ismysqlupdated = ismysqlupdated ;
           this.marketcount = marketcount ;
           this.type = type ;
           this.createdon = createdon ;
           this.updatedon = updatedon ;
     }
}
