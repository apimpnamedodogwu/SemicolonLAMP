package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.repositories.UserRepository;
import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImplementation userServiceImplementation;




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
        UserRegistrationRequest request = new UserRegistrationRequest();
//        User user = new User();
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setEmail("eden.kwinesta@gmail.com");
        request.setPassword("12345678");
        userServiceImplementation.createUserNative(request);
        ArgumentCaptor<User> UserArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(UserArgumentCaptor.capture());
        var capturedUser = UserArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(request);

    }

    @Test
    void createUserElder() {
    }

    @Test
    void createUserAncestor() {
    }
}