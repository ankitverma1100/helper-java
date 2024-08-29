package helper.helperapi.repository;
import helper.helperapi.models.UserToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserTokenRepository extends MongoRepository<UserToken, String> {
    Optional<UserToken> findByUseridAndStatus(String userid, boolean status);
    UserToken findByAccessToken(String accessToken);
}
