package helper.helperapi.controller;

import helper.helperapi.dto.MatchResultDTO;
import helper.helperapi.models.T_market_for_rollback;
import helper.helperapi.repository.T_fancy_rollback_detailRepository;
import helper.helperapi.repository.T_market_for_rollbackRepository;
import helper.helperapi.services.RollbackService;
import helper.helperapi.sqlModels.Market_for_rollback;
import helper.helperapi.mysqlRepo.Fancy_rollback_detailRepository;
import helper.helperapi.mysqlRepo.Market_for_rollbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class RollbackController {
    @Autowired
    T_market_for_rollbackRepository tMarketForRollbackRepository;
    @Autowired
    T_fancy_rollback_detailRepository tFancyRollbackDetailRepository;
   //SQL
    @Autowired
    Fancy_rollback_detailRepository fancyRollbackDetailRepository;

    @Autowired
    Market_for_rollbackRepository market_for_rollbackRepository ;

    @Autowired
    RollbackService rollbackService;

    @PostMapping("/marketRollback")
    public ResponseEntity<?> marketrollback (@RequestBody MatchResultDTO dto){
        rollbackService.marketrollback(dto);
        return ResponseEntity.ok()
                .body(Map.of("message", "rollback success successfully","status",true));
    }

    @PostMapping("/fancyRollback")
    public ResponseEntity<?> fancyrollback (@RequestBody MatchResultDTO dto){
       rollbackService.fancyrollback(dto);
        return ResponseEntity.ok()
                .body(Map.of("message", "Fancy  rollback success successfully","status",true));
    }
    @PostMapping("/abndtieRollback")
    public ResponseEntity<?> tieabndRollback (@RequestBody MatchResultDTO dto){
        rollbackService.tieabndRollback(dto);
        return ResponseEntity.ok()
                .body(Map.of("message", "tieabnd rollback success successfully","status",true));
    }

    @PostMapping("/suspendedfancyRollback")
    public ResponseEntity<?> suspendedfancyRollback (@RequestBody MatchResultDTO dto){
        String fancyid = dto.getMarketid();
        Optional<Market_for_rollback> fetchT_market1 = market_for_rollbackRepository.findByMarketid(dto.getMarketid());
         if(!fetchT_market1.isPresent()){
             String status = "ABANDONED_ROLLBACK";
             Market_for_rollback rollback = new Market_for_rollback();
             rollback.setMarketid(fancyid);
             rollback.setStatus(status);
             rollback.setMarkettype(dto.getMarkettype());
             market_for_rollbackRepository.save(rollback);
         }
         Optional<T_market_for_rollback> fetchT_market2 = tMarketForRollbackRepository.findByMarketid(dto.getMarketid());
         if(!fetchT_market2.isPresent()){
             String status = "ABANDONED_ROLLBACK";
             T_market_for_rollback rollback = new T_market_for_rollback();
             rollback.setStatus(status);
             rollback.setMarketid(fancyid);
             rollback.setMarkettype(dto.getMarkettype());
             tMarketForRollbackRepository.save(rollback);
         }
        return ResponseEntity.ok()
                .body(Map.of("message", "tieabnd rollback success successfully","status",true));
    }


}
