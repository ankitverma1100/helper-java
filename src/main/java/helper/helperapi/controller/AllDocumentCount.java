package helper.helperapi.controller;
import helper.helperapi.dto.GetBetDTO;
import helper.helperapi.dto.MarketCountDTO;
import helper.helperapi.dto.MatchCountDTO;
import helper.helperapi.entity.Event;
import helper.helperapi.modelResponse.BetDataRes;
import helper.helperapi.modelResponse.MatchCountResponse;
import helper.helperapi.modelResponse.MatchData;
import helper.helperapi.mysqlRepo.BetListsRepository;
import helper.helperapi.mysqlRepo.EventRepo;
import helper.helperapi.repository.BetListRepository;
import helper.helperapi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class AllDocumentCount{
   @Autowired
    BetListsRepository betListRepository ;

   @Autowired
   EventRepo eventRepository;

    @PostMapping("/marketCount")
    public ResponseEntity<?>marketCount(@RequestBody MarketCountDTO dto ) {
        int value = betListRepository.countByMarketIdAndIsActive(dto.getMarketid(), true);
        return ResponseEntity.ok()
                .body(Map.of("message", "marketCount fetch data successfully","status",true ,"data", value));
    }

    @PostMapping("/matchidCount")
    public ResponseEntity<?> matchidCount(@RequestBody MatchCountDTO dto) {
        System.out.println("match Id is "+dto.getMatchid());
        int matchidcount = betListRepository.countByMatchIdAndIsActive(dto.getMatchid(), true);
        System.out.println(matchidcount+" this is count");
        Optional<Event> event = eventRepository.findByEventid(dto.getMatchid());
        Event event1 = event.get();
        MatchCountResponse getlock = new MatchCountResponse(event1.getBetLock(), event1.getBetLock(), event1.getEventid());
        MatchData matchData = new MatchData(matchidcount);
        return ResponseEntity.ok()
                .body(Map.of("message", "matchidCount fetch data successfully", "status",true ,"data", matchData));
    }


//    @PostMapping("/getbets")
//    public  ResponseEntity<?> getbets (@RequestBody GetBetDTO dto){
//        List<BetList> getbets = betListRepository.findByMatchidAndMarketidAndIsactive(dto.getMatchid(), dto.getMarketid(), true);
////        count
//        int totalentry = betListRepository.countByMatchidAndMarketidAndIsactive(dto.getMatchid(), dto.getMarketid(), true);
//        BetDataRes  betsData = new BetDataRes(getbets, totalentry);
//       return ResponseEntity.ok()
//               .body(Map.of("message", "get bets data successfully", "status",true ,"data",betsData));
//   }
//
//   @PostMapping("/fancysuspendedcount")
//    public ResponseEntity<?> fancysuspendedcount (@RequestBody GetBetDTO dto){
//       int spendedcount = betListRepository.countByMatchidAndMarketidAndIsactiveFalseAndStatus(dto.getMatchid(), dto.getMarketid(), "SUSPENDED");
//
//       return ResponseEntity.ok()
//               .body(Map.of("message", "Fancy suspended count data successfully","status",true , "data",spendedcount));
//   }

}
