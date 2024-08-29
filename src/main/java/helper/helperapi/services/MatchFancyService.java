package helper.helperapi.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import helper.helperapi.dto.EventDeDTO;
import helper.helperapi.dto.UpdateAllFancyDTO;
import helper.helperapi.dto.UpdateFancyDTO;
import helper.helperapi.entity.Event;
import helper.helperapi.entity.MatchFancy;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.GetAllUpdateFancyRes;
import helper.helperapi.modelResponse.GetFancyResponse.FancyData;
import helper.helperapi.modelResponse.GetFancyResponse.FancyDataClass;
import helper.helperapi.modelResponse.GetFancyResponse.SessionRes;
import helper.helperapi.modelResponse.Get_all_ready_fancyRes;
import helper.helperapi.modelResponse.UpdateOneFancyRes;
import helper.helperapi.mysqlRepo.EventRepo;
import helper.helperapi.mysqlRepo.MatchFancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MatchFancyService {
    @Value("${url_Cricket}")
    private String url;
    @Autowired
    MatchFancyRepo matchFancyRepository ;



    @Autowired
    EventRepo eventRepository ;


    public ResponseEntity<Object> getFancy(@RequestBody EventDeDTO eventDeDTO) {
        System.out.println(eventDeDTO.getEventid()+" this is event id");
        Optional<Event> event = eventRepository.findByEventid(eventDeDTO.getEventid());
        if (!event.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Event id not match","status",false));
        }

        RestTemplate restTemplate = new RestTemplate();

        FancyData data = restTemplate.getForObject(url, FancyData.class);

        List<FancyDataClass> result = data.getGameList().get(0).getEventList().stream()
                .filter(fancyDataClass -> fancyDataClass.getEventData().getEventId() == eventDeDTO.getEventid())
                .collect(Collectors.toList());



        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "No data","status",true));
        }
        // Create a new data structure to match the desired JSON structure
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "Get All fancy successfully");
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (FancyDataClass fancyDataClass : result) {

            Map<String, Object> eventDataMap = new HashMap<>();
            Map<String ,Object> eventData =  new HashMap<>();
            eventData.put("eventname",fancyDataClass.getEventData().getName());
            eventDataMap.put("eventname", eventData);
            // Add other_market, session, and odd_even lists to the eventDataMap
//            eventDataMap.put("fancymarket3", fancyDataClass.getMarketList().getOther_market());
            List<SessionRes> sessionRes =  new ArrayList<>();
            for (SessionRes sessionRes1:fancyDataClass.getMarketList().getSession()
                 ) {

                if(sessionRes1.getStatus() == 1){
                    sessionRes.add(sessionRes1);
                }
            }
            eventDataMap.put("fancymarket2", sessionRes);
//            eventDataMap.put("odd_even", fancyDataClass.getMarketList().getOdd_even());

            dataList.add(eventDataMap);
        }

        responseData.put("data", dataList);

        return ResponseEntity.ok(responseData);
    }



    public HashMap<String, Object> update_all_fancy (@RequestBody UpdateAllFancyDTO dto) {
        List<MatchFancy> updateFancy = matchFancyRepository.findByEventidAndOddstype(dto.getEventid(), dto.getOddstype());
        for (MatchFancy matchFancy : updateFancy) {
            if (dto.getMaxbet() != 0) {
                matchFancy.setMaxBet(dto.getMaxbet());
            }
            if (dto.getMinbet() != 0) {
                matchFancy.setMinBet(dto.getMinbet());
            }
            if(dto.getBetdelay()!=0){
                matchFancy.setBetDelay(dto.getBetdelay());
            }
        }
        matchFancyRepository.saveAll(updateFancy);
        List<MatchFancy> updateFancy1 = matchFancyRepository.findByEventidAndOddstype(dto.getEventid(), dto.getOddstype());
        HashMap<String, Object> res = new HashMap<>();
        res.put("data", updateFancy1.stream().map(data -> new GetAllUpdateFancyRes(data.getEventid(), data.getFancyid(), data.getOddstype(), data.getMinBet(), data.getMaxBet(), data.getBetDelay())).toList());
        return res;
    }


    public HashMap<String, Object> update_One_fancy(@RequestBody UpdateFancyDTO dto) throws ResourceNotFoundException {
        Optional<MatchFancy> matchFancy = matchFancyRepository.findByEventidAndFancyid(dto.getEventid(),dto.getFancyid());
        if(!matchFancy.isPresent()){
            throw new ResourceNotFoundException("please provide a valid eventId and fancy Id");
        }
        MatchFancy updatedFancy = matchFancy.get();
        updatedFancy.setMaxBet(dto.getMaxbet());
        updatedFancy.setBetDelay(dto.getBetdelay());
        updatedFancy.setMinBet(dto.getMinbet());
        matchFancyRepository.save(updatedFancy);
        HashMap<String ,Object> res = new HashMap<>();
        res.put("data", new UpdateOneFancyRes(dto.getFancyid(), dto.getEventid(), dto.getBetdelay(), dto.getMinbet(), dto.getMaxbet()));
        return res;
    }



    public HashMap<String, Object> get_all_ready_fancy ()throws ResourceNotFoundException{
        List<MatchFancy>  getFancy  = matchFancyRepository.findAll();
        if(getFancy.isEmpty()){
            throw new ResourceNotFoundException("No any match fancy data");
        }
        HashMap<String,Object> res = new HashMap<>();
        res.put("data",getFancy.stream().map(data -> new Get_all_ready_fancyRes(data.getFancyid())).toList());
        return res ;
    }


}
