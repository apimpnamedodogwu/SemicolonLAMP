package africa.semicolon.semicolonlamp.repositories;


import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.models.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserById(String userId);
    Optional<User> findUserByEmail(String email);
    List<User> findUserByCohortId(String cohortId);
    List<User> findUserByUserType(UserType userType);
}
