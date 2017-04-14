package View;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
	public static boolean wyswietlPotwierdzenie(String naglowek, String tresc, String pytanie) {
		boolean potwierdzenie = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(naglowek);
		alert.setHeaderText(tresc);
		alert.setContentText(pytanie);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			potwierdzenie = true;
		} else {
			potwierdzenie = false;
		}
		return potwierdzenie;
	}
}
