package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Card {
    private String number;
    private String exp_date;
    private Integer balance;
    private String status;
    private String phone;
    private LocalDate created_date;
}
