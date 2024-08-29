package helper.helperapi.repository;
import helper.helperapi.models.MatchFancy;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface MatchFancyRepository  extends MongoRepository <MatchFancy ,String> {

    Optional<MatchFancy> findByFancyid(String fancyid);
    List<MatchFancy> findByEventid (int eventid);


    List<MatchFancy> findByEventidAndOddstype (int eventId , String oddstype);

    Optional<MatchFancy> findByEventidAndFancyid(int eventid, String  fancyid);

    List<MatchFancy> findByStatusAndEventid(String status, int eventid);
    List<MatchFancy> findByFancyidInAndEventid(String fancyIds, int eventId);
    List<MatchFancy> findByStatusAndEventidOrderByCreatedonDesc(String status, int eventid);

    List<MatchFancy> findByEventidAndIsactive(int eventid, boolean isactive);

    void  deleteByFancyid  (String fancyid);


}
