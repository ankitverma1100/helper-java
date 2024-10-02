package helper.helperapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.helperapi.dto.*;
import helper.helperapi.entity.Market;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.*;
import helper.helperapi.models.Selection;
import helper.helperapi.models.Sport;
import helper.helperapi.mysqlRepo.MarketRepo;
import helper.helperapi.repository.*;
import helper.helperapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class MarketController {
    @Value("${url_Cricket}")
    private String url;

    @Value("${url_Other}")
    private String other_Url;


    @Value("${market_betlock_url}")
    private String marketBetLockUrl;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    MarketRepo marketRepository;

    @Autowired
    SelectionRepository selectionRepository;

    @Autowired
    MarketService marketService;

    @GetMapping("/market/text")
    public String user() {
        return "good";
    }

    // Add market bet lock api
    @PostMapping("/addMarketBetlock")
    public ResponseEntity<Object> addMarketBetlock (@Valid @RequestBody AddMarketBetLockRes dto){
        if (dto.getMarketname() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","marketName must not be null","status",false));
        }
      return   marketService.addMarketBetlock(dto);
    }

    @PostMapping("/addMarketList")
    public Object getEvents( @RequestBody  AddMarketDTO addMarketDTO)
            throws ResourceNotFoundException, JsonProcessingException {
        List<Sport> fetchT_sports = sportRepository.findByTypeAndStatus("betfair", true);
        if (fetchT_sports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Type not betfair. Please contact admin.","status",false));
        }
        Optional<Sport> checkSportId = sportRepository.findBySportid(addMarketDTO.getSportid());
        if (!checkSportId.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Please provide a valid sportId.","status",false));
        }
        Optional<Market> checkMarketId = marketRepository.findByMarketid(addMarketDTO.getMarketid());
        if (checkMarketId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Market already added.","status",false));
        }
        marketService.getEvents(addMarketDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Add market data  successfully","status",true));

    }

    @DeleteMapping("/removedMarket")
    public ResponseEntity<?> removeMarket(@RequestParam String marketid)
            throws ResourceNotFoundException {
        Optional<Market> checkMarketId = marketRepository.findByMarketid(marketid);
        if (!checkMarketId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Market allready removed","status",false));
        }
        selectionRepository.deleteByMarketid(marketid);
        marketRepository.deleteByMarketid(marketid);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Successfully removed","status",true));
    }


    @PostMapping("/maxminratedata")
    public ResponseEntity<Map<String, Object>> findMaxData(@RequestBody EventDeDTO eventDeDTO){
        List<Market> markets = marketRepository.getByEventIdAndIsActiveAndStatus(eventDeDTO.getEventid());
        if (markets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Event not found","status",false));
        }

        Map<String, List<Map<String, Object>>> groupedMarkets = markets.stream()
                .map(market -> {
                    Map<String, Object> selectedMap = new HashMap<>();
                    selectedMap.put("marketname", market.getMarketname());
//                    selectedMap.put("maxLiabilityPerMarket", market.getMaxLiabilityPerMarket());
                    selectedMap.put("marketid", market.getMarketid());
                    selectedMap.put("max_bet_rate", market.getMaxBetRate());
                    selectedMap.put("min_bet_rate", market.getMinBetRate());
                    selectedMap.put("betdelay", market.getBetDelay());
                    selectedMap.put("maxbet",market.getMaxbet());
                    selectedMap.put("minbet",market.getMinbet());
                    selectedMap.put("display_message",market.getDisplayMessage());
                    selectedMap.put("isactive",market.getIsactive());
                    selectedMap.put("issuspended",market.isSuspended());
                    selectedMap.put("status",market.getStatus());
                    selectedMap.put("matchname",market.getMatchname());
                    return selectedMap;
                })
                .collect(Collectors.groupingBy(m -> (String) m.get("marketname")));

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Get data  successfully","status",false,"data",groupedMarkets));
    }


    @PostMapping("/fetchtMarket")
    public List<FetchtMarketRes> fetchtMarket(@RequestBody EventDeDTO eventDeDTO) throws ResourceNotFoundException {
        List<Market> markets = marketRepository.findByEventidAndIsactive(eventDeDTO.getEventid(),true);
        if (markets.isEmpty()) {
            throw new ResourceNotFoundException("Event not found");
        }
        return markets.stream()
                .map(data -> new FetchtMarketRes(data.getEventid(), data.getMarketid(), data.getMarketname())).toList();
    }

    @GetMapping("/get_all_Selection")
    public ResponseEntity<?> get_all_selection() throws ResourceNotFoundException {
        List<Selection> selections = selectionRepository.findAll();
        if (selections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "No any selection","status",false));
        }
        return ResponseEntity.ok()
                .body(Map.of("message", "successfully get selections", "status",true,"data", selections));
    }


    @PutMapping("/display_message")
    @Transactional
    public ResponseEntity<?> display_message(@RequestBody DisplayMessageDTO dto) throws ResourceNotFoundException {
       Market   market = marketRepository.findMarketByMarketid(dto.getMarketid());
        if (market==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Market id not found"));
        }
        market.setDisplayMessage(dto.getDisplay_message());
        marketRepository.save(market);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Display message add Successfully","status",true));
    }

    @PostMapping("/getDisplayMessage")
    public ResponseEntity<Map<String, Object>> getDisplayMessage(@RequestBody GetDisplayMessageDTO dto)
           {
        Market market = marketRepository.findByEventidAndMarketid(dto.getEventid(), dto.getMarketid());
        if (market == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Market id and event id  does not match !","status",false));
        }
        HashMap<String,Object> res = new HashMap<>();
        GetDisplayMessageRes getDisplayMessageRes = new GetDisplayMessageRes(market.getEventid(), market.getMarketid(), market.getDisplayMessage());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Get data  successfully","status",true,"data",getDisplayMessageRes));

    }

    @PutMapping("/updateSuspended")
    public ResponseEntity<Map<String, String>> updateSuspended(@RequestBody UpdateSuspendedDTO dto)
    {
        Optional<Market> market = marketRepository.findByMarketid(dto.getMarketid());
        if (!market.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Market Id not found","status","false"));
        }
        Market market1 = market.get();
        market1.setSuspended(dto.getIssuspended());
        marketRepository.save(market1);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "update suspend successfully","status","true"));
    }

    @PutMapping("/update_market")
