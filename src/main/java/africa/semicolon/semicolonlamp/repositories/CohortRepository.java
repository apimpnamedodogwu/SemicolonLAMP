package africa.semicolon.semicolonlamp.repositories;

import africa.semicolon.semicolonlamp.models.Cohort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CohortRepository extends MongoRepository<Cohort, String> {
    Optional<Cohort> findCohortById(String cohortId);
}
