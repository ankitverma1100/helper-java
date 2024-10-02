package helper.helperapi.controller;
import helper.helperapi.dto.*;
import helper.helperapi.entity.EXMatchBet;
import helper.helperapi.entity.MatchFancy;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.*;
import helper.helperapi.modelResponse.GetFancyResponse.*;
import helper.helperapi.modelResponse.GetFancyResponse.FancyData;
import helper.helperapi.models.BetList;
import helper.helperapi.models.FancyResult;
import helper.helperapi.models.User;
import helper.helperapi.mysqlRepo.MatchFancyRepo;
import helper.helperapi.repository.BetListRepository;
import helper.helperapi.repository.EventRepository;
import helper.helperapi.repository.FancyResultRepository;
import helper.helperapi.services.MatchFancyService;
import helper.helperapi.sqlModels.FancyResultsModel;
import helper.helperapi.sqlModels.MatchFancyModel;
import helper.helperapi.mysqlRepo.BetListsRepository;
import helper.helperapi.mysqlRepo.FancyResultsRepository;
import helper.helperapi.mysqlRepo.MatchFancysRepository;
import helper.helperapi.sqlModels.ObjectCommonResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class MatchFancyController {
    @Value("${url_Cricket}")
    private String url;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MatchFancyRepo matchFancyRepository;

    @Autowired
    MatchFancysRepository matchFancysRepository ;

    @Autowired
    FancyResultsRepository fancyResultsRepository;

    @Autowired
    MatchFancyService matchFancyService ;

    @Autowired
    FancyResultRepository fancyResultRepository ;


    @Autowired
    BetListsRepository betListRepository ;

    @Autowired
    BetListRepository betMongoRepository ;


    @Autowired
    BetListsRepository betListsRepository;


    @Autowired
    private MongoTemplate mongoTemplate;
    SimpleDateFormat dateFormatForDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    @PostMapping("/getFancy")
    public ResponseEntity<Object> getFancy(@RequestBody EventDeDTO eventDeDTO) {
     return matchFancyService.getFancy(eventDeDTO);
    }
    @PostMapping("/addFancy")
    public Object addFancy(@RequestBody AddFancyDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated","status",false));
        }
        Optional<MatchFancy> matchFancy1 = matchFancyRepository.findByFancyid(dto.getMarketid());
        if (matchFancy1.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Fancy already added","status",false));
        }
        RestTemplate restTemplate = new RestTemplate();
        this.url = "http://15.206.103.38:8083/data-provider/cricket";
        FancyData data = restTemplate.getForObject(url, FancyData.class);


        List<FancyDataClass> result = data.getGameList().get(0).getEventList().stream().filter(fancyDataClass -> {
            return fancyDataClass.getEventData().getEventId() == dto.getEventid();
        }).toList();
        User user = (User) authentication.getPrincipal();
        List<Other_marketRes> otherMarketdata = result.get(0).getMarketList().getOther_market();

        if(otherMarketdata!=null){
            List<Other_marketRes> otherMarketRes = result.get(0).getMarketList().getOther_market().stream().filter(fancyDataClass->{
                return fancyDataClass.getMarketId().equals(dto.getMarketid());
            }).toList();
            if(!otherMarketRes.isEmpty()){
                MatchFancy matchFancy = new MatchFancy();
                matchFancy.setEventid(otherMarketRes.get(0).getEid());
                matchFancy.setFancyid(otherMarketRes.get(0).getMarketId());
                matchFancy.setName(otherMarketRes.get(0).getTitle());
                matchFancy.setSportId(otherMarketRes.get(0).getSid());
                matchFancy.setRunnerid(otherMarketRes.get(0).getMarketId());
                matchFancy.setAddby(user.getUserId());
                matchFancy.setStatus("OPEN");
                matchFancy.setMaxBet(dto.getMaxbet());
                matchFancy.setMinBet(dto.getMinbet());
                String fancyId = otherMarketRes.get(0).getMarketId();
                String oddsType = fancyId.substring(fancyId.length() - 2);
                matchFancy.setMatchname(result.get(0).getEventData().getName());
                matchFancy.setOddstype(oddsType);
                matchFancy.setCreatedon(new Date());
                matchFancy.setUpdatedOn(new Timestamp(new Date().getTime()));
                matchFancyRepository.save(matchFancy);
                Optional<MatchFancyModel>matchFancyModel = matchFancysRepository.findByfancyid(dto.getMarketid());
                if(!matchFancyModel.isPresent()){
                    MatchFancyModel match_sql = new MatchFancyModel();
                    match_sql.setEventid(otherMarketRes.get(0).getEid());
                    match_sql.setFancyid(otherMarketRes.get(0).getMarketId());
                    match_sql.setName(otherMarketRes.get(0).getTitle());
                    match_sql.setSportid(otherMarketRes.get(0).getSid());
                    match_sql.setRunnerid(otherMarketRes.get(0).getMarketId());
                    match_sql.setAddby(user.getUserId());
                    match_sql.setStatus("OPEN");
                    match_sql.setMaxbet(dto.getMaxbet());
                    match_sql.setMinbet(dto.getMinbet());
                    String fancyid = otherMarketRes.get(0).getMarketId();
                    String oddstype = fancyid.substring(fancyid.length() - 2);
                    match_sql.setMatchname(result.get(0).getEventData().getName());
                    match_sql.setOddstype(oddstype);
                    match_sql.setCreatedon(dateFormatForDB.format(new Date()));
                    match_sql.setUpdatedon(dateFormatForDB.format(new Date()));
                    matchFancysRepository.save(match_sql);
                }
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add Fancy data  successfully","status",true
                ));
            }
        }
        List<Odd_evenRes> oddEvendata = result.get(0).getMarketList().getOdd_even();
        if(oddEvendata!=null){
            List<Odd_evenRes> oddEvenRes = result.get(0).getMarketList().getOdd_even().stream().filter(oddEvenRes1 -> {
                return oddEvenRes1.getMarketId().equals(dto.getMarketid());
            }).toList();
            if(!oddEvenRes.isEmpty()){
                MatchFancy matchData = new MatchFancy();
                matchData.setEventid(oddEvenRes.get(0).getEid());
                matchData.setFancyid(oddEvenRes.get(0).getMarketId());
                matchData.setName(oddEvenRes.get(0).getTitle());
                matchData.setSportId(oddEvenRes.get(0).getSid());
                matchData.setRunnerid(oddEvenRes.get(0).getMarketId());
                matchData.setAddby(user.getUserId());
                matchData.setMatchname(result.get(0).getEventData().getName());
                matchData.setStatus("OPEN");
                matchData.setMaxBet(dto.getMaxbet());
                matchData.setMinBet(dto.getMinbet());
                String fancyId = oddEvenRes.get(0).getMarketId();
                String oddsType = fancyId.substring(fancyId.length() - 2);
                matchData.setOddstype(oddsType);
                matchData.setUpdatedOn(new Timestamp(new Date().getTime()));
                matchData.setCreatedon(new Date());
                matchFancyRepository.save(matchData);
                Optional<MatchFancyModel>matchFancyModel = matchFancysRepository.findByfancyid(dto.getMarketid());
                if(!matchFancyModel.isPresent()){
                    MatchFancyModel match_sql = new MatchFancyModel();
                    match_sql.setEventid(oddEvenRes.get(0).getEid());
                    match_sql.setFancyid(oddEvenRes.get(0).getMarketId());
                    match_sql.setName(oddEvenRes.get(0).getTitle());
                    match_sql.setSportid(oddEvenRes.get(0).getSid());
                    match_sql.setRunnerid(oddEvenRes.get(0).getMarketId());
                    match_sql.setAddby(user.getUserId());
                    match_sql.setStatus("OPEN");
                    match_sql.setMaxbet(dto.getMaxbet());
                    match_sql.setMinbet(dto.getMinbet());
                    String fancyid = oddEvenRes.get(0).getMarketId();
                    String oddstype = fancyid.substring(fancyid.length() - 2);
                    match_sql.setMatchname(result.get(0).getEventData().getName());
                    match_sql.setOddstype(oddstype);
                    match_sql.setCreatedon(dateFormatForDB.format(new Date()));
                    match_sql.setUpdatedon(dateFormatForDB.format(new Date()));
                    matchFancysRepository.save(match_sql);
                }
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add Fancy data  successfully","status",true
                ));

            }
        }
        List<SessionRes> sessiondata = result.get(0).getMarketList().getSession();
        if(sessiondata!=null){
            List<SessionRes> sessionRes = result.get(0).getMarketList().getSession().stream().filter(sessionRes1 -> {
                return sessionRes1.getMarketId().equals(dto.getMarketid());
            }).toList();
            if(!sessionRes.isEmpty()){
                MatchFancy matchData = new MatchFancy();
                matchData.setEventid(sessionRes.get(0).getEid());
                matchData.setFancyid(sessionRes.get(0).getMarketId());
                matchData.setName(sessionRes.get(0).getTitle());
                matchData.setSportId(sessionRes.get(0).getSid());
                matchData.setRunnerid(sessionRes.get(0).getMarketId());
                matchData.setAddby(user.getUserId());
                matchData.setMatchname(result.get(0).getEventData().getName());
                matchData.setStatus("OPEN");
                matchData.setMaxBet(dto.getMaxbet());
                matchData.setMinBet(dto.getMinbet());
                String fancyId = sessionRes.get(0).getMarketId();
                String oddsType = fancyId.substring(fancyId.length() - 2);
                matchData.setOddstype(oddsType);
                matchData.setUpdatedOn(new Timestamp(new Date().getTime()));
                matchData.setCreatedon(new Date());
                matchFancyRepository.save(matchData);
                matchFancyRepository.save(matchData);
                Optional<MatchFancyModel>matchFancyModel = matchFancysRepository.findByfancyid(dto.getMarketid());
                if(!matchFancyModel.isPresent()){
                    MatchFancyModel match_sql = new MatchFancyModel();
                    match_sql.setEventid(sessionRes.get(0).getEid());
                    match_sql.setFancyid(sessionRes.get(0).getMarketId());
                    match_sql.setName(sessionRes.get(0).getTitle());
                    match_sql.setSportid(sessionRes.get(0).getSid());
                    match_sql.setRunnerid(sessionRes.get(0).getMarketId());
                    match_sql.setAddby(user.getUserId());
                    match_sql.setStatus("OPEN");
                    match_sql.setMaxbet(dto.getMaxbet());
                    match_sql.setMinbet(dto.getMinbet());
                    String fancyid = sessionRes.get(0).getMarketId();
                    String oddstype = fancyid.substring(fancyid.length() - 2);
                    match_sql.setMatchname(result.get(0).getEventData().getName());
                    match_sql.setOddstype(oddstype);
                    match_sql.setCreatedon(dateFormatForDB.format(new Date()));
                    match_sql.setUpdatedon(dateFormatForDB.format(new Date()));
                    matchFancysRepository.save(match_sql);
                }
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add Fancy data  successfully","status",true
                ));
        }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Market id not match ","status",false));
    }

