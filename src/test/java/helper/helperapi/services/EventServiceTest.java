package helper.helperapi.services;

import helper.helperapi.controller.EventController;
import helper.helperapi.dto.AddSportDTO;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.models.Event;
import helper.helperapi.models.Sport;
import helper.helperapi.repository.EventRepository;
import helper.helperapi.repository.MarketRepository;
import helper.helperapi.repository.SportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EventServiceTest {
//    @Mock
//    SportRepository sportRepository;
//
//    @Mock
//    EventRepository eventRepository;
//
//    @Mock
//    MarketRepository marketRepository;
//    @Mock
//    EventController eventController ;
//
//    @InjectMocks
//    EventService eventService;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @Test
//    void t_addTabSports() {
//        AddSportDTO addSportDTO = new AddSportDTO();
//        Sport sport = new Sport();
//        Event event = new Event();
//        addSportDTO.setSportid(5);
//        addSportDTO.setEventid(1234);
//        Sport sport1 = new Sport();
//        sport1.setSportid(1);
//        sport1.setName("Tennis");
//        sport1.setType("betfair");
//        sport1.setStatus(true);
//        Sport sport2 = new Sport();
//        sport2.setName("Football");
//        sport2.setType("betfair");
//        sport2.setStatus(true);
//        List<Sport> sports = Arrays.asList(sport1, sport2);
//        Optional<Sport> optionalSport = Optional.of(sport);
//        Optional<Event> optionalEvent = Optional.of(event);
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(Collections.emptyList());
//        ResponseEntity<?> response =eventService.t_addTabSports(addSportDTO);
//        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
//        //2
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(sports);
//        Mockito.when(sportRepository.findBySportid(addSportDTO.getSportid())).thenReturn(Optional.empty());
//        ResponseEntity<?> resp =eventService.t_addTabSports(addSportDTO);
//        assertEquals(HttpStatus.BAD_REQUEST,resp.getStatusCode());
//        //3
//        Mockito.when(sportRepository.findByTypeAndStatus("betfair", true)).thenReturn(sports);
//        Mockito.when(sportRepository.findBySportid(addSportDTO.getSportid())).thenReturn(optionalSport);
//        Mockito.when(eventRepository.findByEventid(addSportDTO.getEventid())).thenReturn(optionalEvent);
//        ResponseEntity<?> res =eventService.t_addTabSports(addSportDTO);
//        assertEquals(HttpStatus.BAD_REQUEST,res.getStatusCode());
//        //4
//
//
//    }
//
//    @Test
//    void getEventId() {
//        Event event = new Event();
//        event.setEventid(234);
//        //2
//        Event eventdata = new Event();
//        eventdata.setEventid(678);
//        List<Event> eventList = Arrays.asList(event, eventdata);
//        Mockito.when(eventRepository.findAll()).thenReturn(eventList);
//        List<helper.helperapi.entity.Event> result = eventService.getEventId();
//        assertEquals(eventList, result);
//    }
//
//    @Test
//    void removeEvent() throws ResourceNotFoundException {
//        int eventid = 1234;
//        Event addEvent = new Event();
//        Optional<Event> getSport = Optional.of(addEvent);
//        Mockito.when(eventRepository.findByEventid(eventid)).thenReturn(Optional.empty());
//
//        ResponseEntity<?> response = eventService.removeEvent(eventid);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//
//        Mockito.when(eventRepository.findByEventid(eventid)).thenReturn(getSport);
//        Mockito.when(eventRepository.deleteByEventid(eventid)).thenReturn(addEvent);
//        ResponseEntity<?> res = eventService.removeEvent(eventid);
//        assertEquals(HttpStatus.OK, res.getStatusCode());
//    }
}