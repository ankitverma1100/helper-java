package helper.helperapi.services;

import helper.helperapi.controller.SportsController;
import helper.helperapi.dto.SportDTO;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.models.Sport;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class SportsServiceTest {
//    @Mock
//    SportRepository sportRepository;
//
//    @InjectMocks
//    SportsService sportsService;
//
//    @Mock
//    private SportsController sportsController;
//
//    @Test
//    void findAndDeleteBySportId() throws ResourceNotFoundException {
//        int sportId = 1;
//        Sport addsport  = new Sport();
//        Optional<Sport> getSport = Optional.of(addsport);
//        Mockito.when(sportRepository.findBySportid(sportId)).thenReturn(Optional.empty());
//        ResponseEntity<?> response = sportsService.findAndDeleteBySportId(sportId);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        //2
//        when(sportRepository.findBySportid(sportId)).thenReturn(getSport);
//        ResponseEntity<?> responsemessage = sportsService.findAndDeleteBySportId(sportId);
//        assertEquals(HttpStatus.OK, responsemessage.getStatusCode());
//    }
//
//    @Test
//    void findSportsByTypeAndStatus() throws ResourceNotFoundException {
//        Sport sport1 = new Sport();
//        sport1.setSportid(1);
//        sport1.setName("Tennis");
//        Sport sport2 = new Sport();
//        sport2.setName("Football");
//        List<Sport> sports = Arrays.asList(sport1, sport2);
//        String type = "SomeType";
//        boolean status = true;
//        try{
//            sportsService.findSportsByTypeAndStatus(type,status);
//        }catch (ResourceNotFoundException e){
//            assertSame(e.getMessage(),"type not betfair please contact admin");
//        }
//        Mockito.when(sportRepository.findByTypeAndStatus(type, status)).thenReturn(sports);
//        sportsService.findSportsByTypeAndStatus(type, status);
//    }
//
//    @Test
//    void addSport() throws ResourceNotFoundException {
//        SportDTO sport = new SportDTO();
//        sport.setSportid(4);
//        sport.setName("Test Sport");
//        //Test ads sport already exists
//        Sport addsport  = new Sport();
//        Optional<Sport> getSport = Optional.of(addsport);
//        Mockito.when(sportRepository.findBySportid(sport.getSportid())).thenReturn(getSport);
//        ResponseEntity<?> response = sportsService.addSport(sport);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        //Test add sport success
//        Mockito.when(sportRepository.findBySportid(sport.getSportid())).thenReturn(Optional.empty());
//        ResponseEntity<?> responsemessage = sportsService.addSport(sport);
//        assertEquals(HttpStatus.OK, responsemessage.getStatusCode());
//
//    }
}