//    @PostMapping("/maxminfancyupdate")
//    public ResponseEntity<Map<String, Object>> findMaxData(@RequestBody AddFancyDTO dto) throws ResourceNotFoundException {
//        List<MatchFancy> markets = matchFancyRepository.findByEventid(dto.getEventid());
////        if (markets.isEmpty()) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Event not found ","status",false));
////        }
//
//        Map<String, List<Map<String, Object>>> groupedMarkets = markets.stream()
//                .map(market -> {
//                    Map<String, Object> selectedMap = new HashMap<>();
//                    selectedMap.put("betdelay", market.getBetDelay());
//                    selectedMap.put("maxbet", market.getMaxBet());
//                    selectedMap.put("minbet", market.getMinBet());
//                    selectedMap.put("oddstype", market.getOddstype() != null ? market.getOddstype() : "UNKNOWN"); // Handle null "oddstype"
//                    selectedMap.put("eventid", market.getEventid());
//                    selectedMap.put("matchname", market.getMatchname());
//                    return selectedMap;
//                })
//                .collect(Collectors.groupingBy(m -> (String) m.get("oddstype")));
//
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
//                "message", "Get data successfully", "status",true,"data", groupedMarkets));
//    }
    @PostMapping("/maxminfancyupdate")
    public ResponseEntity<Map<String, Object>> findMaxData(@RequestBody AddFancyDTO dto) throws ResourceNotFoundException {
        List<MatchFancy> markets = matchFancyRepository.findByEventid(dto.getEventid());
//        if (markets.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Event not found ","status",false));
//        }

        Map<String, List<Map<String, Object>>> groupedMarkets = markets.stream()
                .map(market -> {
                    Map<String, Object> selectedMap = new HashMap<>();
                    selectedMap.put("betdelay", market.getBetDelay());
                    selectedMap.put("maxbet", market.getMaxBet());
                    selectedMap.put("minbet", market.getMinBet());
                    selectedMap.put("oddstype", market.getOddstype() != null ? market.getOddstype() : "UNKNOWN"); // Handle null "oddstype"
                    selectedMap.put("eventid", market.getEventid());
                    selectedMap.put("matchname", market.getMatchname());
                    return selectedMap;
                })
                .collect(Collectors.groupingBy(m -> (String) m.get("oddstype")));

        Map<String, Object> data = new HashMap<>();
        data.put("F2", groupedMarkets); // groupedMarkets should be defined

        Map<String, Object> response = new HashMap<>();
        response.put("message", "fetch fancy rate");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/updateallfancy")
    public HashMap<String, Object> update_all_fancy (@RequestBody UpdateAllFancyDTO dto){
        HashMap<String, Object> matchFancy = matchFancyService.update_all_fancy(dto);
        return  matchFancy;
    }
    @PutMapping("/updatematchfancy")
    public HashMap<String, Object> update_One_fancy(@RequestBody UpdateFancyDTO dto) throws ResourceNotFoundException {
        HashMap<String, Object> matchFancy = matchFancyService.update_One_fancy(dto);
        return matchFancy ;
    }
    @PostMapping("/fetchmtype")
    public HashMap<String,Object> fetchmtype (@RequestBody EventDeDTO eventDeDTO){

        List<MatchFancy> matchFancies = matchFancyRepository.findByEventidAndIsActive(eventDeDTO.getEventid(),true);
        HashMap<String,Object> res = new HashMap<>();

        res.put("data",matchFancies.stream().map(data->new FetchMTypeRes(data.getEventid(), data.getName(), data.getFancyid(), data.getMtype(), data.getMaxBet(), data.getMinBet(), data.getBetDelay(), data.isShow(), data.getMatchname())));
        res.put("message","fetch mtype");
        return  res;
    }
    @PutMapping("/updatemtype")
    public ResponseEntity<?> update_m_type (@RequestBody UpdateMTypeDTO dto) {
        Optional<MatchFancy> checkFancyId = matchFancyRepository.findByFancyid(dto.getFancyid());
        if(!checkFancyId.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "please provide a valid fancyId","status",false));
        }
        Optional<MatchFancy> updateMType = matchFancyRepository.findByEventidAndFancyid(dto.getEventid(), dto.getFancyid());
        if(!updateMType.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "please provide a valid evenId","status",false));
        }
        MatchFancy updatedFancy = updateMType.get();
        String string1 = new String("player");
        String string2 = new String("session");
        if(dto.getMtype().equals(string1)){
            updatedFancy.setMaxBet(25000);
            updatedFancy.setMtype(dto.getMtype());
        } else if (dto.getMtype().equals(string2)) {
            updatedFancy.setMaxBet(100000);
            updatedFancy.setMtype(dto.getMtype());
        }
        matchFancyRepository.save(updatedFancy);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "update m_type  successfully","status",true));
    }
