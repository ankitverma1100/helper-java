package helper.helperapi.repository;

import helper.helperapi.models.MatchAbNdTie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MatchAbNdTieRepository extends MongoRepository<MatchAbNdTie ,String> {
     Optional<MatchAbNdTie>findByMarketid (String marketid);

     List<MatchAbNdTie>findBySportid (int sportid);
}
