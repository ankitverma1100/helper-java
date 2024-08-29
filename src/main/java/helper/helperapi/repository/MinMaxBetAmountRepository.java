package helper.helperapi.repository;

import helper.helperapi.models.MinMaxBetAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MinMaxBetAmountRepository extends MongoRepository<MinMaxBetAmount,String> {
    List<MinMaxBetAmount> findByTypeIn(List<String> types);

}
