package View;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.repository.impl.KontoRepositoryImpl;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		KontoRepositoryImpl kontoRepository = new KontoRepositoryImpl();
		try {
		
	        kontoRepository.otwarcieNowejScenyZAdresu("/fxml/ObslugaPlatnosci.fxml","Aktualizacja wizytye");
	        
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
