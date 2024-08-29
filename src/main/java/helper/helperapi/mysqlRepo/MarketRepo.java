package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketRepo extends JpaRepository<Market,Integer> {

    Optional<Market> findByMarketid (String marketid);
    Market findMarketByMarketid(String marketid);
    List<Market> findAllByMarketid(String marketid);
    Optional <helper.helperapi.models.Market> findByEventid (int eventid);
    void  deleteByMarketid  (String marketid);
    Market findByEventidAndMarketid(int eventid, String marketid);

    @Query(value = "SELECT * FROM world.t_market where eventid=:eventId and isactive=true and status=true",nativeQuery = true)
    List<Market> getByEventIdAndIsActiveAndStatus(int eventId);
    List<Market> findByEventidAndIsactiveIsTrueAndStatusIsTrue(int eventid);
    List<Market> findBySportidAndIsactiveIsTrueAndStatusIsTrue(int sportid);
    Optional<Market> findBySportidAndEventidAndMarketid(int sportid, int eventid, String marketid);

    List<Market> findByIsSuspended(Boolean isSuspended);

    List<Market>findByEventidAndIsactive(int eventid , Boolean isactive );

}
