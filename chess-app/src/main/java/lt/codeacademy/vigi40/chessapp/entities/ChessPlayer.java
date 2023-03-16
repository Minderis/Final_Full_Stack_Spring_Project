package lt.codeacademy.vigi40.chessapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ChessPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Long personCode;
    private LocalDate startPlayChessFromDate;

    public ChessPlayer(String name, String surname, String email, Long personCode, LocalDate startPlayChessFromDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.personCode = personCode;
        this.startPlayChessFromDate = startPlayChessFromDate;
    }
}
