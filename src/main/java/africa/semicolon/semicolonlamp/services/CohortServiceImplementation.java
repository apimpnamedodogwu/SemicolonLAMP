package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.request.CohortCreateRequest;

import java.util.List;

public class CohortServiceImplementation implements CohortService{
    @Override
    public void changeCohortName(String cohortId, String newName) {

    }

    @Override
    public void updateCohortService(String userId, String newStatus) {

    }

    @Override
    public List<Cohort> getAllInSessionCohorts() {
        return null;
    }

    @Override
    public void deleteCohort(String id) {

    }

    @Override
    public List<Cohort> getAllCompletedCohort() {
        return null;
    }

    @Override
    public List<Cohort> getAllPendingCohort() {
        return null;
    }

    @Override
    public void createCohort(CohortCreateRequest request) {

    }

    @Override
    public List<Cohort> getCohort(String id) {
        return null;
    }

    @Override
    public List<Cohort> getAllCohorts() {
        return null;
    }
}
