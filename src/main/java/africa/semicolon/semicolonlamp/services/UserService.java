package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import africa.semicolon.semicolonlamp.request.UserUpdateRequest;

import java.util.List;

public interface UserService {
    void changeUserType(String UserId, String newType);

    void updateUserDetails(String userId, UserUpdateRequest request);
    List<User> getAllElders();

    List<User> getAllAncestors();


    List<User> getAllNatives();

    List<User> getAllNativesInACohort(String cohortId);

    User getUser(String userId);

    List<User> getAllUsers();

    void deleteUser(String userId);

    void createUserNative(UserRegistrationRequest request);

    void createUserElder(UserRegistrationRequest request);

    void createUserAncestor(UserRegistrationRequest request);

}
