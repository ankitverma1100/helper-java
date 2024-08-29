package helper.helperapi.services;

import helper.helperapi.dto.BetLockDTO;
import helper.helperapi.entity.Market;
import helper.helperapi.models.Event;
import helper.helperapi.repository.EventRepository;
import helper.helperapi.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
public class BetLockService {


//    @Autowired
//    helper.helperapi.mysqlRepo.MarketRepo marketRepository;
//
//    @Autowired
//    EventRepository eventRepository ;
//
//    public ResponseEntity<?> marktebetlock(@RequestBody BetLockDTO dto) {
//        String marketId = dto.getMarketid();
//        String eventId = String.valueOf(dto.getEventid());
//        if (marketId == null) {
//            return ResponseEntity.badRequest().body("Please provide a marketid");
//        }
//        if (eventId == null) {
//            return ResponseEntity.badRequest().body("Please provide an eventid");
//        }
//
//        Market existingMarket = marketRepository.findByEventidAndMarketid(dto.getEventid(),dto.getMarketid());
////        helper.helperapi.entity.Market existingMarket = marketRepositoryMysql.findByEventidAndMarketid(dto.getEventid(),dto.getMarketid());
//        if (existingMarket == null) {
//            return ResponseEntity.notFound().build();
//        }
////        if(existingMarket.getBetLock()==true){
////            existingMarket.setBetlock(false);
////        } else if (existingMarket.getBetlock()==false) {
////            existingMarket.setBetlock(true);
////        }
//        marketRepository.save(existingMarket);
//        return ResponseEntity.ok()
//                .body(Map.of("message", "Market bet lock successfully","status",true));
//    }
//
//
//    public ResponseEntity<?> updatebetlock  (@RequestBody BetLockDTO dto){
//        Optional<Event> event = eventRepository.findByEventid(dto.getEventid());
//        if(!event.isPresent()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "event id not found"));
//        }
//        Event event1 = event.get();
//        if(event1.getBetlock()==true){
//            event1.setBetlock(false);
//        } else if (event1.getBetlock()==false) {
//            event1.setBetlock(true);
//        }
//        eventRepository.save(event1);
//        return ResponseEntity.ok()
//                .body(Map.of("message", "Event bet lock successfully"));
//    }
//    public ResponseEntity<?> updatefancylock (@RequestBody BetLockDTO dto){
//        Optional<Event> event = eventRepository.findByEventid(dto.getEventid());
//        if(!event.isPresent()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "event id not found","status",false));
//        }
//        Event event1 = event.get();
//        if(event1.getFancylock()==true){
//            event1.setFancylock(false);
//        } else if (event1.getFancylock()==false) {
//            event1.setFancylock(true);
//        }
//        eventRepository.save(event1);
//        return ResponseEntity.ok()
//                .body(Map.of("message", "update fancy lock successfully","status",true));
//    }


}
