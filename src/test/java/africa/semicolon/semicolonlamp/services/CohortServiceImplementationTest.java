package africa.semicolon.semicolonlamp.services;

import africa.semicolon.semicolonlamp.models.Cohort;
import africa.semicolon.semicolonlamp.models.CohortStatus;
import africa.semicolon.semicolonlamp.repositories.CohortRepository;
import africa.semicolon.semicolonlamp.request.CohortCreateRequest;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortException;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortStatusException;
import africa.semicolon.semicolonlamp.services.lampExceptions.EmptyFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CohortServiceImplementationTest {
    @Mock
    CohortRepository cohortRepository;

    @InjectMocks
    CohortServiceImplementation cohortServiceImplementation;

    @Test
    void testThatAcCohortNameCanBeChanged() {
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
    void testThatExceptionMessageIsThrownInMethodChangeCohortName() {
        Cohort cohort = new Cohort();
        cohort.setName("Avengers");
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cohortServiceImplementation.changeCohortName(cohort.getId(), cohort.getName()))
                .isInstanceOf(CohortException.class)
                .hasMessageContaining("Cohort with " + cohort.getId() + " does not exist!");
    }

    @Test
    void testThatStatusExceptionMessageIsThrownInMethodUpdateCohortStatus() {
        Cohort cohort = new Cohort();
        String status = "Bread";
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        assertThatThrownBy(() -> cohortServiceImplementation.updateCohortStatus(cohort.getId(), status))
                .isInstanceOf(CohortStatusException.class)
                .hasMessageContaining("Oops " + status + " does not exist!");
    }

    @Test
    void testThatCohortIdExceptionMessageIsThrownInMethodUpdateCohortStatus() {
        Cohort cohort = new Cohort();
        String status = "IN_SESSION";
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cohortServiceImplementation.updateCohortStatus(cohort.getId(), status))
                .isInstanceOf(CohortException.class)
                .hasMessageContaining("Cohort with " + cohort.getId() + " does not exist!");
    }


    @Test
    void testThatCohortCanBeUpdated() {
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
    void testThatAllCohortsInSessionCanBeGotten() {
        cohortServiceImplementation.getAllInSessionCohorts();
        verify(cohortRepository).findCohortByStatus(CohortStatus.IN_SESSION);
    }

    @Test
    void testThatCohortCanBeDeleted() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.of(cohort));
        cohortServiceImplementation.deleteCohort(cohort.getId());
        verify(cohortRepository).delete(cohort);
    }

    @Test
    void testThatExceptionIsThrownInMethodDeleteCohort() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cohortServiceImplementation.deleteCohort(cohort.getId()))
                .isInstanceOf(CohortException.class)
                .hasMessageContaining("Cohort with " + cohort.getId() + " does not exist!");
    }

    @Test
    void tesThatAlCompletedCohortsCanBeGotten() {
        cohortServiceImplementation.getAllCompletedCohort();
        verify(cohortRepository).findCohortByStatus(CohortStatus.COMPLETED);
    }

    @Test
    void testThatAllPendingCohortsCanBeGotten() {
        cohortServiceImplementation.getAllPendingCohort();
        verify(cohortRepository).findCohortByStatus(CohortStatus.PENDING);

    }

    @Test
    void testThatACohortCanBeCreated() {
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
    void testThatEmptyFieldExceptionIsThrownInMethodCreateCohort() {
        CohortCreateRequest request = new CohortCreateRequest();
        request.setName("");
        assertThatThrownBy(() -> cohortServiceImplementation.createCohort(request))
                .isInstanceOf(EmptyFieldException.class)
                .hasMessageContaining("Name field cannot be empty!");
    }

    @Test
    void testThatCohortExceptionIsThrownInMethodCreateCohort() {
        CohortCreateRequest request = new CohortCreateRequest();
        Cohort cohort = new Cohort();
        request.setName("Avengers");
        when(cohortRepository.findCohortByName(request.getName())).thenReturn(Optional.of(cohort));
        assertThatThrownBy(() -> cohortServiceImplementation.createCohort(request))
                .isInstanceOf(CohortException.class)
                .hasMessageContaining(request.getName() + " already exists!");
    }

    @Test
    void testThatACohortCanBeGotten() {
        Cohort cohort = new Cohort();
        String id = "1";
        when(cohortRepository.findCohortById(id)).thenReturn(Optional.of(cohort));
        cohortServiceImplementation.getCohort(id);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(cohortRepository).findCohortById(stringArgumentCaptor.capture());
        var capturedId = stringArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void testThatExceptionMessageInMethodGetCohortIsThrown() {
        Cohort cohort = new Cohort();
        when(cohortRepository.findCohortById(cohort.getId())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cohortServiceImplementation.getCohort(cohort.getId()))
                .isInstanceOf(CohortException.class)
                .hasMessage("Cohort with " + cohort.getId() + " does not exist!");

    }

    @Test
    void testThatAllCohortsCanBeGotten() {
        cohortServiceImplementation.getAllCohorts();
        verify(cohortRepository).findAll();
    }
}