package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.MatchFancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MatchFancyRepo extends JpaRepository<MatchFancy,Integer> {

    Optional<MatchFancy> findByFancyid(String fancyid);
    List<MatchFancy> findByEventid (int eventid);


    List<MatchFancy> findByEventidAndOddstype (int eventId , String oddstype);

    Optional<MatchFancy> findByEventidAndFancyid(int eventid, String  fancyid);

    List<MatchFancy> findByStatusAndEventid(String status, int eventid);
//    List<MatchFancy> findByFancyidInAndEventid(String fancyIds, int eventId);
    List<MatchFancy> findByStatusAndEventidOrderByCreatedonDesc(String status, int eventid);

    List<MatchFancy> findByEventidAndIsActive(int eventid, boolean isactive);

    void  deleteByFancyid  (String fancyid);

    @Query(value = "SELECT fancyid FROM t_matchfancy where eventid=:eventId",nativeQuery = true)
    public List<Map<String,Object>> getByEventId(int eventId);
}
