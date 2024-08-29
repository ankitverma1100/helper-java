package helper.helperapi.services;

import helper.helperapi.dto.SportDTO;
import helper.helperapi.dto.SportsTabResponse;
import helper.helperapi.entity.Sports;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.models.Sport;
import helper.helperapi.mysqlRepo.SportsRepo;
import helper.helperapi.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SportsService {
    @Autowired
    SportsRepo sportRepository;

    public ResponseEntity<?> findAndDeleteBySportId (int sportid)throws  ResourceNotFoundException{
        Optional<Sports> foundSport = sportRepository.findBySportid(sportid);
        if(!foundSport.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "sport allready  removed"
            ));
        }
        sportRepository.deleteBySportid(sportid);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Sport  remove successfully"
        ));

    }

    public Map<String,Object> findSportsByTypeAndStatus(String type, boolean status) throws  ResourceNotFoundException{
        List<Sports> sports = sportRepository.findByTypeAndStatus(type,status);
        Map<String,Object> map = new HashMap<>();
        if(sports.isEmpty()){
            throw  new ResourceNotFoundException("type not betfair please contact admin");
        }else{
            map.put("data",sports);
            map.put("message","fetch t_sports successfully");
            return map;
        }
    }


    public Map<String,Object> findSportsByTypeAndStatusForTab(String type, boolean status) throws  ResourceNotFoundException{
        List<Sports> sports = sportRepository.findByTypeAndStatus(type,status);
        Map<String,Object> map = new HashMap<>();
        if(sports.isEmpty()){
            throw  new ResourceNotFoundException("type not betfair please contact admin");
        }else{
            List<SportsTabResponse> responses = sports.stream().map(data -> new SportsTabResponse(data.getName(),data.getSportid())).toList();
            map.put("data",responses);
            map.put("message","fetch t_sports successfully");
            return map;
        }
    }

    public ResponseEntity<?> addSport (@RequestBody SportDTO sport) throws ResourceNotFoundException {
        Optional<Sports> sport1 = sportRepository.findBySportid(sport.getSportid());
        if (sport1.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "This sport id  already exists","status",false
            ));
        } else {
            Sports sport2 = new Sports();
            sport2.setSportid(sport.getSportid());
            sport2.setName(sport.getName());
            sport2.setAdminid("1");
            sport2.setMarketCount(""+0);
            sport2.setIsmysqlupdated(Boolean.valueOf("0"));
            sport2.setStatus(true);
            sport2.setType("betfair");
            sport2.setIsNew(true);
            sport2.setBetlock("0");
            sport2.setCreatedOn(new Date().toString());
            sport2.setUpdatedOn(new Date().toString());
            sportRepository.save(sport2);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Sport  add successfully","status",true
            ));
        }

    }
    }
