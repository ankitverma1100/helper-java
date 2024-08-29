package helper.helperapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_minmaxbetamount")
public class MinMaxBetAmountSql {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private  int amount ;
        private  String type ;

}
