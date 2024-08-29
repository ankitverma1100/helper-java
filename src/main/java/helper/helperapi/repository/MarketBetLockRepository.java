package helper.helperapi.repository;

import helper.helperapi.models.MarketBetLock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketBetLockRepository extends MongoRepository<MarketBetLock,String> {
    MarketBetLock deleteByMarketNameAndMatchid(String marketName , String matchid);

    MarketBetLock findByMarketNameAndMatchid(String marketName ,String matchid);


}
