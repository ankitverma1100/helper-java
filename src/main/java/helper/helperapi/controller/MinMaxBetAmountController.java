package helper.helperapi.controller;

import helper.helperapi.dto.MinMaxBetAmountDTO;
import helper.helperapi.entity.MinMaxBetAmountSql;
import helper.helperapi.models.MinMaxBetAmount;
import helper.helperapi.mysqlRepo.MinMaxBetAmountRepo;
import helper.helperapi.repository.MinMaxBetAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class MinMaxBetAmountController {
    @Autowired
    MinMaxBetAmountRepo minMaxBetAmountRepository ;

    @PostMapping("/addt_minmaxbetamountMarket")
    public ResponseEntity<?> addt_minmaxbetamountMarket(@RequestBody MinMaxBetAmountDTO dto){
        MinMaxBetAmountSql minMaxBetAmount = new MinMaxBetAmountSql() ;
        minMaxBetAmount.setAmount(dto.getAmount());
        minMaxBetAmount.setType(dto.getType());
        minMaxBetAmountRepository.save(minMaxBetAmount);
        return ResponseEntity.ok()
                .body(Map.of("message", "Add min max bet amount  successfully ", "data", minMaxBetAmount));
    }

    @GetMapping("/t_minmaxbetamountMarket")
    public  ResponseEntity<Map<String, Object>>  t_minmaxbetamountMarket (){
        List<MinMaxBetAmountSql> minMaxBetAmounts = minMaxBetAmountRepository.findAll();
        // Group the data by the "type" field
        Map<String, List<MinMaxBetAmountSql>> groupedData = minMaxBetAmounts.stream()
                .collect(Collectors.groupingBy(MinMaxBetAmountSql::getType));

        return ResponseEntity.ok()
                .body(Map.of("message", "Get min max bet amount  successfully ","status",true, "data", groupedData));
    }

    @GetMapping("/t_minmaxbetamountFancy")
    public ResponseEntity<Object> t_minmaxbetamountFancy (){
        List<MinMaxBetAmountSql> tMinMaxBetAmountFancy = minMaxBetAmountRepository
                .findByTypeIn(List.of("maxbet", "minbet", "betdelay"));

        Map<String, List<MinMaxBetAmountSql>> groupedData = tMinMaxBetAmountFancy.stream()
                .collect(Collectors.groupingBy(MinMaxBetAmountSql::getType));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Fetch market rate");
        response.put("status",true);
        response.put("data", groupedData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
