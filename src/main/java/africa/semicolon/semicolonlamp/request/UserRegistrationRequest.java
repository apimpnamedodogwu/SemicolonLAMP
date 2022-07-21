package africa.semicolon.semicolonlamp.request;

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

}
