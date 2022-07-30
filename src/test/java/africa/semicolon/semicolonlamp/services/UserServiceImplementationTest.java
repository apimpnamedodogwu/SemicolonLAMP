package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.models.UserType;
import africa.semicolon.semicolonlamp.repositories.CohortRepository;
import africa.semicolon.semicolonlamp.repositories.UserRepository;
import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import africa.semicolon.semicolonlamp.request.UserUpdateRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CohortRepository cohortRepository;
    @InjectMocks
    UserServiceImplementation userServiceImplementation;


    @Test
    void changeUserType() {
        User user = new User();
        String newType = "ANCESTOR";
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        userServiceImplementation.changeUserType(user.getId(), newType);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        var capturedUpdate = userArgumentCaptor.getValue();
        assertThat(capturedUpdate.getUserType()).isEqualTo(UserType.ANCESTOR);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUserDetails() {
        UserUpdateRequest request = new UserUpdateRequest();
        User user = new User();
        request.setFirstName("Mofe");
        request.setLastName("Ogunbiyi");
        request.setEmail("ogunbiyi@gmail.com");
        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
        userServiceImplementation.updateUserDetails(user.getId(), request);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        var capturedUpdate = argumentCaptor.getValue();
        assertThat(capturedUpdate.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(capturedUpdate.getLastName()).isEqualTo(request.getLastName());
        assertThat(capturedUpdate.getEmail()).isEqualTo(request.getEmail());
        verify(userRepository, times(1)).save(user);
     }

    @Test
    void getAllElders() {
        userServiceImplementation.getAllElders();
        verify(userRepository).findUserByUserType(UserType.ELDER);
    }

    @Test
    void getAllAncestors() {
        userServiceImplementation.getAllAncestors();
        verify(userRepository).findUserByUserType(UserType.ANCESTOR);
    }

    @Test
    void getAllNatives() {
        userServiceImplementation.getAllNatives();
        verify(userRepository).findUserByUserType(UserType.NATIVE);
    }

    @Test
    void getAllNativesInACohort() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        userServiceImplementation.getAllNativesInACohort(cohort.getId());
        verify(userRepository).findUsersByCohort(cohort);
    }

    @Test
    void getUser() {
        User user = new User();
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        userServiceImplementation.getUser(user.getId());
    }

    @Test
    void getAllUsers() {
        userServiceImplementation.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void deleteUser() {
        User user = new User();
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        userServiceImplementation.deleteUser(user.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void createUserNative() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setEmail("eden.kwinesta@gmail.com");
        request.setPassword("12345678");
        userServiceImplementation.createUserNative(request);
        ArgumentCaptor<User> UserArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(UserArgumentCaptor.capture());
        var capturedUser = UserArgumentCaptor.getValue();
        assertThat(capturedUser.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(capturedUser.getEmail()).isEqualTo(request.getEmail());
        assertThat(capturedUser.getLastName()).isEqualTo(request.getLastName());
        assertThat(capturedUser.getUserType()).isEqualTo(UserType.NATIVE);
        verify(userRepository, times(1)).save(capturedUser);
    }

    @Test
    void createUserElder() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("Chibuzor");
        request.setLastName("Ekejiuba");
        request.setEmail("chibobo.@gmail.com");
        request.setPassword("12345678");
        userServiceImplementation.createUserElder(request);
        ArgumentCaptor<User> UserArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(UserArgumentCaptor.capture());
        var capturedUser = UserArgumentCaptor.getValue();
        assertThat(capturedUser.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(capturedUser.getEmail()).isEqualTo(request.getEmail());
        assertThat(capturedUser.getLastName()).isEqualTo(request.getLastName());
        assertThat(capturedUser.getUserType()).isEqualTo(UserType.ELDER);
        verify(userRepository, times(1)).save(capturedUser);
    }

    @Test
    void createUserAncestor() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setFirstName("Eyimofe");
        request.setLastName("Ogunbiyi");
        request.setEmail("mofe.@gmail.com");
        request.setPassword("12345678");
        userServiceImplementation.createUserAncestor(request);
        ArgumentCaptor<User> UserArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(UserArgumentCaptor.capture());
        var capturedUser = UserArgumentCaptor.getValue();
        assertThat(capturedUser.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(capturedUser.getEmail()).isEqualTo(request.getEmail());
        assertThat(capturedUser.getLastName()).isEqualTo(request.getLastName());
        assertThat(capturedUser.getUserType()).isEqualTo(UserType.ANCESTOR);
        verify(userRepository, times(1)).save(capturedUser);
    }
}