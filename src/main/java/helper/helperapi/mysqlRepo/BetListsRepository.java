package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.EXMatchBet;
import helper.helperapi.models.BetList;
import helper.helperapi.sqlModels.BetListModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetListsRepository extends JpaRepository<EXMatchBet,Integer> {
    List<EXMatchBet> findByMarketIdInAndIsActive(List<String> marketIds, boolean isActive);

    int countByMarketIdAndIsActive(String marketId, boolean val);


    int countByMatchIdAndIsActive(int matchId, boolean val);


    int countByMatchIdAndMarketNameAndIsActive(int matchid,String marketName, boolean isactive);

    int countByMatchIdAndMarketIdAndIsActive(int matchid,String marketId, boolean isactive);

    List<EXMatchBet> findByMatchIdAndMarketNameAndIsActive(int matchid,String marketName, boolean isactive);

}
