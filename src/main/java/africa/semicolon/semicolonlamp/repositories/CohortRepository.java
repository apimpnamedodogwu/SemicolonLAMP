package africa.semicolon.semicolonlamp.repositories;

import africa.semicolon.semicolonlamp.models.Cohort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortRepository extends MongoRepository<Cohort, String> {

}