//    @GetMapping("/get_all_ready_fancy")
//    public HashMap<String, Object> get_all_ready_fancy ()throws ResourceNotFoundException{
//        HashMap<String,Object> get_all_ready_fancy = matchFancyService.get_all_ready_fancy();
//        return get_all_ready_fancy ;
//    }
    @PostMapping("/matchfancyresult")
    public ResponseEntity<?> match_fancy_result (@RequestBody AddFancyResultDTO dto){
        //Get the token userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
        }

        Optional<FancyResult> checkFancyResult =fancyResultRepository.findByFancyid(dto.getFancyid());
        if(checkFancyResult.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "fancyId already exits","status",false));
        }
        Optional<MatchFancy> matchResult = matchFancyRepository.findByFancyid(dto.getFancyid());
        if(!matchResult.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","fancyId not found","status",false));
        }
        User user = (User) authentication.getPrincipal();
        MatchFancy matchFancy = matchResult.get();
        matchFancy.setStatus("CLOSE");
        matchFancyRepository.save(matchFancy);
        FancyResult fancyResult = new FancyResult();
        fancyResult.setMatchid(matchFancy.getEventid());
        fancyResult.setMatchname(matchFancy.getMatchname());
        fancyResult.setResult(dto.getResult());
        fancyResult.setFancyid(matchFancy.getFancyid());
        fancyResult.setFancyname(matchFancy.getName());
        fancyResult.setResultdeclareby(user.getUserId());
        fancyResult.setFancytype(matchFancy.getOddstype());
        fancyResult.setCreatedon(new Date());
        //SQl
        FancyResultsModel fancyResultsModel = new FancyResultsModel();
        fancyResultsModel.setMatchid(matchFancy.getEventid());
        fancyResultsModel.setMatchname(matchFancy.getMatchname());
        fancyResultsModel.setResult(dto.getResult());
        fancyResultsModel.setFancyid(matchFancy.getFancyid());
        fancyResultsModel.setFancyname(matchFancy.getName());
        fancyResultsModel.setResultdeclareby(user.getUserId());
        fancyResultsModel.setFancytype(matchFancy.getOddstype());
        fancyResultsModel.setCreatedon(new Date());
        fancyResultRepository.save(fancyResult);
        fancyResultsRepository.save(fancyResultsModel);
        return ResponseEntity.ok()
                .body(Map.of("message", "result set  successfully ", "status",true,"data", fancyResult));
    }
