package africa.semicolon.semicolonlamp.request;

import africa.semicolon.semicolonlamp.services.lampExceptions.EmptyFieldException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;

    public void validateUserUpdateRequest(UserUpdateRequest request) {
        if (request.getEmail().isEmpty()) {
            throw new EmptyFieldException("Email field cannot be empty!");
        }

        if (request.getFirstName().isEmpty()) {
            throw new EmptyFieldException("First name field cannot be empty!");
        }

        if (request.getLastName().isEmpty()) {
            throw new EmptyFieldException("Last name field cannot be empty!");
        }
    }


}
