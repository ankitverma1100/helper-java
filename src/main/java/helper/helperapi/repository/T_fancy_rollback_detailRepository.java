package helper.helperapi.repository;

import helper.helperapi.models.T_fancy_rollback_detail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface T_fancy_rollback_detailRepository extends MongoRepository <T_fancy_rollback_detail,String> {
       Optional<T_fancy_rollback_detail> findByMarketid(String marketid);
}
