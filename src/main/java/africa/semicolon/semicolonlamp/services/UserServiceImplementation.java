package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.models.UserType;
import africa.semicolon.semicolonlamp.repositories.UserRepository;
import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import africa.semicolon.semicolonlamp.request.UserUpdateRequest;
import africa.semicolon.semicolonlamp.services.lampExceptions.InvalidUserIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void changeUserType(String userId, String newType) {
        var existingUser = userRepository.findUserById(userId);
        if (existingUser.isPresent()) {
            UserType userType = UserType.valueOf(newType);
            existingUser.get().setUserType(userType);
            userRepository.save(existingUser.get());
            return;
        }
        throw new InvalidUserIdException("User with " + userId + " does not exist!");

    }

    @Override
    public void updateUserDetails(String userId, UserUpdateRequest request) {

    }

    @Override
    public List<User> getAllAncestors() {
        return null;
    }

    @Override
    public List<User> getAllNatives() {
        return null;
    }

    @Override
    public List<Cohort> getAllNativesInACohort(String cohortId) {
        return null;
    }

    @Override
    public User getUser(String userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void createUser(UserRegistrationRequest request) {

    }
}
