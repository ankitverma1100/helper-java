package helper.helperapi.controller;
import helper.helperapi.dto.MarketCountDTO;
import helper.helperapi.dto.MatchResultDTO;
import helper.helperapi.entity.EXMatchBet;
import helper.helperapi.entity.MatchAbondendTie;
import helper.helperapi.models.*;
import helper.helperapi.repository.*;
import helper.helperapi.sqlModels.BetListModel;
import helper.helperapi.sqlModels.MatchAbNdTieModel;
import helper.helperapi.sqlModels.MatchResultModel;
import helper.helperapi.mysqlRepo.BetListsRepository;
import helper.helperapi.mysqlRepo.MatchAbNdRepository;

import helper.helperapi.mysqlRepo.MatchResultModelRepository;
import helper.helperapi.mysqlRepo.MatchResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class MatchResultController {
    @Autowired
    SportRepository sportRepository ;
    @Autowired
    EventRepository eventRepository ;
    @Autowired
    MarketRepository marketRepository;

    @Autowired
    SelectionRepository selectionRepository;

    @Autowired
    MatchResultRepository matchResultRepository;

    @Autowired
    MatchResultsRepository matchResultsRepository ;

    @Autowired
    MatchAbNdRepository matchAbNdTieRepository;

    @Autowired
    MatchAbNdRepository matchAbNdRepository;

    @Autowired
    MatchResultModelRepository matchResultModelRepository;

    @Autowired
    BetListRepository betListRepository;

    @Autowired
    BetListsRepository betListsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    SimpleDateFormat dateFormatForDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    @PostMapping("/matchResult")
    public ResponseEntity<?> matchResult (@RequestBody MatchResultDTO dto){
        Optional<Sport> fetchT_sports1 = sportRepository.findBySportid(dto.getSportid());
        if(!fetchT_sports1.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "provide valid sport id","status",false));
        }
        Sport fetchT_sports = fetchT_sports1.get();
        Optional<Event> fetchEvent1 = eventRepository.findByEventid(dto.getEventid());
        if(!fetchEvent1.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "provide a valid event id","status",false));
        }
        Event fetchEvent = fetchEvent1.get();
        Market fetchMarket = marketRepository.findByEventidAndMarketid(dto.getEventid(), dto.getMarketid());
        if(fetchMarket==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "provide a valid event id and market id","status",false));
        }
        Optional<MatchResult>checkMarket = matchResultRepository.findByMarketid(dto.getMarketid());
        if(checkMarket.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Result already set fir this market","status",false));
        }
        Optional<MatchResultModel> matchResultModel = matchResultModelRepository.findByMarketid(dto.getMarketid());
        if(matchResultModel.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Result already set fir this market","status",false));
        }
        Selection fetcht_Selection  = selectionRepository.findBySelectionid(dto.getSelectionid());
        if(fetcht_Selection==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","provide a valid selection id","status",false));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
        }
        User user = (User) authentication.getPrincipal();
//        Selection fetcht_Selection = fetcht_Selection1.get();
        MatchResultModel matchResult = new MatchResultModel();
        matchResult.setMarketid(fetchMarket.getMarketid());
        matchResult.setMarketname(fetchMarket.getMarketname());
        matchResult.setMarkettype(fetchMarket.getMarketname());
        matchResult.setMatchid(fetchEvent.getEventid());
        matchResult.setMatchname(fetchEvent.getEventname());
        matchResult.setResult(fetcht_Selection.getSelectionid());
        matchResult.setSelectionid(fetcht_Selection.getSelectionid());
        matchResult.setSelectionname(fetcht_Selection.getRunner_name());
        matchResult.setSportid(dto.getSportid());
        // matchResult.setSportname(fetchT_sports.getName());
        matchResult.setDate(dateFormatForDB.format(new Date()));
        matchResult.setDeclared_by(user.getUserId());
        matchResultModelRepository.save(matchResult);
        //Save the mongodb
        MatchResult matchResult1  = new MatchResult();
        matchResult1.setMarketid(fetchMarket.getMarketid());
        matchResult1.setMarketname(fetchMarket.getMarketname());
        matchResult1.setMarkettype(fetchMarket.getMarketname());
        matchResult1.setMatchid(fetchEvent.getEventid());
        matchResult1.setMatchname(fetchEvent.getEventname());
        matchResult1.setResult(fetcht_Selection.getSelectionid());
        matchResult1.setSelectionid(fetcht_Selection.getSelectionid());
        matchResult1.setSelectionname(fetcht_Selection.getRunner_name());
        matchResult1.setSportid(dto.getSportid());
        matchResult1.setSportname(fetchT_sports.getName());
        matchResult1.setDate(dateFormatForDB.format(new Date()));
        matchResult1.setDeclared_by(user.getUserId());
        matchResultRepository.save(matchResult1);
        return ResponseEntity.ok()
                .body(Map.of("message", "Result Set successfully ","status",true,"data", matchResult1));
    }

    @PostMapping("/abnttieResult")
    public ResponseEntity<?> abnttieResult (@RequestBody MatchResultDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
        }
        User user = (User) authentication.getPrincipal();
        Optional<Sport> fetchT_sports1 = sportRepository.findBySportid(dto.getSportid());
        if(!fetchT_sports1.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "provide valid sport id","status",false));
        }
        Sport fetchT_sports = fetchT_sports1.get();
        Optional<Event> fetchEvent1 = eventRepository.findByEventid(dto.getEventid());
        if(!fetchEvent1.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "provide a valid event id","status",false));
        }
        Event fetchEvent = fetchEvent1.get();
        Market fetchMarket1 = marketRepository.findByEventidAndMarketid(dto.getEventid(), dto.getMarketid());
        if(fetchMarket1==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "provide a valid event id and market id","status",false));
        }
