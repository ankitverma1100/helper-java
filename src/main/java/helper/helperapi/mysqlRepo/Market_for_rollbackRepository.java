package helper.helperapi.mysqlRepo;
import helper.helperapi.sqlModels.Market_for_rollback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Market_for_rollbackRepository extends JpaRepository<Market_for_rollback,Integer> {
    Optional<Market_for_rollback> findByMarketid(String marketid);
}
