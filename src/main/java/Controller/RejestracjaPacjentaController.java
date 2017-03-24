package Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.Konto;
import model.Osoba;
import model.Pacjent;

import model.repository.KontoRepository;

import model.repository.impl.KontoRepositoryImpl;

public class RejestracjaPacjentaController {
KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
	@FXML
	void zarejestrujPacjenta(ActionEvent event) throws IOException {
		Pacjent pacjent = pobierzDaneZFormularzaDoPacjenta();
		dodajPacjentaDoBazy(pacjent);
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/RejestracjaPacjentaNaWizyte.fxml","Logowanie");
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	private Pacjent pobierzDaneZFormularzaDoPacjenta() {
		Pacjent pacjent = new Pacjent();
		pacjent.setPESEL(pesel.getText());

		Konto konto = new Konto();
		konto.setHaslo(haslo.getText());
		konto.setLogin(login.getText());

		Osoba osoba = new Osoba();
		osoba.setImie(imie.getText());
		osoba.setNazwisko(nazwisko.getText());
		osoba.setNazwisko(nazwisko.getText());
		osoba.setMiejscowosc(miejscowosc.getText());
		osoba.setNumer_domu(Integer.parseInt(numerDomu.getText()));
		if (ulica.getText().length() == 0) {
			osoba.setUlica(ulica.getText());
		} else {
			osoba.setUlica("");
		}

		osoba.setKod_pocztowy(kodPocztowy.getText());
		osoba.setPoczta(poczta.getText());
		osoba.setNumer_telefonu(numerTelefonu.getText());

		pacjent.setOsoba(osoba);
		return pacjent;
	}

	private void dodajPacjentaDoBazy(Pacjent pacjent) {
		KontoRepository kontaRepository = new KontoRepositoryImpl();
		kontaRepository.dodajKontoPacjenta(pacjent);
	}

	@FXML
	private Pane rejestracjaPacjentaPane;

	@FXML
	private JFXTextField pesel;

	@FXML
	private JFXTextField login;

	@FXML
	private JFXPasswordField haslo;

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
	private JFXButton zarejestrujButton;

}
