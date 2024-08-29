package helper.helperapi.repository;

import helper.helperapi.models.Sport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface SportRepository extends MongoRepository <Sport ,String> {
    Optional<Sport> findBySportid (int sportid);
    void deleteBySportid(int sportid);

    List<Sport> findByTypeAndStatus(String type, boolean status);
}
