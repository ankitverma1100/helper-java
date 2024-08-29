package helper.helperapi.repository;

import helper.helperapi.models.Message_market;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MessageRepository extends MongoRepository <Message_market ,String> {
  Optional<Message_market> findById (String id);
}
