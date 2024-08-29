package helper.helperapi.models;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "t_selectionid")
public class Selection {
    @Id
    String id;
  private  String marketid ;
  private  String runner_name ;
  private  int selectionid ;
  private Date createdon ;
  private Boolean is_redis_updated = false;
  public  Selection (String marketid ,String runner_name ,int selectionid ,Date createdon ,Boolean is_redis_updated){
      this.marketid = marketid ;
      this.runner_name = runner_name ;
      this.selectionid = selectionid ;
      this.createdon = createdon ;
      this.is_redis_updated = is_redis_updated ;

  }
}
