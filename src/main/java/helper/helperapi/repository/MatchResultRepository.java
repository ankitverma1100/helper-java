package helper.helperapi.repository;

import helper.helperapi.models.MatchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MatchResultRepository extends MongoRepository<MatchResult,String> {
    Optional<MatchResult> findByMarketid (String marketid);

}
