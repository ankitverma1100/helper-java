package helper.helperapi.mysqlRepo;

import helper.helperapi.models.MatchResult;
import helper.helperapi.sqlModels.MatchResultModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchResultModelRepository extends JpaRepository<MatchResultModel,Integer> {
    Optional<MatchResultModel> findByMarketid (String marketid);
}
