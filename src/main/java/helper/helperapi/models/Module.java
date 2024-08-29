package helper.helperapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "module")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    @Id
    private String id ;
    @NonNull
    private  String moduleName;

    private List <String> apiRoutes ;

    private  Date createdAt = new Date() ;


    public  Module (String moduleName , List<String> apiRoutes ){
         this.moduleName = moduleName ;
         this.apiRoutes = apiRoutes ;
    }

}
