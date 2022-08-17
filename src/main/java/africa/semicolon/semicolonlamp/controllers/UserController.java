package africa.semicolon.semicolonlamp.controllers;

import africa.semicolon.semicolonlamp.request.UserRegistrationRequest;
import africa.semicolon.semicolonlamp.request.UserUpdateRequest;
import africa.semicolon.semicolonlamp.services.UserService;
import africa.semicolon.semicolonlamp.services.lampExceptions.CohortException;
import africa.semicolon.semicolonlamp.services.lampExceptions.ExistingEmailException;
import africa.semicolon.semicolonlamp.services.lampExceptions.InvalidUserIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PatchMapping("/change-user-type/{userId}/new-type")
    public ResponseEntity<?> changeUserType(@PathVariable String userId, @RequestParam String newType) {
        try {
            userService.changeUserType(userId, newType);
            var display = "User with id number " + userId + " has been changed to " + newType + " successfully!";
            return new ResponseEntity<>(display, HttpStatus.OK);
        } catch (InvalidUserIdException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update-user-details/{userId}/request")
    public ResponseEntity<?> updateUserDetails(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        try {
            userService.updateUserDetails(userId, request);
            var display = "User with id number " + userId + " has been updated successfully!";
            return new ResponseEntity<>(display, HttpStatus.OK);
        } catch (ExistingEmailException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-elders")
    public ResponseEntity<?> getAllElders() {
        var elders = userService.getAllElders();
        return new ResponseEntity<>(elders, HttpStatus.OK);
    }

    @GetMapping("/get-all-ancestors")
    public ResponseEntity<?> getAllAncestors() {
        var ancestors = userService.getAllAncestors();
        return new ResponseEntity<>(ancestors, HttpStatus.OK);
    }

    @GetMapping("/get-all-natives")
    public ResponseEntity<?> getAllNatives() {
        var natives = userService.getAllNatives();
        return new ResponseEntity<>(natives, HttpStatus.OK);
    }

    @GetMapping("/get-all-natives-in-a-cohort/{cohortId}")
    public ResponseEntity<?> getAllNativesInACohort(@PathVariable String cohortId) {
        try {
            var nativesInACohort = userService.getAllNativesInACohort(cohortId);
            return new ResponseEntity<>(nativesInACohort, HttpStatus.OK);
        } catch (CohortException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-a-user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        try {
            var user = userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InvalidUserIdException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        var users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create-a-native/request")
    public ResponseEntity<?> createANative(@RequestBody UserRegistrationRequest request) {
        try {
            userService.createUserNative(request);
            var display = "Congratulations, welcome to hell!";
            return new ResponseEntity<>(display, HttpStatus.CREATED);
        } catch (ExistingEmailException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-an-ancestor/request")
    public ResponseEntity<?> createAnAncestor(@RequestBody UserRegistrationRequest request) {
        try {
            userService.createUserAncestor(request);
            var display = "Congratulations, you made it through hell and back. You are now an Ancestor!";
            return new ResponseEntity<>(display, HttpStatus.CREATED);
        } catch (ExistingEmailException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-an-elder/request")
    public ResponseEntity<?> createAnElder(@RequestBody UserRegistrationRequest request) {
        try {
            userService.createUserElder(request);
            var display = "Welcome back to another form of hell loop!";
            return new ResponseEntity<>(display, HttpStatus.CREATED);
        } catch (ExistingEmailException message) {
            var displayMessage = message.getMessage();
            return new ResponseEntity<>(displayMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
