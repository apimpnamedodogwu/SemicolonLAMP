package africa.semicolon.semicolonlamp.repositories;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.CohortStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataMongoTest
class CohortRepositoryTest {
    @Autowired
    CohortRepository cohortRepository;

    @AfterEach
    void tearDown() {
        cohortRepository.deleteAll();
    }

    @Test
    void testThatCohortCanBeFoundById() {
        Cohort cohortOne = new Cohort();
        cohortOne.setName("Avengers");
        cohortOne.setStatus(CohortStatus.PENDING);
        var cohort = cohortRepository.save(cohortOne);
        var cohortWithId = cohortRepository.findCohortById(cohort.getId());
        assertThat(cohortWithId.isPresent()).isTrue();
        assertThat(cohortWithId.get().getName()).isEqualTo("Avengers");
    }

    @Test
    void testThatCohortCanBeFoundByStatus() {
        cohortRepository.deleteAll();
        Cohort cohortOne = new Cohort();
        cohortOne.setName("Avengers");
        cohortOne.setStatus(CohortStatus.PENDING);
        cohortRepository.save(cohortOne);
        Cohort cohort = new Cohort();
        cohort.setName("The Seven");
        cohort.setStatus(cohort.getStatus());
        cohortRepository.save(cohort);
        Cohort cohort1 = new Cohort();
        cohort1.setName("Charliez Angels");
        cohort1.setStatus(CohortStatus.IN_SESSION);
        cohortRepository.save(cohort1);
        var firstList = cohortRepository.findCohortByStatus(CohortStatus.PENDING);
        var secondList = cohortRepository.findCohortByStatus(CohortStatus.IN_SESSION);
        var thirdList = cohortRepository.findCohortByStatus(CohortStatus.COMPLETED);
        assertThat(firstList.size()).isEqualTo(2);
        assertThat(secondList.size()).isEqualTo(1);
        assertThat(thirdList.size()).isEqualTo(0);


    }

    @Test
    void testThatCohortCanBeFoundByName() {
        Cohort cohortOne = new Cohort();
        cohortOne.setName("Avengers");
        cohortOne.setStatus(CohortStatus.PENDING);
        var savedOne = cohortRepository.save(cohortOne);
        Cohort cohort = new Cohort();
        cohort.setName("The Seven");
        cohort.setStatus(cohort.getStatus());
        cohortRepository.save(cohort);
        var existingOne = cohortRepository.findCohortByName(savedOne.getName());
        assertThat(existingOne.isPresent()).isTrue();
        assertThat(existingOne.get().getName()).isEqualTo("Avengers");
    }
}