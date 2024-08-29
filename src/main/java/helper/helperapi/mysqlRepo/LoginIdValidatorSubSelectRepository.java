package helper.helperapi.mysqlRepo;

import helper.helperapi.entity.LoginViewFromEXUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginIdValidatorSubSelectRepository extends JpaRepository<LoginViewFromEXUser,Integer> {
    public LoginViewFromEXUser findByUserid(String userid);
}
