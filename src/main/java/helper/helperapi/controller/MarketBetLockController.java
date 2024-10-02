package helper.helperapi.controller;
import helper.helperapi.dto.MarketBetLockDTO;
import helper.helperapi.entity.MarketBetLock;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.MarketBetLockRes;
import helper.helperapi.mysqlRepo.MarketBetLockRepo;
import helper.helperapi.repository.MarketBetLockRepository;
import helper.helperapi.services.FancyBetLockService;
import helper.helperapi.sqlModels.BetLockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class MarketBetLockController {
    @Value("${Fancy_betlock_url}")
    private String FancyUrl;

    @Autowired
    FancyBetLockService fancyBetLockService;

    @Autowired
    MarketBetLockRepo marketBetLockRepository;

    @PostMapping("/addFancyBetlock")
    public ResponseEntity<Object> addFancyBetlock(@RequestBody @Valid MarketBetLockDTO dto) {
        return fancyBetLockService.addFancyBetlock(dto);
    }

    @GetMapping("/getFancyBetlock")
    public ResponseEntity<Object> getAllReadyFancyBetLockAdded() {
        List<MarketBetLock> getMarket = marketBetLockRepository.findAll();
        List<MarketBetLockRes> data = new ArrayList<>();
        for (MarketBetLock market : getMarket) {
            String marketname = market.getMarketName();
            int matchid = market.getMatchId();
            if ("F3".equals(marketname)) {
                data.add(new MarketBetLockRes("F3",String.valueOf(matchid)));
            } else if ("F2".equals(marketname)) {
                data.add(new MarketBetLockRes("F2", String.valueOf(matchid)));
            } else if ("OE".equals(marketname)) {
                data.add(new MarketBetLockRes("OE", String.valueOf(matchid)));
            }
        }

        return ResponseEntity.ok().body(Map.of("message", "getted market","status",true, "data", data));
    }

    @DeleteMapping("/removeFancyBetlock")
    public ResponseEntity<?> findAndDeleteBySport (@RequestParam String marketName , String matchid) throws ResourceNotFoundException {
        fancyBetLockService.findAndDeleteBySportId(marketName,matchid);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Sport  remove successfully"
        ));
    }


    @GetMapping("/getMarketBetlock/{eventid}")
    public ResponseEntity<Object> getAllReadyMarketBetLockAdded(@PathVariable int eventid) {
        List<MarketBetLock> getMarket = marketBetLockRepository.findByMatchId(eventid);
        if(getMarket.isEmpty()){
            return ResponseEntity.ok().body(Map.of("message", "getted market","status",true, "data",null));

        }
        return ResponseEntity.ok().body(Map.of("message", "getted market","status",true, "data", getMarket.stream().map(data -> new BetLockResponse(data.getMarketName(),data.getMatchId()))));
    }

}

