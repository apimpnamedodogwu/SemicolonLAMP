package africa.semicolon.semicolonlamp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Id
    private String id;
    private UserType userType;
    private Cohort cohort;
    private Address address;
}
