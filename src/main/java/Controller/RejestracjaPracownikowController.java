package Controller;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import View.RejestracjaPracownikowMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import model.Konto;
import model.Lekarz;
import model.Osoba;
import model.Pielegniarka;
import model.Pracownik;
import model.Recepcjonistka;
import model.repository.impl.KontaRepositoryImpl;

public class RejestracjaPracownikowController {
	private int rejestrowaneKonto = 2;

	@FXML
	public void initialize() {
		rejestracjaPracownikaPane.setVisible(true);
		rejestracjaAdministratoraPane.setVisible(false);

		final List<String> dostepneTypyKont = new ArrayList<String>();
		dostepneTypyKont.add("Administrator");
		dostepneTypyKont.add("Lekarza");
		dostepneTypyKont.add("Pielêgniarke");
		dostepneTypyKont.add("Recepcjonistka");

		typKonta.getItems().addAll(dostepneTypyKont);
		typKonta.getSelectionModel().select(1);
		typKonta.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int wybranyTypRejestrowanegoKonta;
				if (newValue == null) {
					// pierwsze klikniêcie
					wybranyTypRejestrowanegoKonta = (Integer) oldValue;
				} else {
					wybranyTypRejestrowanegoKonta = (Integer) newValue;
				}

				rejestrowaneKonto = wybranyTypRejestrowanegoKonta + 1;

				switch (wybranyTypRejestrowanegoKonta) {
				case 0:
					wyswietlFormularzRejestracyjnyAdministratora();
					break;

				case 1:
					wyswietlFormularzRejestracyjnyPracownika();
					break;

				case 2:
					wyswietlFormularzRejestracyjnyPracownika();
					break;

				case 3:
					wyswietlFormularzRejestracyjnyPracownika();
					break;
				}
			}

			private void wyswietlFormularzRejestracyjnyPracownika() {
				rejestracjaPracownikaPane.setVisible(true);
				rejestracjaAdministratoraPane.setVisible(false);
			}

			private void wyswietlFormularzRejestracyjnyAdministratora() {
				rejestracjaPracownikaPane.setVisible(false);
				rejestracjaAdministratoraPane.setVisible(true);
			}
		});
	}

	Pracownik wczytajDaneZFormularzaDoPracownika() {
		Pracownik pracownik = null;
		switch (rejestrowaneKonto) {
		case 2:
			pracownik = new Lekarz();
			break;
		case 3:
			pracownik = new Pielegniarka();
			break;
		case 4:
			pracownik = new Recepcjonistka();
			break;
		}

		Konto konto = new Konto();
		konto.setLogin(login1.getText());
		konto.setHaslo(haslo.getText());
		konto.setTyp_konta(rejestrowaneKonto);

		Osoba osoba = new Osoba();
		osoba.setImie(imie.getText());
		osoba.setNazwisko(nazwisko.getText());
		osoba.setMiejscowosc(miejscowosc.getText());
		osoba.setNumer_domu(Integer.parseInt(numerDomu.getText()));
		osoba.setUlica(ulica.getText());
		osoba.setKod_pocztowy(kodPocztowy.getText());
		osoba.setPoczta(poczta.getText());
		osoba.setNumer_telefonu(numerTelefonu.getText());

		pracownik.setOsoba(osoba);
		pracownik.setKonto(konto);

		return pracownik;
	}

	@FXML
	void rejestracjaAdministratora() {
		KontaRepositoryImpl kontaRepository = new KontaRepositoryImpl();
		if (haslo1.getText().equals(haslo2.getText())) {
			Konto kontoAdministratora = new Konto();
			kontoAdministratora.setLogin(login.getText());
			kontoAdministratora.setHaslo(haslo1.getText());
			kontoAdministratora.setTyp_konta(rejestrowaneKonto);

			kontaRepository.dodajKontoAdministratora(kontoAdministratora);
		} else {
			KontaRepositoryImpl.wyswietlOstrzezenie("Has³a", "Has³a musz¹ byæ takie same!");
		}
	}

	@FXML
	void rejestracjaPracownika() {
		KontaRepositoryImpl kontaRepository = new KontaRepositoryImpl();
		switch (rejestrowaneKonto) {
		case 2:
			Lekarz lekarz = (Lekarz) wczytajDaneZFormularzaDoPracownika();
			kontaRepository.dodajKontoLekarza(lekarz);
			break;

		case 3:
			Pielegniarka pielegniarka = (Pielegniarka) wczytajDaneZFormularzaDoPracownika();
			kontaRepository.dodajKontoPielegniarki(pielegniarka);
			break;

		case 4:
			Recepcjonistka recepcjonistka = (Recepcjonistka) wczytajDaneZFormularzaDoPracownika();
			kontaRepository.dodajKontoRecepcjonistki(recepcjonistka);
			break;

		default:
			break;
		}
	}

	@FXML
	private ChoiceBox<String> typKonta;

	@FXML
	private Pane rejestracjaPracownikaPane;

	@FXML
	private JFXTextField login1;

	@FXML
	private JFXTextField imie;

	@FXML
	private JFXTextField nazwisko;

	@FXML
	private JFXTextField miejscowosc;

	@FXML
	private JFXTextField numerDomu;

	@FXML
	private JFXTextField ulica;

	@FXML
	private JFXTextField kodPocztowy;

	@FXML
	private JFXTextField poczta;

	@FXML
	private JFXTextField numerTelefonu;

	@FXML
	private JFXButton zarejestrujPracownika;

	@FXML
	private JFXPasswordField haslo;

	@FXML
	private Pane rejestracjaAdministratoraPane;

	@FXML
	private JFXTextField login;

	@FXML
	private JFXButton zarejestrujAdministratora;

	@FXML
	private JFXPasswordField haslo1;

	@FXML
	private JFXPasswordField haslo2;

}
