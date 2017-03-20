package View;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class Komunikaty {

	public static void wyswietlOstrzezenie(String naglowek, String tresc) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ostrze¿enie");
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
