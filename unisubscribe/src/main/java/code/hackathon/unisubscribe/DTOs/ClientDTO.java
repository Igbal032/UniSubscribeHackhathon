package code.hackathon.unisubscribe.DTOs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class ClientDTO {

    private long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate createdDate;
}
