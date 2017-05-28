package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DodajLekMain extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/dodajLek.fxml"));
		Pane Pane = loader.load();
		Scene scene = new Scene(Pane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Dodaj lek");
		primaryStage.show();
	}
}