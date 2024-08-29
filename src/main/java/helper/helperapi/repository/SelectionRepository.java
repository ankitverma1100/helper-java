package helper.helperapi.repository;

import helper.helperapi.models.Selection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SelectionRepository  extends MongoRepository <Selection ,String> {
    Optional<Selection> findByMarketid (String marketid);


    List<Selection> findAllByMarketid(String marketid);

    Selection findBySelectionid(String selectionid);
    void  deleteByMarketid  (String marketid);
}
