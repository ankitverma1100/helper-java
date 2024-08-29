package helper.helperapi.repository;

import helper.helperapi.models.BetList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BetListRepository extends MongoRepository<BetList ,String> {
    int countByMarketidAndIsactive(String marketId, boolean val);

    int countByMatchidAndIsactive (int matchid,boolean val);

    int countByMatchidAndMarketidAndIsactive (int matchid, String marketId,boolean val);

    List<BetList> findByMatchidAndMarketidAndIsactive(int matchid, String marketid, boolean isactive);

    int countByMatchidAndMarketidAndIsactiveFalseAndStatus (int matchid, String marketId,String status);

    List<BetList> findByMatchidAndMarketidInAndIsactiveAndStatus(
            int matchid, List<String> marketIds, Boolean isactive, String status
    );

    List<BetList> findByMarketidInAndIsactive(List<String> marketIds, boolean isActive);

    int countByMatchidAndMarketnameAndIsactive(int matchid, String marketName, boolean isactive);

    List<BetList>findByMatchidAndMarketnameAndIsactive(int matchid,String marketName, boolean isactive);
}
