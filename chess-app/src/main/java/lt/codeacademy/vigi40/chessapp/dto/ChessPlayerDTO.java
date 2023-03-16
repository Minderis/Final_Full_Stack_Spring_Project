package lt.codeacademy.vigi40.chessapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChessPlayerDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String sex;
    private String age;
    private String duration;
}
