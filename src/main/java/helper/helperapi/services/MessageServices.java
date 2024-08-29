package helper.helperapi.services;

import helper.helperapi.dto.AddMessageDTO;
import helper.helperapi.entity.MessageMarketWise;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.models.Message_market;
import helper.helperapi.mysqlRepo.MessageRepo;
import helper.helperapi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class MessageServices {
    @Autowired
    MessageRepo messageRepository ;
    public ResponseEntity<?> addMessage (@RequestBody AddMessageDTO addMessageDTO) throws ResourceNotFoundException {
        MessageMarketWise messageMarket = new MessageMarketWise();
        messageMarket.setIsDisplayMessage(true);
        messageMarket.setMessage(addMessageDTO.getMessage());
        messageMarket.setMatchId(addMessageDTO.getMatchId());
        messageMarket.setMarketId(addMessageDTO.getMarketId());
        messageMarket.setMarketName(addMessageDTO.getMarket_name());
        messageRepository.save(messageMarket);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Add message  successfully"
        ));
    }
}
