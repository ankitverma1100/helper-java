package helper.helperapi.mysqlRepo;

import helper.helperapi.sqlModels.MatchResultModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchResultsRepository extends JpaRepository<MatchResultModel,Integer> {
}
