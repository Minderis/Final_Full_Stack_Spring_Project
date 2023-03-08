package lt.codeacademy.vigi40.chessapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class EditChessPlayerDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String personCode;
    private LocalDate startPlayChessFromDate;
}
