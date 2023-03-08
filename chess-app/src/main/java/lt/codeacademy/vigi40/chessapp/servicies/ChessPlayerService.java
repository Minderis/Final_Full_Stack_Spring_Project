package lt.codeacademy.vigi40.chessapp.servicies;

import lt.codeacademy.vigi40.chessapp.entities.ChessPlayer;
import lt.codeacademy.vigi40.chessapp.repositories.ChessPlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChessPlayerService {
    private final ChessPlayerRepository chessPlayerRepository;

    public ChessPlayerService(ChessPlayerRepository chessPlayerRepository) {
        this.chessPlayerRepository = chessPlayerRepository;
    }

    public void addChessPlayer(ChessPlayer chessPlayer) {
        this.chessPlayerRepository.saveAndFlush(chessPlayer);
    }

    public List<ChessPlayer> getAllChessPlayers() {
        return chessPlayerRepository.findAll();
    }

//    public void replaceChessPlayerById(Long id, ChessPlayer chessPlayer) {
//        if (!chessPlayerRepository.existsById(id)) {
//            return;
//        }
//        chessPlayer.setId(id);
//        this.chessPlayerRepository.saveAndFlush(chessPlayer);
//    }

    public void editChessPlayerById(Long id, ChessPlayer chessPlayer) {
        Optional<ChessPlayer> chessPlayerToEditOptional = chessPlayerRepository.findById(id);
        if (!chessPlayerToEditOptional.isPresent()) {
            return;
        }
        ChessPlayer chessPlayerToEdit = chessPlayerToEditOptional.get();
        if (chessPlayer.getName() != null && !chessPlayerToEdit.getName().equals(chessPlayer.getName())){
            chessPlayerToEdit.setName(chessPlayer.getName());
        }
        if (chessPlayer.getSurname() != null && !chessPlayerToEdit.getSurname().equals(chessPlayer.getSurname())){
            chessPlayerToEdit.setSurname(chessPlayer.getSurname());
        }
        if (chessPlayer.getEmail() != null && !chessPlayerToEdit.getEmail().equals(chessPlayer.getEmail())){
            chessPlayerToEdit.setEmail(chessPlayer.getEmail());
        }
        if (chessPlayer.getPersonCode() != null
                && !chessPlayerToEdit.getPersonCode().equals(chessPlayer.getPersonCode())){
            chessPlayerToEdit.setPersonCode(chessPlayer.getPersonCode());
        }
        if (chessPlayer.getStartPlayChessFromDate() != null
                && !chessPlayerToEdit.getStartPlayChessFromDate().equals(chessPlayer.getStartPlayChessFromDate())){
            chessPlayerToEdit.setStartPlayChessFromDate(chessPlayer.getStartPlayChessFromDate());
        }
        chessPlayerRepository.saveAndFlush(chessPlayerToEdit);
    }

    public ChessPlayer getChessPlayerById(Long id) {
        Optional<ChessPlayer> chessPlayer = chessPlayerRepository.findById(id);
        return chessPlayer.orElse(null);
    }

    public void deleteChessPlayerById(Long id) {
        this.chessPlayerRepository.deleteById(id);
    }

    public void loadTestData() {
        addChessPlayers();
    }

    private void addChessPlayers() {
        ArrayList<ChessPlayer> chessPlayers = new ArrayList<>();
        // invalid data (not leap year set in person code, so 29 day is not valid)
        chessPlayers.add(new ChessPlayer("John", "Smith", "john.smith@gmail.com",
                37902294100L, LocalDate.of(2000, 12, 31)));
        chessPlayers.add(new ChessPlayer("Emma", "Davis", "emma.davis@gmail.com",
                47802022355L, LocalDate.of(1999, 11, 12)));
        // invalid data (feature year set in person code, so person is not born yet)
        chessPlayers.add(new ChessPlayer("William", "Jones", "william.jones@gmail.com",
                53103312087L, LocalDate.of(2022, 3, 6)));
        chessPlayers.add(new ChessPlayer("Sophia", "Wilson", "sophia.wilson@gmail.com",
                60011184154L, LocalDate.of(2012, 9, 18)));
        // invalid data (not existing first digit is set in person code)
        chessPlayers.add(new ChessPlayer("Oliver", "Martin", "oliver.martin@gmail.com",
                78911172129L, LocalDate.of(2003, 5, 22)));
        chessPlayers.add(new ChessPlayer("Isabella", "Garcia", "isabella.garcia@gmail.com",
                49402165116L, LocalDate.of(2002, 2, 9)));
        // invalid data (start playing chess date is set later than today date)
        chessPlayers.add(new ChessPlayer("James", "Brown", "james.brown@gmail.com",
                39109102113L, LocalDate.of(2023, 8, 15)));
        chessPlayers.add(new ChessPlayer("Ava", "Taylor", "ava.taylor@gmail.com",
                47712091105L, LocalDate.of(1997, 12, 22)));
        // invalid data (invalid email)
        chessPlayers.add(new ChessPlayer("Mason", "Lee", "mason.lee@gmailcom",
                38403111103L, LocalDate.of(2005, 6, 30)));
        chessPlayers.add(new ChessPlayer("Emily", "Smith", "emily.smith@gmail.com",
                48504136129L, LocalDate.of(2006, 3, 7)));
        chessPlayers.add(new ChessPlayer("Ethan", "Wilson", "ethan.wilson@gmail.com",
                50205023020L, LocalDate.of(2014, 10, 14)));
        chessPlayers.add(new ChessPlayer("Mia", "Martin", "mia.martin@gmail.com",
                60306210153L, LocalDate.of(2018, 7, 12)));
        chessPlayers.add(new ChessPlayer("Noah", "Jones", "noah.jones@gmail.com",
                48607230113L, LocalDate.of(1996, 8, 28)));
        chessPlayers.add(new ChessPlayer("Charlotte", "Davis", "charlotte.davis@gmail.com",
                46008308154L, LocalDate.of(1976, 4, 21)));
        chessPlayers.add(new ChessPlayer("Liam", "Wilson", "liam.wilson@gmail.com",
                37609084023L, LocalDate.of(1997, 11, 2)));
        chessPlayers.add(new ChessPlayer("Amelia", "Brown", "amelia.brown@gmail.com",
                49710202109L, LocalDate.of(2009, 1, 25)));
        chessPlayers.add(new ChessPlayer("Logan", "Taylor", "logan.taylor@gmail.com",
                38011294011L, LocalDate.of(1998, 7, 12)));
        this.chessPlayerRepository.saveAllAndFlush(chessPlayers);
    }
}
