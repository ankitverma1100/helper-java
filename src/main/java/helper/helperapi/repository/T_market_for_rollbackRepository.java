package helper.helperapi.repository;


import helper.helperapi.models.T_market_for_rollback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface T_market_for_rollbackRepository extends MongoRepository<T_market_for_rollback,String> {
    Optional<T_market_for_rollback> findByMarketid(String marketid);
}