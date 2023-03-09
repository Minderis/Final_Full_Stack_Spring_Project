package lt.codeacademy.vigi40.chessapp.controllers;

import lt.codeacademy.vigi40.chessapp.converters.ChessPlayerConverter;
import lt.codeacademy.vigi40.chessapp.dto.AddChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.dto.ChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.entities.ChessPlayer;
import lt.codeacademy.vigi40.chessapp.servicies.ChessPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chess_player")
public class ChessPlayerController {
    @Autowired
    private ChessPlayerService chessPlayerService;

    @GetMapping
    public ResponseEntity<List<ChessPlayerDTO>> getAllChessPlayersPageable(Pageable pageable) {
        Page<ChessPlayer> chessPlayers = chessPlayerService.getAllChessPlayersPageable(pageable);
        List<ChessPlayerDTO> chessPlayerDTOs = ChessPlayerConverter.convertChessPLayerEntityListToDto(chessPlayers.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(chessPlayers.getTotalElements()));
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return ResponseEntity.ok().headers(headers).body(chessPlayerDTOs);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getChessPlayerById(@PathVariable Long id) {
        ChessPlayer player = this.chessPlayerService.getChessPlayerById(id);
        if (player == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chess player with id: " + id + " was not found");
        }
        return ResponseEntity.ok(ChessPlayerConverter.convertChessPlayerEntityToEditDto(player));
    }

    @PostMapping
    public void addChessPlayer(@RequestBody AddChessPlayerDTO addChessPlayerDTO){
        this.chessPlayerService.addChessPlayer(ChessPlayerConverter.convertAddChessPlayerDtoToEntity(addChessPlayerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChessPlayerById(@PathVariable Long id){
        ChessPlayer player = this.chessPlayerService.getChessPlayerById(id);
        if (player == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Chess player with id: " + id + " was not found");
        } else {
            this.chessPlayerService.deleteChessPlayerById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Chess player with id: " + id + " was deleted successfully");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editChessPlayerById(@PathVariable Long id,
                                                 @RequestBody AddChessPlayerDTO addChessPlayerDTO) {
        ChessPlayer player = this.chessPlayerService.getChessPlayerById(id);
        if (player == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Chess player with id: " + id + " was not found");
        } else {
            this.chessPlayerService
                    .editChessPlayerById(id, ChessPlayerConverter.convertAddChessPlayerDtoToEntity(addChessPlayerDTO));
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Chess player with id: " + id + " was modified successfully");
        }
    }
}

