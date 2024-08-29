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
@Table(name = "t_fancy_rollback_detail")
public class Fancy_rollback_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String donby;
    private  String marketid;
    private String status = "ROLLBACK";
    private Date createdon;
}
