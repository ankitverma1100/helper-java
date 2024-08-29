package helper.helperapi.mysqlRepo;

import helper.helperapi.sqlModels.MatchFancyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MatchFancysRepository extends JpaRepository<MatchFancyModel,Integer> {
    @Override
    Optional<MatchFancyModel> findById(Integer integer);

    Optional<MatchFancyModel>findByfancyid (String fancyid);

    @Transactional
    @Modifying
    @Query("UPDATE MatchFancyModel mf SET mf.status = :status, mf.suspendedby = :userId WHERE mf.fancyid IN :fancyids AND mf.isactive = true AND mf.eventid = :eventid")
    void updateAllFancyStatus(String status, String userId, String fancyids, int eventid);

}
