package helper.helperapi.controller;

import helper.helperapi.dto.*;
import helper.helperapi.entity.EXUser;
import helper.helperapi.exception.ErrorMessageCustomException;
import helper.helperapi.exception.ResourceNotFoundException;
import helper.helperapi.jwt.JwtTokenUtil;
import helper.helperapi.modelResponse.CheckPermissionRes;
import helper.helperapi.models.Module;
import helper.helperapi.models.RefreshToken;
import helper.helperapi.models.User;
import helper.helperapi.mysqlRepo.UserRepo;
import helper.helperapi.repository.ModuleRepository;
import helper.helperapi.repository.RefreshTokenRepository;
import helper.helperapi.repository.UserRepository;
import helper.helperapi.repository.UserTokenRepository;
import helper.helperapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static java.awt.geom.Path2D.contains;

@RestController
@RequestMapping("/helper-api")
public class AuthREST {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserTokenRepository userTokenRepository ;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    UserRepo userRepo;


    @PostMapping("/userlogin")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginDTO dto){

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getUserId(),
                    dto.getPassword());

            try {
                Authentication authntication = authenticationManager.authenticate(authToken);
            } catch (BadCredentialsException e) {
                throw new ErrorMessageCustomException("Invalid Username or password", false);
            }

            String token = jwtTokenUtil.generateToken(dto.getUserId());
            LoginResponse loginRes = new LoginResponse();
            EXUser user = userRepo.findByUserid(dto.getUserId()).get();
            if (!List.of(6, 8).contains(user.getUsertype())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "usertype not matched, please contact admin"));
            }
            if(!user.getActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "your status not active please contact admin"));
            }
            if (user.getAccountlock()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "your account locked please contact admin"));
            }
            loginRes.setPassword(user.getPassword());
            loginRes.setId(user.getId());
            loginRes.setActive(user.getActive());
            loginRes.setUsertype(user.getUsertype());
            loginRes.setUserid(user.getUserid());
            loginRes.setAccountlock(user.getAccountlock());
            return ResponseEntity.ok(new TokenDTO("Login Successfull",loginRes,token));

        }catch (BadCredentialsException e){
            BadCredentialsException errorResponse = new BadCredentialsException("Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }




//    @PostMapping("/userlogin")
//    @Transactional
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Optional<User> user1 = userRepository.findByUserId(dto.getUserId());
//        User user2 = user1.get();
//        if (!List.of(6, 8).contains(user2.getUsertype())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "usertype not matched, please contact admin"));
//        }
//        if(!user2.getActive()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "your status not active please contact admin"));
//        }
//        if (user2.getAccountLock()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "your account locked please contact admin"));
//        }
//
//        User user = (User) authentication.getPrincipal();
//
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setOwner(user);
//        refreshTokenRepository.save(refreshToken);
//
//        String accessToken = jwtHelper.generateAccessToken(user);
//        String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);
//        Optional<UserToken>getusertoken = userTokenRepository.findByUseridAndStatus(dto.getUserId(), true);
//        if(getusertoken.isEmpty()){
//            UserToken userToken = new UserToken();
//            userToken.setAccessToken(accessToken);
//            userToken.setStatus(true);
//            userToken.setUserid(dto.getUserId());
//            userTokenRepository.save(userToken);
//        }else{
//            UserToken getuser = getusertoken.get();
//            if(getuser.getStatus()){
//                getuser.setStatus(false);
//                userTokenRepository.save(getuser);
//                UserToken userToken = new UserToken();
//                userToken.setAccessToken(accessToken);
//                userToken.setStatus(true);
//                userToken.setUserid(dto.getUserId());
//                userTokenRepository.save(userToken);
//            }
//        }
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setUserid(user1.get().getUserId());
//        loginResponse.setId(user1.get().getId());
//        loginResponse.setPassword(user1.get().getPassword());
//        loginResponse.setActive(user1.get().getActive());
//        loginResponse.setAccountlock(user1.get().getAccountLock());
//        loginResponse.setUsertype(6);
//
//        return ResponseEntity.ok(new TokenDTO(user.getId(),accessToken, refreshTokenString,"Login Successfull",loginResponse,accessToken));
//    }


    @PostMapping("/addHelperUser")
    @Transactional
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDTO dto) throws  ResourceNotFoundException {
        Optional<User> user1 = userRepository.findByUserId(dto.getUserId());
        if(user1.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","userid already axis","status",false ));
        }else {
            String plainPassword = dto.getPassword();
            String repeatPassword = dto.getConfirmPassword();
            if(plainPassword == null || !plainPassword.equals(repeatPassword)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","password and confirm password should be same","status",false ));

            }else{
                User user = new User();
                user.setUserId(dto.getUserId());
                // user.setEmail( dto.getEmail());
                user.setActive(true);
                user.setUsername(dto.getUsername());
                user.setUsertype(dto.getUsertype());
                user.setPassword(dto.getPassword());
                user.setAccountLock(false);
                user.setCreatedAt(new Date());
                List<String> modules = new ArrayList<>();
                for(String moduleName : dto.getModules()){
                    Module module = moduleRepository.findByModuleName(moduleName).orElseThrow(() -> new ResourceNotFoundException("Module not found for this id :: " + user.getUserId()));

                    if(!modules.contains(moduleName)){
                        modules.add(moduleName);
                    }
                }
                user.setModules(modules);
                userRepository.save(user);

                RefreshToken refreshToken = new RefreshToken();
                refreshToken.setOwner(user);
                refreshTokenRepository.save(refreshToken);

//                String accessToken = jwtHelper.generateAccessToken(user);
//                String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","User create successfully","status",true ));
            }
        }

    }

//    @PostMapping("logout")
//    public ResponseEntity<?> logout(@RequestBody TokenDTO dto) throws BadCredentialsException{
//        String refreshTokenString = dto.getRefreshToken();
//        if (jwtHelper.validateRefreshToken(refreshTokenString)
//                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
//            // valid and exists in db
//            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
//            return ResponseEntity.ok()
//                    .body(Map.of("message", "User logout successfully","status",true ));
//        }
//        return ResponseEntity.ok()
//                .body(Map.of("message", "invalid token","status",false ));
//
//    }

//    @PostMapping("logout-all")
//    public ResponseEntity<?> logoutAll(@RequestBody TokenDTO dto) {
//        String refreshTokenString = dto.getRefreshToken();
//        if (jwtHelper.validateRefreshToken(refreshTokenString)
//                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
//            // valid and exists in db
//
//            refreshTokenRepository.deleteByOwner_Id(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
//           // return ResponseEntity.ok().build();
//            return ResponseEntity.ok()
//                    .body(Map.of("message", "User logout successfully","status",true));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(Map.of("message", "invalid token","status",false));
//
//    }

//    @PostMapping("access-token")
//    public ResponseEntity<?> accessToken(@RequestBody TokenDTO dto) throws ResourceNotFoundException,BadCredentialsException {
//        String refreshTokenString = dto.getRefreshToken();
//        if (jwtHelper.validateRefreshToken(refreshTokenString)
//                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
//            // valid and exists in db
//
//            User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
//            if(user==null){
//                throw new ResourceNotFoundException("user id not found");
//            }
//            String accessToken = jwtHelper.generateAccessToken(user);
//
//            return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, refreshTokenString,null,null,null));
//        }
//        return ResponseEntity.status(401)
//                .body(Map.of("message", "invalid token please again login"));
//    }

//    @PostMapping("refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody TokenDTO dto) throws ResourceNotFoundException {
//        String refreshTokenString = dto.getRefreshToken();
//        if (jwtHelper.validateRefreshToken(refreshTokenString)
//                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
//            // valid and exists in db
//
//            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
//
//            User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
//
//            RefreshToken refreshToken = new RefreshToken();
//            refreshToken.setOwner(user);
//            refreshTokenRepository.save(refreshToken);
//
//            String accessToken = jwtHelper.generateAccessToken(user);
//            String newRefreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);
//
//            return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, newRefreshTokenString,null,null,null));
//        }
//
//        return ResponseEntity.status(498)
//                .body(Map.of("message", "invalid token"));
//    }

    @PostMapping("/checkpermission")
    public ResponseEntity<?> checkpermission (@RequestBody CheckPermissionDTO dto){
        Optional<User> user1 = userRepository.findByUserId(dto.getUserId());
        if(!user1.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "invalid user id","status",false));
        }
        User user = user1.get();
        CheckPermissionRes data = new CheckPermissionRes();
        data.setModules(user.getModules());
        return ResponseEntity.status(200)
                .body(Map.of("message", "get check permission successfully","status",true,"data",data));

    }

//    @GetMapping("/test")
//    public String tesst(){
//        return "server is runing";
//    }
}
