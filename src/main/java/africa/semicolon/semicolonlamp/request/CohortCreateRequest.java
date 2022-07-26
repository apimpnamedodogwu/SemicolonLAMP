package africa.semicolon.semicolonlamp.request;

import africa.semicolon.semicolonlamp.services.lampExceptions.EmptyFieldException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CohortCreateRequest {
    private String name;
    private LocalDate localDate;


}