//    public ResponseEntity<Map<String, String>> updateMarket(@RequestBody UpdateMarketDTO dto){
//        Optional<Market> market = marketRepository.findByMarketid(dto.getMarketid());
//        if (!market.isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Market Id not found","status","false"));
//        }
//        Market market1 = market.get();
//        market1.setMaxbet(dto.getMaxbet());
//        market1.setMinbet(dto.getMinbet());
//        market1.setBetlock(dto.getBetlock());
//        market1.setMax_bet_rate(dto.getMax_bet_rate());
//        market1.setMin_bet_rate(dto.getMin_bet_rate());
//        marketRepository.save(market1);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
//                "message", "update_market successfully","status","true"));
//    }

    public ResponseEntity<Map<String, String>> updateMarket(@RequestBody UpdateMarketDTO dto) {
        Optional<Market> marketOptional = marketRepository.findByMarketid(dto.getMarketid());

        if (!marketOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", "Market Id not found", "status", "false"));
        }

        Market market = marketOptional.get();
        if (dto.getMaxbet() != 0) {
            market.setMaxbet(dto.getMaxbet());
        }

        if (dto.getMinbet() != 0) {
            market.setMinbet(dto.getMinbet());
        }

        if (dto.getBetlock() != null) {
//            market.setBetLock(dto.getBetlock());
        }

        if (dto.getMax_bet_rate() != 0) {
            market.setMaxBetRate(Double.parseDouble(String.valueOf(dto.getMax_bet_rate())));
        }

        if (dto.getMin_bet_rate() != 0) {
            market.setMinBetRate(Double.parseDouble(String.valueOf(dto.getMin_bet_rate())));
        }

        if(dto.getMaxLiabilityPerMarket()!=0){
//            market.setMaxLiabilityPerMarket(dto.getMaxLiabilityPerMarket());
        }

        marketRepository.save(market);

        return ResponseEntity.status(HttpStatus.OK).body(
                Map.of("message", "update_market successfully", "status", "true"));
    }




    @PutMapping("/addMarketBetlock")
    public ResponseEntity<Map<String, String>> updateSuspended(@RequestBody BetLockDTO dto)
            throws ResourceNotFoundException {
        Optional<Market> market = marketRepository.findByMarketid(dto.getMarketid());
        if (!market.isPresent()) {
            throw new ResourceNotFoundException("Market id not found");
        }
        Market market1 = market.get();
//        if (market1.getBetlock() == false) {
//            market1.setBetlock(true);
//        } else if (market1.getBetlock() == true) {
//            market1.setBetlock(false);
//        }
        marketRepository.save(market1);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "update market bet lock successfully"));
    }

    @GetMapping("/getisSuspended")
    public List<GetSuspendedRes> getisSuspended () throws  ResourceNotFoundException{
        List<Market> findMarketdata = marketRepository.findByIsSuspended(false);
        if(findMarketdata.isEmpty()){
            throw  new ResourceNotFoundException("no any  isSuspended data");
        }
        return  findMarketdata.stream().map(data -> new GetSuspendedRes(data.isSuspended(),data.getMarketid())).toList();
    }

    @GetMapping("/allreadysuspended")
    public  HashMap<String, Object> allreadysuspended () throws  ResourceNotFoundException{
        List<Market> findMarketdata = marketRepository.findByIsSuspended(true);

        if(findMarketdata.isEmpty()){
            HashMap<String ,Object> res = new HashMap<>();
            res.put("data",findMarketdata);
            res.put("message","get suspended market");
            return  res ;
        }
        HashMap<String ,Object> res = new HashMap<>();
        res.put("data",findMarketdata.stream().map(data -> new AllReadSuspendedRes(data.getMarketid())).toList());
        res.put( "message","get suspended market");
        return  res ;
    }


    @PostMapping("/fetchT_Selection")
    public List<Selection> fetchT_Selection (@RequestBody AddMarketDTO addMarketDTO)throws  ResourceNotFoundException{
       List<Selection> selectionList = selectionRepository.findAllByMarketid(addMarketDTO.getMarketid());
       if(selectionList.isEmpty()){
           throw  new ResourceNotFoundException("selection not found this market id");
       }
       return selectionList;
    }

}