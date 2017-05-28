package Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXTextField;

import View.Komunikaty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Leki;
import model.repository.LekiRepository;
import model.repository.impl.LekiRepositoryImpl;

public class ModyfikowanieLekuController {
	private LekiRepository lekiRepository;
	private Leki modyfikowanyLek;

	@FXML
	public void initialize() {
		lekiRepository = new LekiRepositoryImpl();
	}

	// metoda wywo³ywana po klikniêciu w button
	@FXML
	void modyfikujLek(MouseEvent event) throws IOException {
		if (nazwa.getText().length() > 1 && producent.getText().length() > 1 && zawartosc.getText().length() > 1) {
			modyfikowanyLek = wczytajLekZFormularza();
			// jesli pomyslnie dodano lek zamknij okno
			if (lekiRepository.modyfikujLek(modyfikowanyLek)) {
				wyczyscFormularz();
				FXMLLoader fxmlLoader = new FXMLLoader();
				Parent root = fxmlLoader.load(this.getClass().getResource("/fxml/zarzadzanieLekami.fxml").openStream());
				ZarzadzanieLekamiController zarzadzanieLekamiController = (ZarzadzanieLekamiController)fxmlLoader.getController();
				zarzadzanieLekamiController.odswiezTabele();
				Stage stage = (Stage) nazwa.getScene().getWindow();
				stage.close();
			}

		} else {
			Komunikaty.wyswietlOstrzezenie("B³¹d!", "Uzupe³nij wszystkie pola!");
		}
	}

	private void wyczyscFormularz() {
		nazwa.setText("");
		producent.setText("");
		zawartosc.setText("");

	}

	private Leki wczytajLekZFormularza() {
		Leki lek = modyfikowanyLek;
		lek.setID(modyfikowanyLek.getID());
		lek.setNazwa(nazwa.getText());
		lek.setProducent(producent.getText());
		lek.setIlosc(zawartosc.getText());
		return lek;
	}

	public void wczytajLekDoFormularza(Leki modyfikowanyLek) {
		this.modyfikowanyLek = modyfikowanyLek;
		nazwa.setText(modyfikowanyLek.getNazwa());
		producent.setText(modyfikowanyLek.getProducent());
		zawartosc.setText(modyfikowanyLek.getIlosc());
	}

	@FXML
	private JFXTextField nazwa;

	@FXML
	private JFXTextField producent;

	@FXML
	private JFXTextField zawartosc;

}
