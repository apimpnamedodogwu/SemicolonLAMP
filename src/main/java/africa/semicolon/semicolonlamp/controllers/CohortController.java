package africa.semicolon.semicolonlamp.controllers;


import africa.semicolon.semicolonlamp.request.CohortCreateRequest;
import africa.semicolon.semicolonlamp.services.CohortService;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortException;
import africa.semicolon.semicolonlamp.services.lampExceptions.EmptyFieldException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/cohort")
@Slf4j
public class CohortController {
    @Autowired
    CohortService cohortService;

    @PatchMapping("name/{cohortId}/")
    public ResponseEntity<?> changeCohortName(@PathVariable String cohortId, @RequestParam String newName) {
        try {
            cohortService.changeCohortName(cohortId, newName);
            var mess = "Your cohort's name has been changed to " + newName + " successfully!";
            return new ResponseEntity<>(mess, HttpStatus.CREATED);
        } catch (CohortException message) {
            var display = message.getMessage();
            return new ResponseEntity<>(display, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("status/{cohortId}")
    public ResponseEntity<?> updateCohortStatus(@PathVariable String cohortId, @RequestParam String newStatus) {
        try {
            cohortService.updateCohortStatus(cohortId, newStatus);
            var mess = "Your cohort's status has been updated to " + newStatus + " successfully!";
            return new ResponseEntity<>(mess, HttpStatus.CREATED);
        } catch (CohortException message) {
            var display = message.getMessage();
            return new ResponseEntity<>(display, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/in-session")
    public ResponseEntity<?> getAllInSession() {
        var session = cohortService.getAllInSessionCohorts();
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @DeleteMapping("/{cohortId}")
    public ResponseEntity<?> deleteCohort(@PathVariable String cohortId) {
        try {
            cohortService.deleteCohort(cohortId);
            var display = "Cohort with id number " + cohortId + " has been deleted successfully!";
            return new ResponseEntity<>(display, HttpStatus.OK);
        } catch (CohortException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/completed")
    public ResponseEntity<?> getAllCompleted() {
        var completed = cohortService.getAllCompletedCohort();
        return new ResponseEntity<>(completed, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getAllPending() {
        var pending = cohortService.getAllPendingCohort();
        return new ResponseEntity<>(pending, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createCohort(@RequestBody CohortCreateRequest request) {
        try {
            cohortService.createCohort(request);
            var display = "Your cohort has been created successfully!";
            return new ResponseEntity<>(display, HttpStatus.CREATED);
        } catch (EmptyFieldException | CohortException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{cohortId}")
    public ResponseEntity<?> getCohort(@PathVariable String cohortId) {
        try {
            var cohort = cohortService.getCohort(cohortId);
            return new ResponseEntity<>(cohort, HttpStatus.OK);
        } catch (CohortException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCohorts() {
        var allCohorts = cohortService.getAllCohorts();
        return new ResponseEntity<>(allCohorts, HttpStatus.OK);
    }
}
