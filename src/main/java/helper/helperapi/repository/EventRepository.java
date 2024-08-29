package helper.helperapi.repository;

import helper.helperapi.models.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findByEventid(int eventid);

    List<Event> findAll();

    List<Event> findBySportid (int sportid);


  List<Event> findBySportidAndIsactive (Integer sportid, boolean isactive);
   List<Event> findByBetlock(boolean betlock);
   List<Event>findByFancylock(boolean fancylock);

    Event deleteByEventid(int sportid);
}
