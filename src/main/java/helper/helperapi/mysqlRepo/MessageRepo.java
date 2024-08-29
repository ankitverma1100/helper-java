package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.MessageMarketWise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepo extends JpaRepository<MessageMarketWise, Integer> {

    Optional<MessageMarketWise> findById (String id);
}
