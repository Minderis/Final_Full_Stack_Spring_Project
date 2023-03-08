package lt.codeacademy.vigi40.chessapp;

import lt.codeacademy.vigi40.chessapp.servicies.ChessPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ChessApplication {

	@Autowired
	private ChessPlayerService chessPlayerService;

	public static void main(String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadTestDataForTesting() {
		this.chessPlayerService.loadTestData();
	}

}
