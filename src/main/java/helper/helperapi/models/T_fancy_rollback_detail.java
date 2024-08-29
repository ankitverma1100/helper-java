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
@Document(collection = "t_fancy_rollback_detail")
public class T_fancy_rollback_detail {
    @Id
    String id;
    private String donby;
    private  String marketid;
    private String status = "ROLLBACK";
    private Date createdon;
}
