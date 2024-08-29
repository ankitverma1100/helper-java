package helper.helperapi.services;

import helper.helperapi.entity.EXUser;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.UpdateAccountLockResponse;
import helper.helperapi.modelResponse.UpdatedAccountActiveResponse;
import helper.helperapi.modelResponse.UserUpdatePermissionResponse;
import helper.helperapi.models.User;
import helper.helperapi.mysqlRepo.UserRepo;
import helper.helperapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class UserService  {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepo userRepo;


//        return  userRepo.findByUserid(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("username not found"));


    public User findById(String id)throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user id not found"));
    }
    public  ResponseEntity<?> updatedAccountActive (@RequestBody User user) throws  ResourceNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Not authenticated","status",false));
        }
        User users = (User) authentication.getPrincipal();
        if(users.getUsertype()!=8){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Unauthorized request","status",false));
        }
        User user1 = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found for this userId :: " + user.getUserId()));

        if(user1.getActive()==false) {
            user1.setActive(Boolean.valueOf("true"));
        } else if (user1.getActive()==true) {
            user1.setActive(Boolean.valueOf("false"));
        }
        User data= userRepository.save(user1);
        User user2 = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found for this userId :: " + user.getUserId()));
        UpdatedAccountActiveResponse useracrtive = new UpdatedAccountActiveResponse();
        useracrtive.setId(user2.getId());
        useracrtive.setUserId(user2.getUserId());
        useracrtive.setActive(user2.getActive());
        return ResponseEntity.status(HttpStatus.OK).body( Map.of("message","User password change successfully","status",true,"data",useracrtive));
    }
    public  ResponseEntity<?> updateAccountLock (@RequestBody User user)throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Not authenticated","status",false));
        }
        User users = (User) authentication.getPrincipal();
        if(users.getUsertype()!=8){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Unauthorized request","status",false));
        }
        User user1 = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException(" :: " + user.getUserId()));
        if (user1.getAccountLock() == false){
            user1.setAccountLock(Boolean.valueOf("true"));
        } else if (user1.getAccountLock()==true) {
            user1.setAccountLock(Boolean.valueOf("false"));
        }
        User saveData = userRepository.save(user1);
        User user2 = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found for this userId :: " + user.getUserId()));
        UpdateAccountLockResponse upadteaccount = new UpdateAccountLockResponse();
        upadteaccount.setId(user2.getId());
        upadteaccount.setUserId(user2.getUserId());
        upadteaccount.setAccountLock(user2.getAccountLock());
        return ResponseEntity.status(HttpStatus.OK).body( Map.of("message","User password change successfully","status",true,"data",upadteaccount));
    }

    public ResponseEntity<?> helperPermissionUpdated(User user) throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Not authenticated","status",false));
        }
        User users = (User) authentication.getPrincipal();
        if(users.getUsertype()!=8){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Unauthorized request","status",false));
        }
        User user1 = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found for this userId :: " + user.getUserId()));
        user1.setModules(user.getModules());
        User saveData = userRepository.save(user1);
        UserUpdatePermissionResponse userpaer = new UserUpdatePermissionResponse();
        userpaer.setUserId(user1.getUserId());
        userpaer.setModules(user1.getModules());
        userpaer.setId(user1.getId());
        return ResponseEntity.status(HttpStatus.OK).body( Map.of("message","User password change successfully","status",true,"data",userpaer));
    }

}


