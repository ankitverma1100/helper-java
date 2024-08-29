package helper.helperapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import helper.helperapi.dto.AddSportDTO;
import helper.helperapi.dto.EventDeDTO;
import helper.helperapi.dto.GetMarketDTO;
import helper.helperapi.dto.OtherEvent;
import helper.helperapi.entity.Event;
import helper.helperapi.entity.Sports;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.MarketItemRes;
import helper.helperapi.modelResponse.eventResponse.EventDataClass;
import helper.helperapi.modelResponse.eventResponse.GameData;
import helper.helperapi.modelResponse.marketResponse.*;
import helper.helperapi.models.Market;
import helper.helperapi.models.Series;
import helper.helperapi.models.Sport;
import helper.helperapi.mysqlRepo.EventRepo;
import helper.helperapi.mysqlRepo.SportsRepo;
import helper.helperapi.repository.MarketRepository;
import helper.helperapi.repository.SeriesRepository;
import helper.helperapi.repository.SportRepository;
import helper.helperapi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class EventController {
    @Value("${url_Cricket}")
    private String url;

    @Value("${url_Other}")
    private String other_Url;
    @Autowired
    EventRepo eventRepository;
    @Autowired
    SeriesRepository seriesRepository;

    @Autowired
    SportsRepo sportRepository;

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    EventService eventService;

    @GetMapping("/getEvent")
    public GameData getEvents() {
        RestTemplate restTemplate = new RestTemplate();
        GameData data = restTemplate.getForObject(url, GameData.class);
        return data;
    }



    @PostMapping("/getOtherEvent")
    public List<EventDataClass> getOtherEvent(@RequestBody OtherEvent otherEvent) {
        RestTemplate restTemplate = new RestTemplate();
        GameData data = restTemplate.getForObject(other_Url, GameData.class);
        GameData data1 = restTemplate.getForObject(url, GameData.class);
        if(otherEvent.getSportId()==4){
            List<EventDataClass> result = new ArrayList<>();
            result = data1.getGameList().get(0).getEventList();
            return  result;
        }
        if (data == null) {
            return null;
        }
        List<EventDataClass> result = new ArrayList<>();
        for (int i = 0; i < data.getGameList().size(); i++) {
            if (otherEvent.getSportId() == data.getGameList().get(i).getSportId()) {
                result = data.getGameList().get(i).getEventList();
                return result;
            }

        }

        return result;
    }


    @PostMapping("/t_addTabSports")
    public ResponseEntity<?> t_addTabSport(@RequestBody AddSportDTO addSportDTO){
        List<Sports> fetchT_sports = sportRepository.findByTypeAndStatus("betfair", true);
        System.out.println("event id is"+ addSportDTO.getEventid());
        if (fetchT_sports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Type not betfair. Please contact admin","status",false
            ));
        }
        Optional<Sports> checkSportId = sportRepository.findBySportid(addSportDTO.getSportid());
            if (!checkSportId.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "message", "Please provide a valid sportId.","status",false
                ));
            }

        System.out.println("event id is"+ addSportDTO.getEventid());
        Optional<Event> event1 = eventRepository.findByEventid(addSportDTO.getEventid());
        if (event1.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Event already added for this eventID","status",false));

        }

        eventService.t_addTabSports(addSportDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Event added successfully","status",true
        ));

    }

    @GetMapping("/getEventId")
    public ResponseEntity<Object> getAllEvent(){
        List<Map<String,Object>> events = eventService.getEventId();
        if (events.isEmpty()) {
            return ResponseEntity.ok()
                    .body(Map.of("message", "No data", "data", events));
        }
        return ResponseEntity.ok()
                .body(Map.of("message", "get events successfully", "data", events));

    }

    @DeleteMapping("/removeEvents")
    public ResponseEntity<?> removeEvent(@RequestParam int eventid) throws ResourceNotFoundException {
        Optional<Event> events = eventRepository.findByEventid(eventid);
        if (!events.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Event id allready remove","status",false));
        }
        eventService.removeEvent(eventid);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Remove successfully","status",true
        ));
    }

    @PostMapping("/getMarketlist")
    public  Object getMarketList(@RequestBody GetMarketDTO getMarketDTO) throws ResourceNotFoundException {
        List<Sports> fetchT_sports = sportRepository.findByTypeAndStatus("Betfair", true);
        MarketData data;
        if (fetchT_sports.isEmpty()) {
            throw new ResourceNotFoundException("Type not betfair. Please contact admin.");
        }
        Optional<Sports> checkSportId = sportRepository.findBySportid(getMarketDTO.getSportid());
        if (!checkSportId.isPresent()) {
            throw new ResourceNotFoundException("Please provide a valid sportId.");
        }
        RestTemplate restTemplate = new RestTemplate();
        data = restTemplate.getForObject(url, MarketData.class);
        MarketData  data1 = restTemplate.getForObject(other_Url, MarketData.class);
       if(getMarketDTO.getSportid()==4){
           for (MarketClass marketClass : data.getGameList()) {
               for (MarketDataClass marketDataClass : marketClass.getEventList()) {
                   if (marketDataClass.getEventData().getEventId() == getMarketDTO.getEventId()){
                       ObjectMapper objectMapper = new ObjectMapper();
                       Map<String, Object> hash = objectMapper.convertValue(marketDataClass.getMarketList(), Map.class);
                       List<Object> list = new ArrayList<>();
                       for (String key: hash.keySet()) {
                           List<Object> list2 = new ArrayList<>();
                           if (key.equalsIgnoreCase("bookmaker")) {
                               List<Object> l = (List) hash.get(key);
                               l.forEach(o -> {
                                   list2.add(o);
                               });
                           }
                           if (list2.isEmpty()) {
                               list.add(hash.get(key));
                           }else {
                               list2.forEach(o -> {
                                   list.add(o);
                               });
                           }
                       }
                       HashMap res = new HashMap<>();
                       res.put("eventData", marketDataClass.getEventData());
                       res.put("marketList", list);

                       return res;
                   }
               }
           }
       }
        for (MarketClass marketClass : data1.getGameList()) {
            for (MarketDataClass marketDataClass : marketClass.getEventList()) {
                MarketList marketList = marketDataClass.getMarketList();
                if (marketList != null) {
                    MatchOdd matchOdd = marketList.getMatch_odd();
                    if (marketDataClass.getEventData().getEventId() == getMarketDTO.getEventId()) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> hash = objectMapper.convertValue(marketList, Map.class);
                        List<Object> list = new ArrayList<>();
                        for (String key : hash.keySet()) {
                            List<Object> list2 = new ArrayList<>();
                            if (key.equalsIgnoreCase("bookmaker")) {
                                List<Object> l = (List) hash.get(key);
                                if (l != null) {
                                    l.forEach(o -> {
                                        list2.add(o);
                                    });
                                }
                            }
                            if (list2.isEmpty()) {
                                list.add(hash.get(key));
                            } else {
                                list2.forEach(o -> {
                                    list.add(o);
                                });
                            }
                        }
                        HashMap res = new HashMap<>();
                        res.put("eventData", marketDataClass.getEventData());
                        res.put("marketList", list);

                        return res;
                    }
                }
            }
        }
        throw new ResourceNotFoundException("Event id not match");

    }


    @PostMapping("/activemarket")
    public ResponseEntity<?> fetchtEvent(@RequestBody EventDeDTO deDTO) {
        List<Event> event = eventRepository.findBySportid(deDTO.getSportid());
        if (event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "provide a valid sport id","status",false
            ));
        }
        //Sort sort = Sort.by(Sort.Direction.ASC, "open_date");
        Sort sort = Sort.by(Sort.Direction.DESC, "open_date");
