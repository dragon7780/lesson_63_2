package dto;


import enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Transaction {
    private String card_number;
    private Integer amount;
    private String terminal_code;
    private TransactionType type;
    private LocalDate created_date;
}
