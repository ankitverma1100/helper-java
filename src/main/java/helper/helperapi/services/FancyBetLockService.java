package helper.helperapi.services;

import helper.helperapi.dto.MarketBetLockDTO;
import helper.helperapi.entity.MarketBetLock;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.models.Sport;
import helper.helperapi.models.User;
import helper.helperapi.mysqlRepo.MarketBetLockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FancyBetLockService {

//    working both
    @Autowired
    MarketBetLockRepo marketBetLockRepository;

    public ResponseEntity<Object> addFancyBetlock (MarketBetLockDTO dto){
        if (dto.getMarketName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","marketName must not be null","status",false));
        }
        if(dto.getMatchId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Match id must not be null","status",false));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated","status",false));
        }
        User user = (User) authentication.getPrincipal();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity("http://api.247365.exchange/admin-new-apis/bets/add-remove-market-bet-lock-token-free", dto, Object.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            MarketBetLock marketBetLock = new MarketBetLock();
            marketBetLock.setMarketName(dto.getMarketName());
            marketBetLock.setMatchId(dto.getMatchId());
            marketBetLock.setUserId(user.getUserId());
            marketBetLockRepository.save(marketBetLock);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","add Fancy  Betlock  Successfully","status",true));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }

    public ResponseEntity<?> findAndDeleteBySportId (String marketName ,String matchid)throws ResourceNotFoundException {
        MarketBetLock foundSport =marketBetLockRepository.findByMarketNameAndMatchId(marketName,matchid);
        if(foundSport==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", "sport allready  removed"
            ));
        }
        marketBetLockRepository.deleteByMarketNameAndMatchId(marketName,matchid);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Sport  remove successfully"
        ));

    }
}
