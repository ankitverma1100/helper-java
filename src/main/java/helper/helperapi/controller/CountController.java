package helper.helperapi.controller;

import helper.helperapi.dto.GetBetDTO;
import helper.helperapi.entity.EXMatchBet;
import helper.helperapi.modelResponse.FancyCountDataRes;
import helper.helperapi.models.BetList;
import helper.helperapi.mysqlRepo.BetListsRepository;
import helper.helperapi.repository.BetListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class CountController {

    @Autowired
    BetListsRepository betListRepository;
    @PostMapping("/oddsbookmakerCount")
    public ResponseEntity<Object> oddsbookmakerCount(@RequestBody GetBetDTO dto) {
        try {
            int oddsBookmakerCount = betListRepository.countByMatchIdAndMarketIdAndIsActive(dto.getMatchid(), dto.getMarketid(), true);
            return ResponseEntity.ok()
                    .body(Map.of("message", "odds bookmaker Count successfully","status",true, "data",oddsBookmakerCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    @PostMapping("/fancy2count")
    public ResponseEntity<?> fancy2count (@RequestBody GetBetDTO dto){
        try {
            List<EXMatchBet> betLists = betListRepository.findByMatchIdAndMarketNameAndIsActive(dto.getMatchid(), dto.getMarketname(), true);
            int totalentry = betListRepository.countByMatchIdAndMarketNameAndIsActive(dto.getMatchid(), dto.getMarketname(), true);
            FancyCountDataRes data = new FancyCountDataRes();
            data.setOddEvencount(betLists);
            data.setTotalentry(totalentry);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Get fancy2count successfully", "status",true,"data",data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    @PostMapping("/fancy3count")
    public ResponseEntity<?> fancy3count (@RequestBody GetBetDTO dto){
        try {
            List<EXMatchBet> betLists = betListRepository.findByMatchIdAndMarketNameAndIsActive(dto.getMatchid(), dto.getMarketname(), true);
            int totalentry = betListRepository.countByMatchIdAndMarketNameAndIsActive(dto.getMatchid(), dto.getMarketname(), true);
            FancyCountDataRes data = new FancyCountDataRes();
            data.setOddEvencount(betLists);
            data.setTotalentry(totalentry);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Get fancy3count successfully" ,"status",true,"data",data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

    @PostMapping("/OddEvencount")
    public ResponseEntity<?> OddEvencount (@RequestBody GetBetDTO dto){
        try {
            List<EXMatchBet> betLists = betListRepository.findByMatchIdAndMarketNameAndIsActive(dto.getMatchid(), dto.getMarketname(), true);
            int totalentry = betListRepository.countByMatchIdAndMarketNameAndIsActive(dto.getMatchid(), dto.getMarketname(), true);
            FancyCountDataRes data = new FancyCountDataRes();
            data.setOddEvencount(betLists);
            data.setTotalentry(totalentry);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Get OddEvencount successfully","status",true,"data",data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
        }
    }

}
