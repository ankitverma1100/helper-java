package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event,Integer> {

    Optional<Event> findByEventid(int eventid);

    List<Event> findAll();

    @Query(value = "SELECT eventid as eventid , sportid  FROM world.t_event", nativeQuery = true)
    List<Map<String,Object>> getAllEventId();

    List<Event> findBySportid (int sportid);




    @Query(value="SELECT ev.sportid,st.name as sports,ev.eventid,ev.eventname,ev.betlock,ev.fancylock,ev.in_play,ev.odd_status FROM t_event ev left join t_sport st ON st.sportid = ev.sportid  where ev.sportid =:sportId and ev.isactive =:isActive",nativeQuery = true)
   public List<Map<String,Object>> getBySportIdAndActive(int sportId,boolean isActive);


    List<Event> findBySportidAndIsactive (Integer sportid, boolean isactive);

    @Query(value = "SELECT eventid as eventid FROM world.t_event where betlock=:betLock" , nativeQuery = true)
    List<Map<String,Object>> getLockedEvent(boolean betLock);


    @Query(value = "SELECT eventid as eventid FROM world.t_event where fancylock=:fancylock" , nativeQuery = true)
    List<Map<String,Object>> getFancyLock(boolean fancylock);


    List<Event>findByFancyLock(boolean fancylock);

    @Transactional
    void deleteByEventid(int sportid);
}
