package dto;

import enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Profile {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDate created_date;
    private String status;
    private ProfileRole role;
}
