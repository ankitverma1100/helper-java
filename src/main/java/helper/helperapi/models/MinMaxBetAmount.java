package helper.helperapi.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "t_minmaxbetamount")
public class MinMaxBetAmount {
    private  String amount ;
    private  String type ;
}
