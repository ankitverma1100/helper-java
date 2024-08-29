package helper.helperapi.controller;

import helper.helperapi.dto.BetLockDTO;
import helper.helperapi.services.BetLockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class BetLockController {
    @Autowired
    BetLockService betLockService;


//    Not used..
//    @PostMapping("/addMarketBetlocks")
//    public ResponseEntity<?> marktebetlock(@RequestBody BetLockDTO dto) {
//        betLockService.marktebetlock(dto);
//        return ResponseEntity.ok()
//                .body(Map.of("message", "Market bet lock successfully","status",true));
//    }
//
//    @PutMapping("/updatebetlock")
//    public ResponseEntity<?> updatebetlock  (@RequestBody BetLockDTO dto){
//       betLockService.updatebetlock(dto);
//        return ResponseEntity.ok()
//                .body(Map.of("message", "Event bet lock successfully","status",true));
//    }
//
//    @PutMapping("/updatefancylock")
//    public ResponseEntity<?> updatefancylock (@RequestBody BetLockDTO dto){
//        betLockService.updatefancylock(dto);
//        return ResponseEntity.ok()
//                .body(Map.of("message", "update fancy lock successfully","status",true));
//    }

}
