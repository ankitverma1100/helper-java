package helper.helperapi.services;

import helper.helperapi.entity.LoginViewFromEXUser;
import helper.helperapi.exception.ErrorMessageCustomException;
import helper.helperapi.mysqlRepo.LoginIdValidatorSubSelectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    LoginIdValidatorSubSelectRepository userDb;

    @Override
    public LoginViewFromEXUser loadUserByUsername(String username) {
        LoginViewFromEXUser user= userDb.findByUserid(username);
        if(null == user) {
            throw new ErrorMessageCustomException("Invalid Username or password", false);
        }
        return user;
    }
}
