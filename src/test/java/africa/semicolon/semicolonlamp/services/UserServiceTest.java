package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
//    @Autowired
    UserServiceImplementation userServiceImplementation;


    @BeforeEach
    void setUp() {
        userServiceImplementation = new UserServiceImplementation();
    }

    @Test
    void changeUserType() {
    }

    @Test
    void updateUserDetails() {
    }

    @Test
    void getAllElders() {
    }

    @Test
    void getAllAncestors() {
    }

    @Test
    void getAllNatives() {
    }

    @Test
    void getAllNativesInACohort() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getAllUsers() {
        userServiceImplementation.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void deleteUser() {
    }

    @Test
    void createUserNative() {
    }

    @Test
    void createUserElder() {
    }

    @Test
    void createUserAncestor() {
    }
}