//        Optional<Selection> fetcht_Selection1 = selectionRepository.findByMarketid(dto.getMarketid());
//        if(!fetcht_Selection1.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "provide a valid market id"));
//        }
        Optional<MatchAbNdTieModel> matchAbNdTieModel = matchAbNdRepository.findByMarketid(dto.getMarketid());
        if(matchAbNdTieModel.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Result already set fir this market","status",false));
        }
        Optional<MatchAbNdTieModel> matchAb = matchAbNdTieRepository.findByMarketid(dto.getMarketid());
        if(matchAb.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Result already set fir this market","status",false));
        }else{
            // Selection fetcht_Selection = fetcht_Selection1.get();
            Optional<Market> market1 = marketRepository.findBySportidAndEventidAndMarketid(dto.getSportid(), dto.getEventid(), dto.getMarketid());
//        Market market = market1.get();
//        market.setIsactive(false);
//        market.setStatus(false);
//        marketRepository.save(market);
            //SQL
            MatchAbNdTieModel matchAbNdTie1 = new MatchAbNdTieModel();
            matchAbNdTie1.setMarketid(dto.getMarketid());
            matchAbNdTie1.setMarketname(fetchMarket1.getMarketname());
            matchAbNdTie1.setMatchid(fetchEvent.getEventid());
            matchAbNdTie1.setMatchname(fetchEvent.getEventname());
            matchAbNdTie1.setSportid(dto.getSportid());
            matchAbNdTie1.setSportname(fetchT_sports.getName());
            matchAbNdTie1.setResult(dto.getResult());
            matchAbNdTie1.setStatus(true);
            matchAbNdTie1.setDate(dateFormatForDB.format(new Date()));
            matchAbNdTie1.setDeclared_by(user.getUserId());
            matchAbNdRepository.save(matchAbNdTie1);
            //Mongodb
            MatchAbNdTieModel matchAbNdTie = new MatchAbNdTieModel();
            matchAbNdTie.setMarketid(dto.getMarketid());
            matchAbNdTie.setMarketname(fetchMarket1.getMarketname());
            matchAbNdTie.setMatchid(fetchEvent.getEventid());
            matchAbNdTie.setMatchname(fetchEvent.getEventname());
            matchAbNdTie.setSportid(dto.getSportid());
            matchAbNdTie.setSportname(fetchT_sports.getName());
            matchAbNdTie.setResult(dto.getResult());
            matchAbNdTie.setStatus(true);
            matchAbNdTie.setDate(dateFormatForDB.format(new Date()));
            matchAbNdTie.setDeclared_by(user.getUserId());
            matchAbNdTieRepository.save(matchAbNdTie);
            return ResponseEntity.ok()
                    .body(Map.of("message", "Result Set successfully ", "status",true,"data",matchAbNdTie1));
        }
//
    }

    @PostMapping("/getabndtie")
    public ResponseEntity<?> fetchtEvent(@RequestBody MatchResultDTO dto) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<MatchAbNdTieModel> matchAbNdTies = matchAbNdTieRepository.findBySportid(dto.getSportid());
        if(matchAbNdTies.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "no any getabndtie data","status",false
            ));
        }
        Collections.sort(matchAbNdTies, (e1, e2) -> e2.getDate().compareTo(e1.getDate()));
        return ResponseEntity.ok()
                .body(Map.of("message", "get abndtie data successfully", "status",true,"data", matchAbNdTies));
    }

    @PostMapping("/getmatchResult")
    public ResponseEntity<?> getMatchResult(@RequestBody MatchResultDTO dto) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("sportid").is(dto.getSportid())),
                Aggregation.lookup("t_fancy_rollback_detail", "marketid", "marketid", "markets"),
                Aggregation.unwind("markets", true),
                Aggregation.project("marketid", "markets.donby", "sportname", "marketname", "matchname", "selectionname", "date", "declared_by"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "date"))
        );
        List<MatchResult> matchResults = mongoTemplate.aggregate(aggregation, "t_matchresult", MatchResult.class).getMappedResults();
        return ResponseEntity.ok()
                .body(Map.of("message", "fetch result data successfully","status",true, "data", matchResults));

    }

    @PostMapping("/resultCount")
    public ResponseEntity<?> resultCount (@RequestBody MarketCountDTO dto){
        try {
            String marketid = dto.getMarketid();

            List<Market> fetchMarket = marketRepository.findAllByMarketid(marketid);
            List<BetList> resultCount = betListRepository.findByMarketidInAndIsactive(fetchMarket.stream().map(Market::getMarketid).collect(Collectors.toList()), true);
            fetchMarket.forEach(item -> {
                int count = (int) resultCount.stream().filter(i -> i.getMarketid().equals(item.getMarketid())).count();
                item.setCount(count);
            });

            List<EXMatchBet> sqlMarketCount = betListsRepository.findByMarketIdInAndIsActive(fetchMarket.stream().map(Market::getMarketid).collect(Collectors.toList()), true);

            fetchMarket.forEach(item -> {
                int count2 = (int) sqlMarketCount.stream().filter(i -> i.getMarketId().equals(item.getMarketid())).count();
                System.out.println(count2+"sql");
                item.setCount1(count2);
            });
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "get count successfully","status",true, "data", fetchMarket));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "something went wrong"));
        }
    }
}


