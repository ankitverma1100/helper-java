package helper.helperapi.controller;

import helper.helperapi.dto.AddMessageDTO;
import helper.helperapi.dto.UpdateMessageDTO;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.models.Message_market;
import helper.helperapi.repository.MessageRepository;
import helper.helperapi.services.MessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/helper-api")
public class MessageController {
    @Autowired
    MessageRepository messageRepository ;
    @Autowired
    MessageServices messageServices ;
    @PostMapping("/addMessage")
   public ResponseEntity<?> addMessage (@RequestBody AddMessageDTO addMessageDTO) throws  ResourceNotFoundException {
         messageServices.addMessage(addMessageDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "message", "Add message  successfully"
        ));
    }
    @GetMapping("/getMessage")
    public ResponseEntity<?> getMessage () throws  ResourceNotFoundException {
        List<Message_market> messageMarkets = messageRepository.findAll();
        if(messageMarkets.isEmpty()){
            throw  new ResourceNotFoundException("No any message");
        }
        return ResponseEntity.ok()
                .body(Map.of("message", "Get all message successfully","data", messageMarkets));
    }

    @PutMapping("/updateMessage")
    public Message_market updateMessage (@RequestBody UpdateMessageDTO updateMessageDTO) throws  ResourceNotFoundException {
       Optional<Message_market> messageMarket1 = messageRepository.findById(updateMessageDTO.getId());
       if(!messageMarket1.isPresent()){
           throw new ResourceNotFoundException("Message id is not found");
       }
       Message_market todoToUpdate=messageMarket1.get();
       System.out.println(todoToUpdate);
       todoToUpdate.setMessage(updateMessageDTO.getMessage());
       messageRepository.save(todoToUpdate);
       return  todoToUpdate ;
    }
}
