package helper.helperapi.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class SportDTO {
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
}
