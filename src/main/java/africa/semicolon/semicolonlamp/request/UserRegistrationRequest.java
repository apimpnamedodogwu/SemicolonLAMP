package africa.semicolon.semicolonlamp.request;

import africa.semicolon.semicolonlamp.services.lampExceptions.EmptyFieldException;
import africa.semicolon.semicolonlamp.services.lampExceptions.InvalidPasswordException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public boolean valPassword(String password) {
        return password.length() == 8;
    }

    public void validateRegistrationRequest(UserRegistrationRequest request) {
        if (request.getFirstName().isEmpty()) {
            throw new EmptyFieldException("First name field cannot be empty!");
        }

        if (request.getLastName().isEmpty()) {
            throw new EmptyFieldException("Last name field cannot be empty!");
        }

        if (request.getEmail().isEmpty()) {
            throw new EmptyFieldException("Email field cannot be empty!");
        }

        if (!valPassword(request.getPassword())) {
            throw new InvalidPasswordException(request.getPassword() + " is not up to eight digits!");
        }
    }
}
