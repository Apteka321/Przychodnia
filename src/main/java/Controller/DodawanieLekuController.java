package Controller;

import com.jfoenix.controls.JFXTextField;

import View.Komunikaty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Leki;
import model.repository.LekiRepository;
import model.repository.impl.LekiRepositoryImpl;

public class DodawanieLekuController {
	LekiRepository lekiRepository;

	@FXML
	public void initialize() {
		lekiRepository = new LekiRepositoryImpl();
	}

	// metoda wywo³ywana po klikniêciu w button
	@FXML
	void dodajLek(MouseEvent event) {
		if (nazwa.getText().length() > 1 && producent.getText().length() > 1 && zawartosc.getText().length() > 1) {
			Leki lek = wczytajLekZFormularza();
			// jesli pomyslnie dodano lek zamknij okno
			if (lekiRepository.dodajLek(lek)) {
				wyczyscFormularz();
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
		Leki lek = new Leki();
		lek.setNazwa(nazwa.getText());
		lek.setProducent(producent.getText());
		lek.setIlosc(zawartosc.getText());
		return lek;
	}

	@FXML
	private JFXTextField nazwa;

	@FXML
	private JFXTextField producent;

	@FXML
	private JFXTextField zawartosc;

}
