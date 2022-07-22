package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import africa.semicolon.semicolonlamp.request.UserUpdateRequest;

import java.util.List;

public class UserServiceImplementation implements UserService{
    @Override
    public void changeUserType(String UserId, String newType) {

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
