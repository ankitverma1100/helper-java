package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.Sports;
import helper.helperapi.models.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportsRepo extends JpaRepository<Sports,Integer> {

    Optional<Sports> findBySportid(int sportid);
    void deleteBySportid(int sportid);

    List<Sports> findByTypeAndStatus(String type, boolean status);

}
