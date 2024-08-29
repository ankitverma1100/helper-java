package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.EXUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<EXUser,Integer> {

    Optional<EXUser> findByUserid(String userId);

}
