package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.MatchAbondendTie;
import helper.helperapi.models.MatchAbNdTie;
import helper.helperapi.sqlModels.MatchAbNdTieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchAbNdRepository extends JpaRepository<MatchAbNdTieModel,Integer> {
    Optional<MatchAbNdTieModel> findByMarketid (String marketid);

    List<MatchAbNdTieModel> findBySportid(int sportId);
}
