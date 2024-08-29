package helper.helperapi.repository;

import helper.helperapi.models.FancyResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FancyResultRepository extends MongoRepository<FancyResult,String> {
    Optional<FancyResult> findByFancyid (String fancyid);

}
