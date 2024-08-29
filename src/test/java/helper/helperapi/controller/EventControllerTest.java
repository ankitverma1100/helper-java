//package helper.helperapi.controller;
//
//import helper.helperapi.dto.AddSportDTO;
//import helper.helperapi.dto.EventDeDTO;
//import helper.helperapi.dto.OtherEvent;
//import helper.helperapi.entity.Event;
//import helper.helperapi.exception.ResourceNotFoundException;
//import helper.helperapi.modelResponse.AllReadRes;
//import helper.helperapi.modelResponse.eventResponse.EventDataClass;
//import helper.helperapi.modelResponse.eventResponse.GameData;
//import helper.helperapi.models.Market;
//import helper.helperapi.models.Sport;
//import helper.helperapi.repository.EventRepository;
//import helper.helperapi.repository.MarketRepository;
//import helper.helperapi.repository.SportRepository;
//import helper.helperapi.services.EventService;
//import helper.helperapi.services.SportsService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class EventControllerTest {
//
//    @Mock
//    SportRepository sportRepository;
//
//    @Mock
//    EventRepository eventRepository;
//
//    @Mock
//    MarketRepository marketRepository;
//
//    @InjectMocks
//    EventController eventController ;
//
//    @Mock
//    EventService eventService;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @Value("${url_Other}")
//    private String other_Url;
//
//    @Test
//    void getEvents(){
//    }
//
//    @Test
//    void getOtherEvent() {
//    }
//
//    @Test
//    void t_addTabSport() {
//        AddSportDTO addSportDTO = new AddSportDTO();
//        addSportDTO.setSportid(2);
//        addSportDTO.setEventid(234);
//        Sport sport1 = new Sport();
//        sport1.setSportid(1);
//        sport1.setName("Tennis");
//        Sport sport2 = new Sport();
//        sport2.setName("Football");
//        List<Sport> sports = Arrays.asList(sport1, sport2);
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(Collections.emptyList());
//        ResponseEntity<?> response = eventController.t_addTabSport(addSportDTO);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        //2
//        List<Sport> sportlists = Arrays.asList(sport1, sport2);
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(sportlists);
//        Sport addsport  = new Sport();
//        Optional<Sport> getSport = Optional.of(addsport);
//        when(sportRepository.findBySportid(3333)).thenReturn(getSport);
//        ResponseEntity<?> responseEntity = eventController.t_addTabSport(addSportDTO);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        //3
//        Optional<Sport> findSport  = Optional.of(addsport);
//        when(sportRepository.findBySportid(addSportDTO.getSportid())).thenReturn(findSport);
//        Event event  = new Event();
//        Optional<Event> getEvent = Optional.of(event);
//        //Test ads sport already exists
//        when(eventRepository.findByEventid(addSportDTO.getEventid())).thenReturn(getEvent);
//        ResponseEntity<?> responsemessage = eventController.t_addTabSport(addSportDTO);
//        assertEquals(HttpStatus.BAD_REQUEST, responsemessage.getStatusCode());
//        //4
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(Collections.emptyList());
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(sportlists);
//        when(eventRepository.findByEventid(addSportDTO.getEventid())).thenReturn(Optional.empty());
//        when(eventRepository.findByEventid(3)).thenReturn(getEvent);
//        ResponseEntity<?> resp = eventController.t_addTabSport(addSportDTO);
//        assertEquals(HttpStatus.OK, resp.getStatusCode());
//
//
//    }
//
//    @Test
//    void getAllEvent() {
//        Event event = new Event();
//        event.setEventid(234);
//        //2
//        Event eventdata = new Event();
//        eventdata.setEventid(678);
//        List<Event> eventList = Arrays.asList(event, eventdata);
//        when(eventService.getEventId()).thenReturn(Collections.emptyList());
//        ResponseEntity<?> responseEntity = eventController.getAllEvent();
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        //2
//        when(eventService.getEventId()).thenReturn(eventList);
//        ResponseEntity<?> response= eventController.getAllEvent();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//
//    @Test
//    void removeEvent() throws ResourceNotFoundException {
//        int eventid = 23451;
//        Event addEvent = new Event();
//        Optional<Event> getSport = Optional.of(addEvent);
//        Mockito.when(eventRepository.findByEventid(eventid)).thenReturn(Optional.empty());
//        ResponseEntity<?> response = eventController.removeEvent(eventid);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        //2
//        when(eventRepository.findByEventid(eventid)).thenReturn(getSport);
//        ResponseEntity<?> responsemessage = eventController.removeEvent(eventid);
//        assertEquals(HttpStatus.OK, responsemessage.getStatusCode());
//    }
//
//    @Test
//    void getMarketList() {
//    }
//
//    @Test
//    void fetchtEvent() {
//        EventDeDTO deDTO =new EventDeDTO();
//        deDTO.setSportid(3);
//        // Create Event instances
//        Event event1 = new Event();
//        event1.setOpen_date("10-02-2023");
//        event1.setEventid(123);
//
//        Event event2 = new Event();
//        event2.setOpen_date("10-04-2023");
//        event2.setEventid(124);
//
//        List<Event> eventList = Arrays.asList(event1, event2);
//        Mockito.when(eventRepository.findBySportid(deDTO.getSportid())).thenReturn(Collections.emptyList());
//        ResponseEntity<?> response = eventController.fetchtEvent(deDTO);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//        Mockito.when(eventRepository.findBySportid(deDTO.getSportid())).thenReturn(eventList);
//        Mockito.when(eventRepository.findBySportidAndIsactive(deDTO.getSportid(), true)).thenReturn(eventList);
//        ResponseEntity<?> res = eventController.fetchtEvent(deDTO);
//        assertEquals(HttpStatus.OK, res.getStatusCode());
//    }
//
//    @Test
//    void fetchtMarket() {
//        EventDeDTO deDTO = new EventDeDTO();
//        deDTO.setSportid(3);
//
//        // Create Event instances
//        Event event1 = new Event();
//        event1.setOpen_date("10-02-2023");
//        event1.setEventid(123);
//
//        Event event2 = new Event();
//        event2.setEventid(124);
//
//        Market market = new Market();
//        market.setOpendate("10-03-2023");
//        List<Event> eventList = Arrays.asList(event1, event2);
//
//        Mockito.when(eventRepository.findBySportid(deDTO.getSportid())).thenReturn(Collections.emptyList());
//        ResponseEntity<?> response = eventController.fetchtMarket(deDTO);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//        Mockito.when(eventRepository.findBySportid(deDTO.getSportid())).thenReturn(eventList);
//
//        Mockito.when(marketRepository.findBySportidAndIsactiveIsTrueAndStatusIsTrue(deDTO.getSportid())).thenReturn(Collections.emptyList());
//
//        ResponseEntity<?> res = eventController.fetchtMarket(deDTO);
//        assertEquals(HttpStatus.OK, res.getStatusCode());
//    }
//
//    @Test
//    void fetchsports() {
//        Event event1 = new Event();
//        event1.setOpenDate("10-02-2023");
//        event1.setEventid(123);
//
//        Event event2 = new Event();
//        event2.setOpenDate("10-04-2023");
//        event2.setEventid(124);
//
//        List<Event> eventList = Arrays.asList(event1, event2);
//        Mockito.when(eventRepository.findBySportid(4)).thenReturn(Collections.emptyList());
//        ResponseEntity<?> response = eventController.fetchsports();
//        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
//        //2
//        Mockito.when(eventRepository.findBySportid(4)).thenReturn(eventList);
//        Mockito.when(eventRepository.findBySportid(4)).thenReturn(eventList);
//        ResponseEntity<?> res = eventController.fetchsports();
//        assertEquals(HttpStatus.OK, res.getStatusCode());
//
//    }
//
//    @Test
//    void activeEvents() {
//        EventDeDTO  deDTO = new EventDeDTO();
//        deDTO.setSportid(2);
//        Sport addSport = new Sport();
//
//        Event event = new Event();
//        event.setEventid(234);
//        event.setCreatedon(new Date().toString());
//        Event eventdata = new Event();
//        eventdata.setEventid(2345);
//        eventdata.setCreatedon(new Date().toString());
//        List<Event> eventList = Arrays.asList(event, eventdata);
//        Optional<Sport> sport = Optional.of(addSport);
//        Mockito.when(sportRepository.findBySportid(4)).thenReturn(sport);
//        ResponseEntity<?> response = eventController.activeEvents(deDTO);
//        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
//        //2
//        Mockito.when(sportRepository.findBySportid(deDTO.getSportid())).thenReturn(sport);
//        Mockito.when(eventRepository.findBySportidAndIsactive(deDTO.getSportid(),true)).thenReturn(Collections.emptyList());
//        ResponseEntity<?> res = eventController.activeEvents(deDTO);
//        assertEquals(HttpStatus.BAD_REQUEST,res.getStatusCode());
//        //3
//        Mockito.when(sportRepository.findBySportid(deDTO.getSportid())).thenReturn(sport);
////        Mockito.when(eventRepository.findBySportidAndIsactive(deDTO.getSportid(),true)).thenReturn(eventList);
//        ResponseEntity<?> resp = eventController.activeEvents(deDTO);
//        assertEquals(HttpStatus.OK,resp.getStatusCode());
//
//
//    }
//
//    @Test
//    void allreadybetlock() {
//        Event event = new Event();
//        event.setEventid(234);
//        event.setCreatedon(new Date());
//        Event eventdata = new Event();
//        eventdata.setEventid(2345);
//        eventdata.setCreatedon(new Date());
//        List<Event> eventList = Arrays.asList(event, eventdata);
//        Mockito.when(eventRepository.findByBetlock(true)).thenReturn(eventList);
//        ResponseEntity<?> response = eventController.allreadybetlock();
//        assertEquals(HttpStatus.OK,response.getStatusCode());
//
//    }
//
//    @Test
//    void allreadyfancylock() {
//        Event event = new Event();
//        event.setEventid(234);
//        event.setCreatedon(new Date());
//        Event eventdata = new Event();
//        eventdata.setEventid(2345);
//        eventdata.setCreatedon(new Date());
//        List<Event> eventList = Arrays.asList(event, eventdata);
//        Mockito.when(eventRepository.findByFancylock(true)).thenReturn(Collections.emptyList());
//        ResponseEntity<?> response = eventController.allreadyfancylock();
//        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
//        //2
//        Mockito.when(eventRepository.findByFancylock(true)).thenReturn(eventList);
//        ResponseEntity<?> res = eventController.allreadyfancylock();
//        assertEquals(HttpStatus.OK,res.getStatusCode());
//
//
//    }
//}