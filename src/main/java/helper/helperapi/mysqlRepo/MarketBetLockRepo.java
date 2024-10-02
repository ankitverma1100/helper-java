package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.MarketBetLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketBetLockRepo extends JpaRepository<MarketBetLock,Integer> {

    MarketBetLock deleteByMarketNameAndMatchId(String marketName , String matchid);

    MarketBetLock findByMarketNameAndMatchId(String marketName , String matchid);

    List<MarketBetLock> findByMatchId(int eventid);



}
