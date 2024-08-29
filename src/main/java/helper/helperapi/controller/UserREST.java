package helper.helperapi.controller;

import helper.helperapi.dto.SignupDTO;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.modelResponse.UpdateAccountLockResponse;
import helper.helperapi.modelResponse.UpdatedAccountActiveResponse;
import helper.helperapi.modelResponse.UserUpdatePermissionResponse;
import helper.helperapi.models.User;
import helper.helperapi.models.UserToken;
import helper.helperapi.repository.UserRepository;
import helper.helperapi.repository.UserTokenRepository;
import helper.helperapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/users")
public class UserREST {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService ;

    @Autowired
    UserTokenRepository userTokenRepository ;

    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }
    @PutMapping("/activeaccount")
    public ResponseEntity<?> updatedAccountActive (@RequestBody User user) throws ResourceNotFoundException {
       return  userService.updatedAccountActive(user);
    }
    @GetMapping("/gethelperuserdata")
    public ResponseEntity<?> gethelperuserdata (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Not authenticated","status",false));
        }
        User users = (User) authentication.getPrincipal();
        if(users.getUsertype()!=8){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( Map.of("message","Unauthorized request","status",false));
        }
       List<User> userList = userRepository.findAll();
       if(userList.isEmpty()){
           return ResponseEntity.ok()
                   .body(Map.of("message", "No data","status",false));
       }
       return ResponseEntity.ok()
               .body(Map.of("message", "Get all data  successfully","status",true ,"data", userList));
   }
    @PutMapping("/accountlock")
    public ResponseEntity<?> updateAccountLock (@RequestBody User user) throws ResourceNotFoundException {
        return  userService.updateAccountLock(user);
    }

    @PostMapping("/updatePermission")
    public ResponseEntity<?> updatePermission (@RequestBody User user) throws  ResourceNotFoundException {
        return userService.helperPermissionUpdated(user);
    }


   @PutMapping("/changepassword")
   public ResponseEntity<?> changepassworduser (@RequestBody SignupDTO dto){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (authentication == null || !authentication.isAuthenticated()) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Not authenticated"));
       }
       User user = (User) authentication.getPrincipal();
       String plainPassword = dto.getNewPassword();
       String repeatPassword = dto.getConfirmPassword();
       if(plainPassword == null || !plainPassword.equals(repeatPassword)) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","new password and confirm password should be same","status",false));

       }
       user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
       userRepository.save(user);
       return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","User password change successfully","status",true));

   }


    @GetMapping("/tokenstatus")
    public ResponseEntity<Object> getTokenStatus(@RequestHeader("Authorization") String authorization) {
            String[] authParts = authorization.split(" ");
            if (authParts.length != 2) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Authorization "));
            }
           String accessToken = authParts[1];
            UserToken isTokenExists = userTokenRepository.findByAccessToken(accessToken);
            if (isTokenExists == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Authorization user 2"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Get user token status","status",isTokenExists.getStatus()));
        }
}