//
//    @PutMapping("/matchfancySuspand")
//    public ResponseEntity<?> getMatchFancySuspand (@RequestBody UpdateFancyDTO dto){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
//        }
//        User user = (User) authentication.getPrincipal();
//        Optional<MatchFancy> match_fancy_result = matchFancyRepository.findByFancyid(dto.getFancyid());
//        if(!match_fancy_result.isPresent()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "fancyId not found","status",false));
//        }
//
//        Optional<MatchFancyModel> matchFancyModel = matchFancysRepository.findByfancyid(dto.getFancyid());
//        if(!matchFancyModel.isPresent()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "fancyId not found from sql db","status",false));
//        }
//        MatchFancyModel matchFancyModel1 = matchFancyModel.get();
//        if(matchFancyModel1.getStatus().equals("SUSPENDED")){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "please choose another this fancyid allready suspended","status",false));
//        }
//        if ("F2".equals(matchFancyModel1.getOddstype()) ||
//                "F3".equals(matchFancyModel1.getOddstype()) ||
//                "OE".equals(matchFancyModel1.getOddstype())) {
//            matchFancyModel1.setStatus("SUSPENDED");
//            matchFancyModel1.setSuspendedby(user.getUserId());
//            matchFancysRepository.save(matchFancyModel1);
//        }else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Not match any oddsType data ","status",false));
//        }
//
//        MatchFancy matchFancy = match_fancy_result.get();
//        if ("F2".equals(matchFancy.getOddstype()) ||
//                "F3".equals(matchFancy.getOddstype()) ||
//                "OE".equals(matchFancy.getOddstype())) {
//            matchFancy.setStatus("SUSPENDED");
//            matchFancy.setSuspendedBy(user.getUserId());
//            matchFancyRepository.save(matchFancy);
//        }else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Not match any oddsType data ","status",false));
//        }
//
//        return ResponseEntity.ok()
//                .body(Map.of("message", "Match fancy Suspand  successfully ","status",true, "data", matchFancy));
//    }
//
//    @PostMapping("/allfancySuspand")
//    public ResponseEntity<Object> suspendAllFancy(@RequestBody FancySupDTO dto) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
//            }
//            User user = (User) authentication.getPrincipal();
//            String fancyIds = dto.getFancyid();
//            int eventId = dto.getEventid();
//            List<MatchFancy> matchingFancies = matchFancyRepository.findByFancyidInAndEventid(fancyIds, eventId);
//            for (MatchFancy matchFancy : matchingFancies) {
//                matchFancy.setStatus("SUSPENDED");
//                matchFancy.setSuspendedBy(user.getUserId());
//                matchFancyRepository.save(matchFancy);
//            }
//            matchFancysRepository.updateAllFancyStatus("SUSPENDED", user.getUserId(), fancyIds, eventId);
//            return ResponseEntity.ok()
//                    .body(Map.of("message", "All fancy suspended successfully","status",true));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("message", "Something went wrong","status",false));
//        }
//    }
//
    @PostMapping("/getfancySuspended")
    public ResponseEntity<Object> getFancySuspended(@RequestBody FancySupDTO dto) {
        try {
            int eventid = dto.getEventid();
            List<MatchFancy> getFancySuspended = matchFancyRepository.findByStatusAndEventidOrderByCreatedonDesc("SUSPENDED", eventid);
            List<String> marketIds = getFancySuspended.stream()
                    .map(MatchFancy::getFancyid)
                    .collect(Collectors.toList());
            List<BetList> mongoMarketCount = betMongoRepository.findByMatchidAndMarketidInAndIsactiveAndStatus(eventid, marketIds, false, "SUSPENDED");
            List<GetFancySuspendedRes> fancyResponses = new ArrayList<>();
            getFancySuspended.forEach(item -> {
                int count = (int) mongoMarketCount.stream()
                        .filter(i -> i.getMarketid().equals(item.getFancyid()))
                        .count();

                GetFancySuspendedRes response = new GetFancySuspendedRes();
                response.setFancyid(item.getFancyid());
                response.setName(item.getName());
                response.setSuspendedby(item.getSuspendedBy());
                response.setCreatedon(item.getCreatedon());
                response.setCount(count);
                fancyResponses.add(response);
            });

            return ResponseEntity.ok()
                    .body(Map.of("message", "All fancy suspended successfully","status",true,"data",fancyResponses));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Something went wrong");
        }
    }
