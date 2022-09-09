package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.CohortStatus;
import africa.semicolon.semicolonlamp.repositories.CohortRepository;
import africa.semicolon.semicolonlamp.request.CohortCreateRequest;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortException;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortStatusException;
import africa.semicolon.semicolonlamp.services.lampExceptions.EmptyFieldException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CohortServiceImplementation implements CohortService {
    @Autowired
    CohortRepository cohortRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void changeCohortName(String cohortId, String newName) {
        var cohort = cohortRepository.findCohortById(cohortId);
        if (cohort.isPresent()) {
            cohort.get().setName(newName);
            cohortRepository.save(cohort.get());
            return;
        }
        throw new CohortException("Cohort with " + cohortId + " does not exist!");
    }

    @Override
    public void updateCohortStatus(String cohortId, String newStatus) {
        var cohort = cohortRepository.findCohortById(cohortId);
        if (cohort.isPresent()) {
            try {
                CohortStatus status = CohortStatus.valueOf(newStatus);
                cohort.get().setStatus(status);
            } catch (Exception message) {
                throw new CohortStatusException("Oops " + newStatus + " does not exist!");
            }
            cohortRepository.save(cohort.get());
            return;
        }
        throw new CohortException("Cohort with " + cohortId + " does not exist!");
    }

    @Override
    public List<Cohort> getAllInSessionCohorts() {
        return cohortRepository.findCohortByStatus(CohortStatus.IN_SESSION);
    }

    @Override
    public void deleteCohort(String id) {
        var cohort = cohortRepository.findCohortById(id);
        if (cohort.isPresent()) {
            cohortRepository.delete(cohort.get());
            return;
        }
        throw new CohortException("Cohort with " + id + " does not exist!");
    }

    @Override
    public List<Cohort> getAllCompletedCohort() {
        return cohortRepository.findCohortByStatus(CohortStatus.COMPLETED);
    }

    @Override
    public List<Cohort> getAllPendingCohort() {
        return cohortRepository.findCohortByStatus(CohortStatus.PENDING);
    }

    @Override
    public void createCohort(CohortCreateRequest request) {
        if (request.getName().isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty!");
        }
        var existingName = cohortRepository.findCohortByName(request.getName());
        if (existingName.isPresent()) {
            throw new CohortException(request.getName() + " already exists!");
        }
        Cohort cohort = new Cohort();
        modelMapper.map(request, cohort);
        cohort.setStatus(cohort.getStatus());
        cohortRepository.save(cohort);
    }

    @Override
    public Cohort getCohort(String id) {
        var cohort = cohortRepository.findCohortById(id);
        if (cohort.isPresent()) {
            return cohort.get();
        }
        throw new CohortException("Cohort with " + id + " does not exist!");
    }

    @Override
    public List<Cohort> getAllCohorts() {
        return cohortRepository.findAll();
    }
}
