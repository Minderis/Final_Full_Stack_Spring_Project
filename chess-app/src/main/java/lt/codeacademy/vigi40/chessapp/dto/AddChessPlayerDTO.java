package lt.codeacademy.vigi40.chessapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AddChessPlayerDTO {
    private String name;
    private String surname;
    private String email;
    private Long personCode;
    private LocalDate startPlayChessFromDate;
}
