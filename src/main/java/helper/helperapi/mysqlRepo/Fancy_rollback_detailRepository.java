package helper.helperapi.mysqlRepo;

import helper.helperapi.sqlModels.Fancy_rollback_detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Fancy_rollback_detailRepository extends JpaRepository<Fancy_rollback_detail,Integer> {
      Optional<Fancy_rollback_detail> findByMarketid(String marketid);
}
