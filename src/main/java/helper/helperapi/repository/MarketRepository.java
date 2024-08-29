package helper.helperapi.repository;

import helper.helperapi.models.Market;
import helper.helperapi.models.MatchAbNdTie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends MongoRepository<Market ,String> {
    Optional<Market> findByMarketid (String marketid);
    Market findMarketByMarketid(String marketid);
    List<Market> findAllByMarketid(String marketid);
    Optional <Market> findByEventid (int eventid);
    void  deleteByMarketid  (String marketid);
    Market findByEventidAndMarketid(int eventid, String marketid);
    List<Market> findByEventidAndIsactiveAndStatus(int eventid,boolean isactive,boolean status);



    List<Market> findBySportidAndIsactiveIsTrueAndStatusIsTrue(int sportid);
   Optional<Market> findBySportidAndEventidAndMarketid(int sportid, int eventid, String marketid);

    List<Market> findByIssuspended(Boolean isSuspended);

    List<Market>findByEventidAndIsactive(int eventid ,Boolean isactive );
}