//t_matchFancy table mysql
    @PostMapping("/matchfancy")
    public ResponseEntity<Map<String, Object>> getMatchFancy(@RequestBody FancySupDTO dto) {
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("eventid").is(dto.getEventid())
                        .and("isactive").is(true)
                        .and("status").is("OPEN")
                        .and("is_show").is(true)),
                lookup("t_rsfancy_updated_result", "fancyid", "fancyid", "rsfancy_data"),
                unwind("rsfancy_data", true),
                Aggregation.project("_id", "fancyid", "name", "matchname", "oddstype", "eventid", "createdon", "is_show")
                        .and("rsfancy_data.result").as("result"),
                Aggregation.sort(Sort.Direction.DESC, "createdon")
        );

        List<MatchFancyRes> matchResults = mongoTemplate.aggregate(aggregation, "t_matchfancy", MatchFancyRes.class).getMappedResults();
        List<String> fancyIds = matchResults.stream().map(MatchFancyRes::getFancyid).collect(Collectors.toList());

        List<EXMatchBet> mongoMarketCount = betListRepository.findByMarketIdInAndIsActive(fancyIds, true);

        matchResults.forEach(item -> {
            int count = (int) mongoMarketCount.stream()
                    .filter(i -> i.getMarketId().equals(item.getFancyid()))
                    .count();
                    item.setCount(count);
                     System.out.println(item+"item data");
        });
        List<EXMatchBet> sqlCount = betListsRepository.findByMarketIdInAndIsActive(fancyIds,true);
        matchResults.forEach(item -> {
            int count1 = (int) sqlCount.stream()
                    .filter(i -> i.getMarketId().equals(item.getFancyid()))
                    .count();
                   item.setCount1(count1);

        });
        Map<String, Object> response = new HashMap<>();
        response.put("message", "fetch match fancy successfully");
        response.put("status",true);
        response.put("data", groupByOddstype(matchResults));
        return ResponseEntity.ok(response);

    }
    private Map<String, List<MatchFancyRes>> groupByOddstype(List<MatchFancyRes> data) {
        // Group data by oddstype
        Map<String, List<MatchFancyRes>> groupedData = new HashMap<>();
        for (MatchFancyRes item : data) {
            groupedData.computeIfAbsent(item.getOddstype(), k -> new ArrayList<>()).add(item);
        }
        return groupedData;
    }