//        List<Event> events = eventRepository.findBySportidAndIsactive(deDTO.getSportid(), true);
        List<Map<String,Object>>  data = eventRepository.getBySportIdAndActive(deDTO.getSportid(), true);
//
//        List<Event> events = eventRepository.findBySportidAndIsactive(deDTO.getSportid(), true);
//        Collections.sort(events, (e1, e2) -> e2.getOpenDate().compareTo(e1.getOpenDate()));
        return ResponseEntity.ok()
                .body(Map.of("message", "fetch t_events and sort events data successfully","status",true,"data", data));
    }

    @PostMapping("/fetchT_events1")
    public ResponseEntity<?> fetchtMarket(@RequestBody EventDeDTO deDTO) {
        List<Event> event = eventRepository.findBySportid(deDTO.getSportid());
        if (event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "provide a valid sport id","status",false
            ));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "startdate");
        List<Market> markets = marketRepository.findBySportidAndIsactiveIsTrueAndStatusIsTrue(deDTO.getSportid());
        Collections.sort(markets, (e1, e2) -> e2.getStartdate().compareTo(e1.getStartdate()));
        return ResponseEntity.ok()
                .body(Map.of("message", "fetch market and sort market data successfully","status",true,"data", markets));
    }

    @GetMapping("/fetchsports")
    public ResponseEntity<?> fetchsports() {
        List<Event> event = eventRepository.findBySportid(4);
        if (event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "provide a valid sport id","status",false
            ));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "open_date");
        List<Event> event1 = eventRepository.findBySportid(4);
        Collections.sort(event1, (e1, e2) -> e2.getOpenDate().compareTo(e1.getOpenDate()));
        return ResponseEntity.ok()
                .body(Map.of("message", "fetch sports event successfully","status",true ,"data", event1));
    }

    @PostMapping("/activeEvents")
    public ResponseEntity<Object> activeEvents(@RequestBody EventDeDTO deDTO) {
        Optional<Sports> sport = sportRepository.findBySportid(deDTO.getSportid());
        if (sport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Sport not found","status",false));
        }
        Sports sport1 = sport.get();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdon");
        List<Event> fetcht_event = eventRepository.findBySportidAndIsactive(deDTO.getSportid(), true);
        Collections.sort(fetcht_event, (e1, e2) -> e2.getCreatedon().compareTo(e1.getCreatedon()));

        if (fetcht_event.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Events not found","status",false));
        }
        List<Map<String, Object>> data = new ArrayList<>();
        for (Event item : fetcht_event) {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("eventid", item.getEventid());
            eventData.put("sportid", item.getSportid());
            eventData.put("eventname", item.getEventname());
            eventData.put("betlock", item.getBetLock());
            eventData.put("fancylock", item.isFancyLock());
            eventData.put("in_play", item.isInPlay());
            eventData.put("sports", sport1.getName());
            data.add(eventData);
        }

        return ResponseEntity.ok()
                .body(Map.of("message", "Get all active events data successfully","status",true, "data", data));
    }

    @GetMapping("/allreadybetlock")
    public ResponseEntity<?> allreadybetlock() {
        List<Map<String,Object>> events = eventRepository.getLockedEvent(true);
        return ResponseEntity.ok()
                .body(Map.of("message", "get allready betlock","status",true, "data", events));
    }

    @GetMapping("/allreadyfancylock")
    public ResponseEntity<?> allreadyfancylock() {
        List<Map<String,Object>> events = eventRepository.getFancyLock(true);
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Currently no fancy is locked","status",false));
        }

        return ResponseEntity.ok()
                .body(Map.of("message", "get allready fancylocak", "status",true,"data", events));
    }

}

