package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.CohortStatus;
import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.models.UserType;
import africa.semicolon.semicolonlamp.repositories.CohortRepository;
import africa.semicolon.semicolonlamp.repositories.UserRepository;
import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import africa.semicolon.semicolonlamp.request.UserUpdateRequest;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortException;
import africa.semicolon.semicolonlamp.services.lampExceptions.ExistingEmailException;
import africa.semicolon.semicolonlamp.services.lampExceptions.InvalidUserIdException;
import africa.semicolon.semicolonlamp.services.lampExceptions.UserTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;
    CohortRepository cohortRepository;

    private User validateUserId(String userId) {
        return userRepository.findUserById(userId).
                orElseThrow(() -> new InvalidUserIdException("User with id " + userId + " does not exist!"));
    }

    private User validateUserEmail(UserRegistrationRequest request) {
        return userRepository.findUserByEmail(request.getEmail()).
                orElseThrow(() -> new ExistingEmailException(request.getEmail() + " already exists!"));
    }

    @Override
    public void changeUserType(String userId, String newType) {
        var existingUser = userRepository.findUserById(userId);
        if (existingUser.isPresent()) {
            try {
                UserType userType = UserType.valueOf(newType);
                existingUser.get().setUserType(userType);
            } catch (Exception message) {
                throw new UserTypeException("Oops " + newType + " does not exist!");
            }
            userRepository.save(existingUser.get());
            return;
        }
        throw new InvalidUserIdException("User with " + userId + " does not exist!");

    }

    @Override
    public void updateUserDetails(String userId, UserUpdateRequest request) {

    }

    @Override
    public List<User> getAllElders() {
        return userRepository.findUserByUserType(UserType.ELDER);
    }

    @Override
    public List<User> getAllAncestors() {
        return userRepository.findUserByUserType(UserType.ANCESTOR);
    }


    @Override
    public List<User> getAllNatives() {
        return userRepository.findUserByUserType(UserType.NATIVE);
    }

    @Override
    public List<User> getAllNativesInACohort(String cohortId) {
        var cohort = cohortRepository.findCohortById(cohortId);
        if (cohort.isPresent()) {
            return userRepository.findUsersByCohort(cohort.get());
        } else throw new CohortException("Cohort with " + cohortId + " does not exist!");
    }

    @Override
    public User getUser(String userId) {
        var user = userRepository.findUserById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new InvalidUserIdException("User with id " + userId + " does not exist!");

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String userId) {
        var user = validateUserId(userId);
        userRepository.delete(user);
    }

    @Override
    public void createUserNative(UserRegistrationRequest request) {
        request.validateRegistrationRequest(request);
        var existingEmail = userRepository.findUserByEmail(request.getEmail());
        if (existingEmail.isPresent()) {
            throw new ExistingEmailException(request.getEmail() + " already exists!");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUserType(UserType.NATIVE);
        userRepository.save(user);
    }

    @Override
    public void createUserElder(UserRegistrationRequest request) {
        request.validateRegistrationRequest(request);
        var user = validateUserEmail(request);
//        var existingEmail = userRepository.findUserByEmail(request.getEmail());
//        if (existingEmail.isPresent()) {
//            throw new ExistingEmailException(request.getEmail() + " already exists!");
//        }
        user.setFirstName(request.getFirstName());

//        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUserType(UserType.ELDER);
        userRepository.save(user);
    }

    @Override
    public void createUserAncestor(UserRegistrationRequest request) {
        request.validateRegistrationRequest(request);
        var existingEmail = userRepository.findUserByEmail(request.getEmail());
        if (existingEmail.isPresent()) {
            throw new ExistingEmailException(request.getEmail() + " already exists!");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUserType(UserType.ANCESTOR);
        userRepository.save(user);
    }
}
