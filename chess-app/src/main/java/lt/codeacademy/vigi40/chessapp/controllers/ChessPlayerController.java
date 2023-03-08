package lt.codeacademy.vigi40.chessapp.controllers;

import lt.codeacademy.vigi40.chessapp.converters.ChessPlayerConverter;
import lt.codeacademy.vigi40.chessapp.dto.AddChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.dto.ChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.dto.EditChessPlayerDTO;
import lt.codeacademy.vigi40.chessapp.servicies.ChessPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chess_player")
public class ChessPlayerController {
    @Autowired
    private ChessPlayerService chessPlayerService;

    @GetMapping
    public List<ChessPlayerDTO> getAllChessPlayers() {
        return ChessPlayerConverter.convertChessPLayerEntityListToDto(this.chessPlayerService.getAllChessPlayers());
    }

    @PostMapping
    public void addChessPlayer(@RequestBody AddChessPlayerDTO addChessPlayerDTO){
        this.chessPlayerService.addChessPlayer(ChessPlayerConverter.convertAddChessPlayerDtoToEntity(addChessPlayerDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteChessPlayerById(@PathVariable Long id){
        this.chessPlayerService.deleteChessPlayerById(id);
    }

    @PatchMapping("/{id}")
    public void editChessPlayerById(@PathVariable Long id, @RequestBody AddChessPlayerDTO addChessPlayerDTO) {
        this.chessPlayerService.editChessPlayerById(id, ChessPlayerConverter.convertAddChessPlayerDtoToEntity(addChessPlayerDTO));
    }

    @GetMapping("/{id}")
    public EditChessPlayerDTO getChessPlayerById(@PathVariable Long id) {
        return ChessPlayerConverter.convertChessPlayerEntityToEditDto(this.chessPlayerService.getChessPlayerById(id));
    }
}
