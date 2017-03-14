package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RejestracjaPracownikowMain extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/rejestracjaPracownikow.fxml"));
		Pane Pane = loader.load();
		Scene scene = new Scene(Pane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Rejestracja pracownika");
		primaryStage.show();
	}

	public static void wyswietlOstrzezenie(String naglowek, String tresc) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ostrzeżenie");
		alert.setHeaderText(naglowek);
		alert.setContentText(tresc);
		alert.initStyle(StageStyle.UTILITY);

		alert.showAndWait();
	}

	public static void wyswietlInformacje(String naglowek, String tresc) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacja");
		alert.setHeaderText(naglowek);
		alert.setContentText(tresc);
		alert.initStyle(StageStyle.UTILITY);

		alert.showAndWait();
	}

}
