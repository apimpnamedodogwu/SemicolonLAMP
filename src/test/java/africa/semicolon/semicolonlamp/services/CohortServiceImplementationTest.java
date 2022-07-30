package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.CohortStatus;
import africa.semicolon.semicolonlamp.repositories.CohortRepository;
import africa.semicolon.semicolonlamp.request.CohortCreateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CohortServiceImplementationTest {
    @Mock
    CohortRepository cohortRepository;

    @InjectMocks
    CohortServiceImplementation cohortServiceImplementation;

    @Test
    void changeCohortName() {
        Cohort cohort = new Cohort();
        String name = "The Seven";
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        cohortServiceImplementation.changeCohortName(cohort.getId(), name);
        ArgumentCaptor<Cohort> cohortArgumentCaptor = ArgumentCaptor.forClass(Cohort.class);
        verify(cohortRepository).save(cohortArgumentCaptor.capture());
        var changedCohort = cohortArgumentCaptor.getValue();
        assertThat(changedCohort.getName()).isEqualTo(name);
        verify(cohortRepository, times(1)).save(cohort);
    }

    @Test
    void updateCohortStatus() {
        Cohort cohort = new Cohort();
        String status = "IN_SESSION";
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        cohortServiceImplementation.updateCohortStatus(cohort.getId(), status);
        ArgumentCaptor<Cohort> cohortArgumentCaptor = ArgumentCaptor.forClass(Cohort.class);
        verify(cohortRepository).save(cohortArgumentCaptor.capture());
        var updatedCohort = cohortArgumentCaptor.getValue();
        assertThat(updatedCohort.getStatus()).isEqualTo(CohortStatus.IN_SESSION);
        verify(cohortRepository, times(1)).save(cohort);

    }

    @Test
    void getAllInSessionCohorts() {
        cohortServiceImplementation.getAllInSessionCohorts();
        verify(cohortRepository).findCohortByStatus(CohortStatus.IN_SESSION);
    }

    @Test
    void deleteCohort() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        cohortServiceImplementation.deleteCohort(cohort.getId());
        verify(cohortRepository).delete(cohort);
    }

    @Test
    void getAllCompletedCohort() {
        cohortServiceImplementation.getAllCompletedCohort();
        verify(cohortRepository).findCohortByStatus(CohortStatus.COMPLETED);
    }

    @Test
    void getAllPendingCohort() {
        cohortServiceImplementation.getAllPendingCohort();
        verify(cohortRepository).findCohortByStatus(CohortStatus.PENDING);
    }

    @Test
    void createCohort() {
        CohortCreateRequest request = new CohortCreateRequest();
        request.setName("Avengers");
        request.setLocalDate(request.getLocalDate());
        cohortServiceImplementation.createCohort(request);
        ArgumentCaptor<Cohort> cohortArgumentCaptor = ArgumentCaptor.forClass(Cohort.class);
        verify(cohortRepository).save(cohortArgumentCaptor.capture());
        var capturedCohort = cohortArgumentCaptor.getValue();
        assertThat(capturedCohort.getName()).isEqualTo(request.getName());
        assertThat(capturedCohort.getLocalDate()).isEqualTo(request.getLocalDate());
        assertThat(capturedCohort.getStatus()).isEqualTo(CohortStatus.PENDING);
        verify(cohortRepository, times(1)).save(capturedCohort);
    }

    @Test
    void getCohort() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        cohortServiceImplementation.getCohort(cohort.getId());
    }

    @Test
    void getAllCohorts() {
        cohortServiceImplementation.getAllCohorts();
        verify(cohortRepository).findAll();
    }
}