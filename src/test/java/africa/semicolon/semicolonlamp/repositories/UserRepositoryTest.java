package africa.semicolon.semicolonlamp.repositories;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.CohortStatus;
import africa.semicolon.semicolonlamp.models.User;
import africa.semicolon.semicolonlamp.models.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CohortRepository cohortRepository;


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testThatUserCanBeFoundById() {
        User user = new User();
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setEmail("eden.kwinesta@gmail.com");
        user.setPassword("12345678");
        user.setUserType(UserType.NATIVE);
        var savedObj = userRepository.save(user);
        var exists = userRepository.findUserById(savedObj.getId());
        assertThat(exists.isPresent()).isTrue();
        assertThat(exists.get().getFirstName()).isEqualTo("Eden");
        assertThat(exists.get().getLastName()).isEqualTo("Elenwoke");
    }

    @Test
    void testThatUserCanBeFoundByEmail() {
        User user = new User();
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setEmail("eden.kwinesta@gmail.com");
        user.setPassword("12345678");
        user.setUserType(UserType.NATIVE);
        var savedObj = userRepository.save(user);
        var exists = userRepository.findUserByEmail(savedObj.getEmail());
        assertThat(exists.isPresent()).isTrue();
        assertThat(exists.get().getEmail()).isEqualTo("eden.kwinesta@gmail.com");
    }

    @Test
    void testThatUserCanBeFoundByCohort() {
        Cohort cohortOne = new Cohort();
        cohortOne.setName("Avengers");
        cohortOne.setStatus(CohortStatus.PENDING);
        Cohort cohortTwo = new Cohort();
        cohortTwo.setName("The Seven");
        cohortTwo.setStatus(CohortStatus.PENDING);
        cohortRepository.save(cohortOne);
        cohortRepository.save(cohortTwo);
        User user = new User();
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setEmail("eden.kwinesta@gmail.com");
        user.setPassword("12345678");
        user.setUserType(UserType.NATIVE);
        user.setCohort(cohortOne);
        userRepository.save(user);
        User user1 = new User();
        user1.setFirstName("Mofe");
        user1.setLastName("Oggunbiyi");
        user1.setEmail("mofe@gmail.com");
        user1.setPassword("12345678");
        user1.setUserType(UserType.NATIVE);
        user1.setCohort(cohortTwo);
        userRepository.save(user1);
        var firstList = userRepository.findUsersByCohort(cohortOne);
        var secList = userRepository.findUsersByCohort(cohortTwo);
        assertThat(firstList.size()).isEqualTo(1);
        assertThat(secList.size()).isEqualTo(1);


    }

    @Test
    void findUserByUserType() {
        User user = new User();
        user.setFirstName("Eden");
        user.setLastName("Elenwoke");
        user.setEmail("eden.kwinesta@gmail.com");
        user.setPassword("12345678");
        user.setUserType(UserType.NATIVE);
        userRepository.save(user);
        User user1 = new User();
        user1.setFirstName("Mofe");
        user1.setLastName("Oggunbiyi");
        user1.setEmail("mofe@gmail.com");
        user1.setPassword("12345678");
        user1.setUserType(UserType.NATIVE);
        userRepository.save(user1);
        var userList = userRepository.findUserByUserType(UserType.NATIVE);
        assertThat(userList.size()).isEqualTo(2);
    }
}