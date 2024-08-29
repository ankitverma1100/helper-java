package helper.helperapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import helper.helperapi.dto.AddMarketBetLockRes;
import helper.helperapi.dto.AddMarketDTO;
import helper.helperapi.entity.Market;
import helper.helperapi.entity.Sports;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.addMarketRespone.BookmakerResponse;
import helper.helperapi.modelResponse.addMarketRespone.MarketData;
import helper.helperapi.modelResponse.addMarketRespone.MarketDataClass;
import helper.helperapi.modelResponse.addMarketRespone.TiedMatchRunner;
import helper.helperapi.models.Selection;
import helper.helperapi.models.Sport;
import helper.helperapi.mysqlRepo.MarketRepo;
import helper.helperapi.mysqlRepo.SportsRepo;
import helper.helperapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarketService {
    @Value("${url_Cricket}")
    private String url;

    @Value("${url_Other}")
    private String other_Url;

     @Autowired
     MarketRepo marketRepository ;
    @Autowired
    SportsRepo sportRepository ;

    @Autowired
    SelectionRepository  selectionRepository ;
    SimpleDateFormat dateFormatForDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

    public ResponseEntity<Object> addMarketBetlock (@RequestBody @Valid AddMarketBetLockRes dto){
        if (dto.getMarketname() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","marketName must not be null","status",false));
        }
        if(dto.getMatchid()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Match id must not be null","status",false));
        }
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity("http://api.247365.exchange/admin-new-apis/bets/add-remove-market-bet-lock-token-free", dto, Object.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Successfully added","status",true));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Wrong");
        }
    }

    public Object getEvents(@RequestBody  AddMarketDTO addMarketDTO) throws ResourceNotFoundException, JsonProcessingException {
        List<Sports> fetchT_sports = sportRepository.findByTypeAndStatus("betfair", true);
        if (fetchT_sports.isEmpty()) {
            throw new ResourceNotFoundException("Type not betfair. Please contact admin.");
        }
        Optional<Sports> checkSportId = sportRepository.findBySportid(addMarketDTO.getSportid());
        if (!checkSportId.isPresent()) {
            throw new ResourceNotFoundException("Please provide a valid sportId.");
        }
        Optional<Market> checkMarketId = marketRepository.findByMarketid(addMarketDTO.getMarketid());
        if (checkMarketId.isPresent()) {
            throw new ResourceNotFoundException("Market already added");
        }
        RestTemplate restTemplate = new RestTemplate();
        MarketData data = restTemplate.getForObject(url, MarketData.class);
        MarketData otherData = restTemplate.getForObject(other_Url,MarketData.class);
        if(addMarketDTO.getSportid()==4) {
            List<MarketDataClass> result = data.getGameList().get(0).getEventList().stream().filter(marketDataClass -> {
                return marketDataClass.getEventData().getEventId() == addMarketDTO.getEventid();
            }).toList();

            if (result.isEmpty()) {
                throw new ResourceNotFoundException("Event id not match ");
            }

            List<BookmakerResponse> res = result.get(0).getMarketList().getBookmaker().stream().filter(bookmakerResponse -> {
                return bookmakerResponse.getMarketId().equals(addMarketDTO.getMarketid());
            }).toList();
            if (!res.isEmpty()) {
                Market market = new Market();
                market.setMarketid(res.get(0).getMarketId());
                market.setBetDelay(res.get(0).getBet_delay());
                market.setCreatedon(new Date().toString());
                market.setUpdatedOn(new Timestamp(new Date().getTime()));
                market.setEventid((int) res.get(0).getEid());
                market.setMarketname(res.get(0).getTitle());
                market.setMatchname(result.get(0).getEventData().getName());
                market.setSportid(res.get(0).getSid());
                System.out.println(dateFormatForDB.format(new Date()));
                market.setStartdate(dateFormatForDB.format(new Date()));
                try {
                    market.setOpendate(dateFormatForDB.parse(new Date().toString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                market.setMaxbet(addMarketDTO.getMaxbet());
                market.setMinbet(addMarketDTO.getMinbet());
//                market.setMaxLiabilityPerMarket(addMarketDTO.getMaxLiabilityPerMarket());
//                market.setMax_bet_rate(addMarketDTO.getMax_bet_rate());
//                market.setMin_bet_rate(addMarketDTO.getMin_bet_rate());
                Gson gson = new Gson();
                TiedMatchRunner[] tiedMatchRunners = gson.fromJson(res.get(0).getRunners(), TiedMatchRunner[].class);
                for (TiedMatchRunner tiedMatchRunner : tiedMatchRunners) {
                    Selection selection = new Selection();
                    selection.setSelectionid(Integer.parseInt(tiedMatchRunner.getSecId()));
                    selection.setMarketid(tiedMatchRunner.getMarketId());
                    selection.setRunner_name(tiedMatchRunner.getRunner());
                    selection.setCreatedon(new Date());
                    selectionRepository.save(selection);
                }
                marketRepository.save(market);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add market data  successfully","status",true
                ));
            } else if (result.get(0).getMarketList().getMatch_odd().getMarketId().equals(addMarketDTO.getMarketid())) {
                Market market = new Market();

                market.setMarketid(result.get(0).getMarketList().getMatch_odd().getMarketId());
                market.setBetDelay(result.get(0).getMarketList().getMatch_odd().getBet_delay());
                market.setCreatedon(new Date().toString());
                market.setUpdatedOn(new Timestamp(new Date().getTime()));
                market.setEventid((int) result.get(0).getMarketList().getMatch_odd().getEid());
                market.setMarketname(result.get(0).getMarketList().getMatch_odd().getTitle());
                market.setMatchname(result.get(0).getEventData().getName());
                market.setSportid(result.get(0).getMarketList().getMatch_odd().getSid());
                market.setStartdate(dateFormatForDB.format(new Date()));
                try {
                    market.setOpendate(dateFormatForDB.parse(new Date().toString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                market.setMaxbet(addMarketDTO.getMaxbet());
                market.setMinbet(addMarketDTO.getMinbet());
//                market.setMaxLiabilityPerMarket(addMarketDTO.getMaxLiabilityPerMarket());
                market.setMaxBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMax_bet_rate())));
                market.setMinBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMin_bet_rate())));
                Gson gson = new Gson();
                TiedMatchRunner[] tiedMatchRunners = gson.fromJson(result.get(0).getMarketList().getMatch_odd().getRunners(), TiedMatchRunner[].class);
                for (TiedMatchRunner tiedMatchRunner : tiedMatchRunners) {
                    Selection selection = new Selection();
                    selection.setSelectionid(Integer.parseInt(tiedMatchRunner.getSecId()));
                    selection.setMarketid(addMarketDTO.getMarketid());
                    selection.setRunner_name(tiedMatchRunner.getRunner());
                    selection.setCreatedon(new Date());
                    selectionRepository.save(selection);
                }
                marketRepository.save(market);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add market data  successfully","status",true
                ));
            } else if (result.get(0).getMarketList().getCompleted_match().getMarketId().equals(addMarketDTO.getMarketid())) {
                Market market = new Market();
                market.setMarketid(result.get(0).getMarketList().getCompleted_match().getMarketId());
                market.setBetDelay(result.get(0).getMarketList().getCompleted_match().getBet_delay());
                market.setCreatedon(new Date().toString());
                market.setUpdatedOn(new Timestamp(new Date().getTime()));
                market.setEventid((int) result.get(0).getMarketList().getCompleted_match().getEid());
                market.setMarketname(result.get(0).getMarketList().getCompleted_match().getTitle());
                market.setMatchname(result.get(0).getEventData().getName());
                market.setSportid(result.get(0).getMarketList().getCompleted_match().getSid());
                market.setStartdate(dateFormatForDB.format(new Date()));
                try {
                    market.setOpendate(dateFormatForDB.parse(new Date().toString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                market.setMaxbet(addMarketDTO.getMaxbet());
                market.setMinbet(addMarketDTO.getMinbet());
//                market.setMaxLiabilityPerMarket(addMarketDTO.getMaxLiabilityPerMarket());
                market.setMaxBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMax_bet_rate())));
                market.setMinBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMin_bet_rate())));
                Gson gson = new Gson();
                TiedMatchRunner[] tiedMatchRunners = gson.fromJson(result.get(0).getMarketList().getCompleted_match().getRunners(), TiedMatchRunner[].class);
                for (TiedMatchRunner tiedMatchRunner : tiedMatchRunners) {
                    Selection selection = new Selection();
                    selection.setSelectionid(Integer.parseInt(tiedMatchRunner.getSecId()));
                    selection.setMarketid(addMarketDTO.getMarketid());
                    selection.setRunner_name(tiedMatchRunner.getRunner());
                    selection.setCreatedon(new Date());
                    selectionRepository.save(selection);
                }
                marketRepository.save(market);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add market data  successfully","status",true
                ));
            } else if (result.get(0).getMarketList().getTied_match().getMarketId().equals(addMarketDTO.getMarketid())) {
                Market market = new Market();
                market.setMarketid(result.get(0).getMarketList().getTied_match().getMarketId());
                market.setBetDelay(result.get(0).getMarketList().getTied_match().getBet_delay());
                market.setCreatedon(new Date().toString());
                market.setUpdatedOn(new Timestamp(new Date().getTime()));
                market.setEventid((int) result.get(0).getMarketList().getTied_match().getEid());
                market.setMarketname(result.get(0).getMarketList().getTied_match().getTitle());
                market.setMatchname(result.get(0).getEventData().getName());
                market.setSportid(result.get(0).getMarketList().getTied_match().getSid());
                market.setStartdate(dateFormatForDB.format(new Date()));
                try {
                    market.setOpendate(dateFormatForDB.parse(new Date().toString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                market.setMaxbet(addMarketDTO.getMaxbet());
                market.setMinbet(addMarketDTO.getMinbet());
//                market.setMaxLiabilityPerMarket(addMarketDTO.getMaxLiabilityPerMarket());
                market.setMaxBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMax_bet_rate())));
                market.setMinBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMin_bet_rate())));
                Gson gson = new Gson();
                TiedMatchRunner[] tiedMatchRunners = gson.fromJson(result.get(0).getMarketList().getTied_match().getRunners(), TiedMatchRunner[].class);
                for (TiedMatchRunner tiedMatchRunner : tiedMatchRunners) {
                    Selection selection = new Selection();
                    selection.setSelectionid(Integer.parseInt(tiedMatchRunner.getSecId()));
                    selection.setMarketid(addMarketDTO.getMarketid());
                    selection.setRunner_name(tiedMatchRunner.getRunner());
                    selection.setCreatedon(new Date());
                    selectionRepository.save(selection);
                }
                marketRepository.save(market);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Add market data  successfully","status",true
                ));
            }

        }
        List<MarketDataClass> result1 = otherData.getGameList().stream()
                .flatMap(gameData -> gameData.getEventList().stream())
                .filter(marketDataClass -> marketDataClass.getEventData().getEventId() == addMarketDTO.getEventid())
                .toList();
        if(result1.get(0).getMarketList().getMatch_odd().getMarketId().equals(addMarketDTO.getMarketid())){
            Market market = new Market();
            market.setMarketid(result1.get(0).getMarketList().getMatch_odd().getMarketId());
            market.setBetDelay(result1.get(0).getMarketList().getMatch_odd().getBet_delay());
            market.setCreatedon(new Date().toString());
            market.setUpdatedOn(new Timestamp(new Date().getTime()));
            market.setEventid((int) result1.get(0).getMarketList().getMatch_odd().getEid());
            market.setMarketname(result1.get(0).getMarketList().getMatch_odd().getTitle());
            market.setMatchname(result1.get(0).getEventData().getName());
            market.setSportid(result1.get(0).getMarketList().getMatch_odd().getSid());
            market.setStartdate(dateFormatForDB.format(new Date()));try {
                market.setOpendate(dateFormatForDB.parse(new Date().toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            market.setMaxbet(addMarketDTO.getMaxbet());
            market.setMinbet(addMarketDTO.getMinbet());
//            market.setMaxLiabilityPerMarket(addMarketDTO.getMaxLiabilityPerMarket());
            market.setMaxBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMax_bet_rate())));
            market.setMinBetRate(Double.parseDouble(String.valueOf(addMarketDTO.getMin_bet_rate())));
            Gson gson = new Gson();
            TiedMatchRunner[] tiedMatchRunners = gson.fromJson(result1.get(0).getMarketList().getMatch_odd().getRunners(), TiedMatchRunner[].class);
            for (TiedMatchRunner tiedMatchRunner : tiedMatchRunners) {
                Selection selection = new Selection();
                selection.setSelectionid(Integer.parseInt(tiedMatchRunner.getSecId()));
                selection.setMarketid(addMarketDTO.getMarketid());
                selection.setRunner_name(tiedMatchRunner.getRunner());
                selection.setCreatedon(new Date());
                selectionRepository.save(selection);
            }
            marketRepository.save(market);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "Add market data  successfully","status",true
            ));
        }
        throw new ResourceNotFoundException("Market id not match ");
    }
}
