package lt.codeacademy.vigi40.chessapp.repositories;

import lt.codeacademy.vigi40.chessapp.entities.ChessPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {
}
