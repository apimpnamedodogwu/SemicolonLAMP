package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.request.CohortCreateRequest;

import java.util.List;

public interface CohortService {
    void changeCohortName(String cohortId, String newName);

    void updateCohortStatus(String cohortId, String newStatus);

    List<Cohort> getAllInSessionCohorts();

    void deleteCohort(String id);

    List<Cohort> getAllCompletedCohort();

    List<Cohort> getAllPendingCohort();

    void createCohort(CohortCreateRequest request);

    Cohort getCohort(String id);

    List<Cohort> getAllCohorts();
}

