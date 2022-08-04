package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementationTest {
    @Mock
    UserRepository userRepository;
    @Mock
    CohortRepository cohortRepository;
    @Mock
    UserUpdateRequest userUpdateRequest;
    @InjectMocks
    UserServiceImplementation userServiceImplementation;


    @Test
    void testThatUserCanChangeType() {
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
    void testThatChangeUserTypeUserIdExceptionIsThrown() {
        User user = new User();
        String newType = "IN_SESSION";
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.changeUserType(user.getId(), newType))
                .isInstanceOf(InvalidUserIdException.class)
                .hasMessageContaining("User with " + user.getId() + " does not exist!");
    }

    @Test
    void testThatUserTypeExceptionIsThrown() {
        User user = new User();
        String newType = "Bread";
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userServiceImplementation.changeUserType(user.getId(), newType))
                .isInstanceOf(UserTypeException.class)
                .hasMessageContaining("Oops " + newType + " does not exist!");
    }

    @Test
    void testThatUserCanUpdateDetails() {
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
    void testThatExceptionIsThrownInMethodUpdateUserDetails() {
        UserUpdateRequest request = new UserUpdateRequest();
        User user = new User();
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setEmail("eden.kwinesta@gmail.com");
        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.updateUserDetails(user.getId(), request))
                .isInstanceOf(ExistingEmailException.class)
                .hasMessageContaining(request.getEmail() + " does not exist!");
    }

    @Test
    void testThatAllEldersCanBeGotten() {
        userServiceImplementation.getAllElders();
        verify(userRepository).findUserByUserType(UserType.ELDER);
    }

    @Test
    void testThatAllAncestorsCanBeGotten() {
        userServiceImplementation.getAllAncestors();
        verify(userRepository).findUserByUserType(UserType.ANCESTOR);
    }

    @Test
    void testThatAllNativesCanBeGotten() {
        userServiceImplementation.getAllNatives();
        verify(userRepository).findUserByUserType(UserType.NATIVE);
    }

    @Test
    void testThatAllNativesInACohortCanBeGotten() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        userServiceImplementation.getAllNativesInACohort(cohort.getId());
        verify(userRepository).findUsersByCohort(cohort);
    }

    @Test
    void testThatExceptionIsThrownInMethodGetAllNativesInACohort() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.getAllNativesInACohort(cohort.getId()))
                .isInstanceOf(CohortException.class)
                .hasMessageContaining("Cohort with " + cohort.getId() + " does not exist!");
    }

    @Test
    void testThatUserCanBeGotten() {
        User user = new User();
        String userId = "1";
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        userServiceImplementation.getUser(userId);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findUserById(stringArgumentCaptor.capture());
        var capturedId = stringArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(userId);

    }

    @Test
    void testThatExceptionIsThrownInMethodGetUser() {
        User user = new User();
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userServiceImplementation.getUser(user.getId()))
                .isInstanceOf(InvalidUserIdException.class)
                .hasMessageContaining("User with id " + user.getId() + " does not exist!");
    }

    @Test
    void getAllUsers() {
        userServiceImplementation.getAllUsers();
        verify(userRepository).findAll();

    }

    @Test
    void testThatUserCanBeDeleted() {
        User user = new User();
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        userServiceImplementation.deleteUser(user.getId());
        verify(userRepository).delete(user);
    }

    @Test
    void testThatNativeCanBeCreated() {
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
    void testThatExceptionIsThrownInMethodCreateNative() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        User user = new User();
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setPassword("12345678");
        request.setEmail("eden.kwinesta@gmail.com");
        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userServiceImplementation.createUserNative(request))
                .isInstanceOf(ExistingEmailException.class)
                .hasMessageContaining(request.getEmail() + " already exists!");
    }

    @Test
    void testThatElderCanBeCreated() {
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
    void testThatExceptionIsThrownInMethodCreateElder() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        User user = new User();
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setPassword("12345678");
        request.setEmail("eden.kwinesta@gmail.com");
        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userServiceImplementation.createUserNative(request))
                .isInstanceOf(ExistingEmailException.class)
                .hasMessageContaining(request.getEmail() + " already exists!");
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

    @Test
    void testThatExceptionIsThrownInMethodCreateAncestor() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        User user = new User();
        request.setFirstName("Eden");
        request.setLastName("Elenwoke");
        request.setPassword("12345678");
        request.setEmail("eden.kwinesta@gmail.com");
        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> userServiceImplementation.createUserNative(request))
                .isInstanceOf(ExistingEmailException.class)
                .hasMessageContaining(request.getEmail() + " already exists!");
    }
}