//
//    @PostMapping("/hidefancy")
//    public ResponseEntity<Map<String, Object>> hidefancy(@RequestBody FancySupDTO dto) {
//        Aggregation aggregation = Aggregation.newAggregation(
//                match(Criteria.where("eventid").is(dto.getEventid())
//                        .and("isactive").is(true)
//                        .and("status").is("OPEN")
//                        .and("is_show").is(false)),
//                lookup("t_rsfancy_updated_result", "fancyid", "fancyid", "rsfancy_data"),
//                unwind("rsfancy_data", true),
//                Aggregation.project("_id", "fancyid", "name", "matchname", "oddstype", "eventid", "createdon", "is_show")
//                        .and("rsfancy_data.result").as("result"),
//                Aggregation.sort(Sort.Direction.DESC, "createdon")
//        );
//
//        List<HideFancyRes> matchResults = mongoTemplate.aggregate(aggregation, "t_matchFancy", HideFancyRes.class).getMappedResults();
//        List<String> fancyIds = matchResults.stream().map(HideFancyRes::getFancyid).collect(Collectors.toList());
//
//        List<BetList> mongoMarketCount = betListRepository.findByMarketidInAndIsactive(fancyIds, true);
//
//        matchResults.forEach(item -> {
//            int count = (int) mongoMarketCount.stream()
//                    .filter(i -> i.getMarketid().equals(item.getFancyid()))
//                    .count();
//            item.setCount(count);
//            System.out.println(item+"item data");
//            System.out.println(count+" count data");
//        });
//        List<BetListModel> sqlCount = betListsRepository.findByMarketidInAndIsactive(fancyIds,true);
//        matchResults.forEach(item -> {
//            int count1 = (int) sqlCount.stream()
//                    .filter(i -> i.getMarketid().equals(item.getFancyid()))
//                    .count();
//            item.setCount1(count1);
//
//            System.out.println(count1+"sql count data");
//        });
//        Map<String, Object> response = new HashMap<>();
//        response.put("status",true);
//        response.put("message", "fetch hide fancy successfully");
//        response.put("data", groupByOdds(matchResults));
//        return ResponseEntity.ok(response);
//
//    }
//    private Map<String, List<HideFancyRes>> groupByOdds(List<HideFancyRes> data) {
//        // Group data by oddstype
//        Map<String, List<HideFancyRes>> groupedDatas = new HashMap<>();
//        for (HideFancyRes item : data) {
//            groupedDatas.computeIfAbsent(item.getOddstype(), k -> new ArrayList<>()).add(item);
//        }
//        return groupedDatas;
//    }
//
//
//    @DeleteMapping("/removedfancy")
//    public ResponseEntity<?> removeMarket(@RequestParam String fancyid){
//        Optional<MatchFancy> checkMarketId = matchFancyRepository.findByFancyid(fancyid);
//        if (!checkMarketId.isPresent()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Market allready removed","status",false));
//        }
//        matchFancyRepository.deleteByFancyid(fancyid);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
//                "message", "Successfully removed","status",true));
//    }
//
//    @PostMapping("/fancyresult")
//        public ResponseEntity<Map<String, Object>> fancyresult(@RequestBody FancySupDTO dto) {
//        ObjectCommonResponseModel objectCommonResponseModel = new ObjectCommonResponseModel();
//            List<Map<String,Object>> result = fancyResultsRepository.getFancyResult(dto.getMatchid());
//
//        }

        @PostMapping("/allreadymatchfancy")
        public ResponseEntity<Object> getAllReadyMatchFancy(@RequestBody EventDeDTO eventDeDTO){
            ObjectCommonResponseModel objectCommonResponseModel = new ObjectCommonResponseModel();
            List<Map<String,Object>> response =  matchFancyRepository.getByEventId(eventDeDTO.getEventid());
            if(response == null){
                objectCommonResponseModel.setData(null);
                objectCommonResponseModel.setMessage("No Data found");
                return new ResponseEntity<>(objectCommonResponseModel,HttpStatus.OK);
            }
            objectCommonResponseModel.setMessage("Fancy Found");
            objectCommonResponseModel.setData(response);
            objectCommonResponseModel.setStatus(true);
            return new ResponseEntity<>(objectCommonResponseModel,HttpStatus.OK);

        }



}

