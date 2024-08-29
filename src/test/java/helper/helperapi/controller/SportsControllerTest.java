package helper.helperapi.controller;

import helper.helperapi.dto.SportDTO;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.AllReadRes;
import helper.helperapi.modelResponse.sportResponse.SportClass;
import helper.helperapi.models.Sport;
import helper.helperapi.repository.SportRepository;
import helper.helperapi.services.SportsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class SportsControllerTest {
//    @Mock
//    SportRepository sportRepository;
//
//    @Mock
//    SportsService sportsService;
//
//    @Mock
//    private RestTemplate restTemplate;
//    @InjectMocks
//    private SportsController sportsController;
//    private Sport sportData1;
//
//    @Test
//    void addSport() throws ResourceNotFoundException {
//        SportDTO sport = new SportDTO();
//        sport.setSportid(4);
//        Sport addsport  = new Sport();
//        Optional<Sport> getSport = Optional.of(addsport);
//        //Test ads sport already exists
//        when(sportRepository.findBySportid(sport.getSportid())).thenReturn(getSport);
//        ResponseEntity<?> response = sportsController.addSport(sport);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        //Test add sport success
//        SportDTO sportDTO = new SportDTO();
//        sportDTO.setSportid(1);
//        when(sportRepository.findBySportid(1)).thenReturn(Optional.empty());
//        ResponseEntity<?> responseEntity = sportsController.addSport(sportDTO);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void findAndDeleteBySport() throws ResourceNotFoundException {
//        int sportId = 1;
//        ResponseEntity<?> responseEntity = sportsController.findAndDeleteBySport(sportId);
//        Mockito.verify(sportsService).findAndDeleteBySportId(sportId);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void getSports(){}
//
//
//    @Test
//    void getTSports() throws ResourceNotFoundException {
//        // Create sample data
//        Sport sport1 = new Sport();
//        sport1.setSportid(1);
//        sport1.setName("Tennis");
//        Sport sport2 = new Sport();
//        sport2.setName("Football");
//        List<Sport> sports = Arrays.asList(sport1, sport2);
//        List<Sport> sportList = sportsController.getTSports();
//        Mockito.when(sportsService.findSportsByTypeAndStatus("betfair", true)).thenReturn(sports);
//
//    }
//
//    @Test
//    void getAllSports() throws ResourceNotFoundException {
//        //1
//        Sport sport1 = new Sport();
//        sport1.setSportid(1);
//        //2
//        Sport sport2 = new Sport();
//        sport2.setSportid(2);
//        List<Sport> sports = Arrays.asList(sport1, sport2);
//        when(sportRepository.findAll()).thenReturn(sports);
//        List<AllReadRes> sportList = sportsController.getAllSports();
//        //3
//        when(sportRepository.findAll()).thenReturn(Collections.emptyList());
//
//    }
}