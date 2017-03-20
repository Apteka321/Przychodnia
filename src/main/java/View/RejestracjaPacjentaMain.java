package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RejestracjaPacjentaMain extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/rejestracjaPacjenta.fxml"));
		Pane Pane = loader.load();
		Scene scene = new Scene(Pane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Rejestracja Pacjenta");
		primaryStage.show();
	}

}
