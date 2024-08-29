package helper.helperapi.services;

import helper.helperapi.dto.MatchResultDTO;
import helper.helperapi.models.T_fancy_rollback_detail;
import helper.helperapi.models.T_market_for_rollback;
import helper.helperapi.models.User;
import helper.helperapi.repository.T_fancy_rollback_detailRepository;
import helper.helperapi.repository.T_market_for_rollbackRepository;
import helper.helperapi.sqlModels.Fancy_rollback_detail;
import helper.helperapi.sqlModels.Market_for_rollback;
import helper.helperapi.mysqlRepo.Fancy_rollback_detailRepository;
import helper.helperapi.mysqlRepo.Market_for_rollbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class RollbackService {
    @Autowired
    T_market_for_rollbackRepository tMarketForRollbackRepository;
    @Autowired
    T_fancy_rollback_detailRepository tFancyRollbackDetailRepository;
    //SQL
    @Autowired
    Fancy_rollback_detailRepository fancyRollbackDetailRepository;

    @Autowired
    Market_for_rollbackRepository market_for_rollbackRepository ;

    public ResponseEntity<?> marketrollback (@RequestBody MatchResultDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
        }
        User user = (User) authentication.getPrincipal();
        Optional<Market_for_rollback> fetchT_market1 =market_for_rollbackRepository.findByMarketid(dto.getMarketid());
        if (!fetchT_market1.isPresent()){
            Market_for_rollback rollback = new Market_for_rollback();
            rollback.setMarketid(dto.getMarketid());
            market_for_rollbackRepository.save(rollback);
        }
        Optional<T_market_for_rollback> fetchT_market2 = tMarketForRollbackRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market2.isPresent()){
            T_market_for_rollback rollback1 = new T_market_for_rollback();
            rollback1.setMarketid(dto.getMarketid());
            tMarketForRollbackRepository.save(rollback1);
        }
        Optional<Fancy_rollback_detail> fetchT_market3 = fancyRollbackDetailRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market3.isPresent()){
            Fancy_rollback_detail rollbackdetails = new Fancy_rollback_detail();
            rollbackdetails.setMarketid(dto.getMarketid());
            rollbackdetails.setDonby(user.getUserId());
            rollbackdetails.setCreatedon(new Date());
            fancyRollbackDetailRepository.save(rollbackdetails);
        }
        Optional<T_fancy_rollback_detail>fetchT_market4 = tFancyRollbackDetailRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market4.isPresent()){
            T_fancy_rollback_detail  fetchT_market = new T_fancy_rollback_detail();
            fetchT_market.setMarketid(dto.getMarketid());
            fetchT_market.setDonby(user.getUserId());
            fetchT_market.setCreatedon(new Date());
            tFancyRollbackDetailRepository.save(fetchT_market);
        }
        return ResponseEntity.ok()
                .body(Map.of("message", "rollback success successfully","status",true));
    }
    public ResponseEntity<?> fancyrollback (@RequestBody MatchResultDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Not authenticated"));
        }
        User user = (User) authentication.getPrincipal();
        Optional<Market_for_rollback> fetchT_market1 = market_for_rollbackRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market1.isPresent()){
            Market_for_rollback rollback = new Market_for_rollback();
            rollback.setMarketid(dto.getMarketid());
            rollback.setMarkettype(dto.getMarkettype());
            market_for_rollbackRepository.save(rollback);
        }
        Optional<T_market_for_rollback> fetchT_market2 = tMarketForRollbackRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market2.isPresent()){
            T_market_for_rollback rollback1 = new T_market_for_rollback();
            rollback1.setMarketid(dto.getMarketid());
            rollback1.setMarkettype(dto.getMarkettype());
            tMarketForRollbackRepository.save(rollback1);
        }
        Optional<Fancy_rollback_detail> fetchT_market3 =fancyRollbackDetailRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market3.isPresent()){
            Fancy_rollback_detail rollbackdetails = new Fancy_rollback_detail();
            rollbackdetails.setMarketid(dto.getMarketid());
            rollbackdetails.setDonby(user.getUserId());
            rollbackdetails.setCreatedon(new Date());
            fancyRollbackDetailRepository.save(rollbackdetails);
        }
        Optional<T_fancy_rollback_detail> fetchT_market4 = tFancyRollbackDetailRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market4.isPresent()){
            T_fancy_rollback_detail rollbackdetails1 = new T_fancy_rollback_detail();
            rollbackdetails1.setMarketid(dto.getMarketid());
            rollbackdetails1.setDonby(user.getUserId());
            rollbackdetails1.setCreatedon(new Date());
            tFancyRollbackDetailRepository.save(rollbackdetails1);
        }
        return ResponseEntity.ok()
                .body(Map.of("message", "Fancy  rollback success successfully","status",true));
    }
    public ResponseEntity<?> tieabndRollback (@RequestBody MatchResultDTO dto){
        Optional<Market_for_rollback> fetchT_market1 = market_for_rollbackRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market1.isPresent()){
            String status = "ABANDONED_ROLLBACK";
            Market_for_rollback rollback = new Market_for_rollback();
            rollback.setMarketid(dto.getMarketid());
            rollback.setStatus(status);
            market_for_rollbackRepository.save(rollback);
        }
        Optional<T_market_for_rollback> fetchT_market2 = tMarketForRollbackRepository.findByMarketid(dto.getMarketid());
        if(!fetchT_market2.isPresent()){
            String status = "ABANDONED_ROLLBACK";
            T_market_for_rollback rollback = new T_market_for_rollback();
            rollback.setMarketid(dto.getMarketid());
            rollback.setStatus(status);
            tMarketForRollbackRepository.save(rollback);
        }

        return ResponseEntity.ok()
                .body(Map.of("message", "tieabnd rollback success successfully","status",true));
    }

}
