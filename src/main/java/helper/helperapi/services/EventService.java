package helper.helperapi.services;


import helper.helperapi.dto.AddSportDTO;
import helper.helperapi.entity.Event;
import helper.helperapi.entity.Sports;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.eventResponse.EventClass;
import helper.helperapi.modelResponse.eventResponse.EventDataClass;
import helper.helperapi.modelResponse.eventResponse.GameData;
import helper.helperapi.models.Series;
import helper.helperapi.models.Sport;
import helper.helperapi.mysqlRepo.EventRepo;
import helper.helperapi.mysqlRepo.SportsRepo;
import helper.helperapi.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class EventService {
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
    SimpleDateFormat dateFormatForDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//
    public ResponseEntity<?> t_addTabSports(AddSportDTO addSportDTO) {
        List<Sports> fetchT_sports = sportRepository.findByTypeAndStatus("betfair", true);
        if (fetchT_sports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Type not betfair. Please contact admin.","status",false));
        }
        Optional<Sports> checkSportId = sportRepository.findBySportid(addSportDTO.getSportid());
        if (!checkSportId.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Please provide a valid sportId.","status",false));
        }
        Optional<Event> event1 = eventRepository.findByEventid(addSportDTO.getEventid());
        if (event1.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Event already added for this eventID","status",false));

        }
        RestTemplate restTemplate = new RestTemplate();
        GameData data = restTemplate.getForObject(other_Url, GameData.class);
        GameData data1 = restTemplate.getForObject(url, GameData.class);
        if (addSportDTO.getSportid() == 4) {
            for (EventClass addEventClass : data.getGameList()) {
                for (EventDataClass addEventDataClass : addEventClass.getEventList()) {
                    Event event = new Event();
                    Series series = new Series();
                    if (addEventDataClass.getEventData().getEventId() == addSportDTO.getEventid() && addEventDataClass.getEventData().getSportId() == addSportDTO.getSportid()) {
                        event.setEventid(addEventDataClass.getEventData().getEventId());
                        event.setSportid(addEventDataClass.getEventData().getSportId());
                        event.setLiveTv(addEventDataClass.getEventData().getIsTv());
                        event.setSeriesid(addEventDataClass.getEventData().getCompId());
                        event.setStatus(addEventDataClass.getEventData().getStatus());
                        event.setFancy(false);
                        event.setEventname(addEventDataClass.getEventData().getName());
                        event.setInPlay(addEventDataClass.getEventData().getType().equals("IN_PLAY") ? true : false);
                        try {
                            event.setMatchstartdate(dateFormatForDB.parse(new Date().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        event.setOpenDate(dateFormatForDB.format(new Date()));
                        event.setCreatedon(new Date().toString());
                        event.setUpdatedOn(new Timestamp(new Date().getTime()));
                        series.setSportid(addEventDataClass.getEventData().getSportId());
                        series.setSeriesid(addEventDataClass.getEventData().getCompId());
                        series.setSeriesname(addEventDataClass.getEventData().getLeague());
                        series.setStatus(addEventDataClass.getEventData().getStatus());
                        series.setCreatedon(new Date());
                        series.setUpdatedon(new Date());
                        series = seriesRepository.save(series);
                        event = eventRepository.save(event);
                    }
                }
            }

        }
        List<EventDataClass> result = new ArrayList<>();
        for (GameData gameData : List.of(data, data1)) {
            if (gameData != null) {
                for (int i = 0; i < gameData.getGameList().size(); i++) {
                    if (addSportDTO.getSportid() == gameData.getGameList().get(i).getSportId()) {
                        List<EventDataClass> events = gameData.getGameList().get(i).getEventList();
                        for (EventDataClass eventData : events) {
                            if (eventData.getEventData().getEventId() == addSportDTO.getEventid()) {
                                Event event = new Event();
                                Series series = new Series();
                                event.setEventid(eventData.getEventData().getEventId());
                                event.setSportid(eventData.getEventData().getSportId());
                                event.setLiveTv(eventData.getEventData().getIsTv());
                                event.setSeriesid(eventData.getEventData().getCompId());
                                event.setStatus(eventData.getEventData().getStatus());
                                if(eventData.getEventData().getFancy()!=null){
                                    event.setFancy(true);
                                }else {
                                    event.setFancy(false);
                                }
                                System.out.println("in here");
                                event.setEventname(eventData.getEventData().getName());
                                event.setInPlay(eventData.getEventData().getType().equals("IN_PLAY") ? true : false);
//                                String time = dateFormatForDB.format(new Date());
//                                Date matchStartData = dateFormatForDB.parse(time,)
//                                try {
                                    event.setMatchstartdate(new Date());
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                    throw new RuntimeException(e);
//                                }
                                event.setOpenDate(dateFormatForDB.format(new Date()));
                                event.setCreatedon(new Date().toString());
                                event.setUpdatedOn(new Timestamp(new Date().getTime()));
                                //series
                                series.setSportid(eventData.getEventData().getSportId());
                                series.setSeriesid(eventData.getEventData().getCompId());
                                series.setSeriesname(eventData.getEventData().getLeague());
                                series.setStatus(eventData.getEventData().getStatus());
                                series.setCreatedon(new Date());
                                series.setUpdatedon(new Date());
                                try{
                                series = seriesRepository.save(series);
                                event = eventRepository.save(event);}
                                catch (Exception e){
                                    System.out.println("here");
                                    e.printStackTrace();

                                }
                                System.out.println("here");
                            }
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok()
                .body(Map.of("message", "Event added successfully.","status",true));


    }

    public List<Map<String,Object>> getEventId() {
        List<Map<String,Object>> events = eventRepository.getAllEventId();
        return events;
    }

    public ResponseEntity<?> removeEvent(int eventid) throws ResourceNotFoundException {
        Optional<Event> checkSportId = eventRepository.findByEventid(eventid);
        if (!checkSportId.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Please provide a valid eventId.","status",false
            ));
        } else {
            try {
                 eventRepository.deleteByEventid(eventid);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Remove successfully","status",true
        ));
    }


}
