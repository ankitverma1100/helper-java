package helper.helperapi.sqlModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_marketid_for_rollback")
public class Market_for_rollback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String marketid;
    private  String markettype="MARKET";
    private String status ="ROLLBACK";
}
