package helper.helperapi.controller;


import helper.helperapi.dto.SportDTO;
import helper.helperapi.entity.Sports;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.AllReadRes;
import helper.helperapi.modelResponse.sportResponse.GetSportResponse;
import helper.helperapi.modelResponse.sportResponse.SportClass;
import helper.helperapi.models.Sport;
import helper.helperapi.mysqlRepo.SportsRepo;
import helper.helperapi.repository.SportRepository;
import helper.helperapi.services.SportsService;
import helper.helperapi.sqlModels.ObjectCommonResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class SportsController {
    @Autowired
    SportsRepo sportRepository ;
    @Autowired
    SportsService sportsService ;
    @Value("${url_Other}")
    private String apiUrl;
    @CrossOrigin("*")
    @PostMapping("/AddSports")
    public ResponseEntity<?> addSport (@RequestBody SportDTO sport) throws ResourceNotFoundException {
      Optional<Sports> sport1 = sportRepository.findBySportid(sport.getSportid());
      if(sport1.isPresent()){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                  "message", "This sport id  already exists","status",false
          ));
      }
           sportsService.addSport(sport);
          return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                  "message", "Sport  add successfully","status",true
          ));

  }
    @PostMapping("/RemoveSports")
    public ResponseEntity<?> findAndDeleteBySport (@RequestBody Map<String,Object> sportid) throws ResourceNotFoundException {
        if(sportid.get("sportid")==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "Please Provide Sportid"
            ));
        }
        sportsService.findAndDeleteBySportId(Integer.parseInt(sportid.get("sportid").toString()));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Sport  remove successfully"
        ));
    }

    @GetMapping ("/GetSports")
    public Map<String,Object> getSports (){
    RestTemplate restTemplate  = new RestTemplate();
    SportClass data = restTemplate.getForObject(apiUrl, SportClass.class);

    Map<String,Object> map = new HashMap<>();
        List<GetSportResponse> updatedSports = data.getSportList().stream()
                .map(sport -> new GetSportResponse(sport.getSportId(), null,sport.getName()))
                .collect(Collectors.toList());
    map.put("data",updatedSports);

    return  map ;
}


    @GetMapping("/fetchT_sports")
    public Map<String,Object> getTSports() throws ResourceNotFoundException {
        return sportsService.findSportsByTypeAndStatus("betfair", true);
    }

    @GetMapping("/fetchT_sportsTab")
    public Map<String,Object> getTSportsTab() throws ResourceNotFoundException {
        return sportsService.findSportsByTypeAndStatusForTab("betfair", true);
    }


    @GetMapping("/allreadySportsAdded")
    public ResponseEntity<Object> getAllSports()throws  ResourceNotFoundException {

            List<Sports> sports = sportRepository.findAll();
            if(sports.isEmpty()){
                throw new ResourceNotFoundException("Please add sports to continue");
            }
        List<Map<String,Object>> maps = new ArrayList<>();
        ObjectCommonResponseModel objectCommonResponseModel = new ObjectCommonResponseModel();
        objectCommonResponseModel.setData(sports.stream().map(data->new AllReadRes(data.getSportid())).toList());
        objectCommonResponseModel.setMessage("Sport List found");
        return ResponseEntity.ok(objectCommonResponseModel);


    }

}
