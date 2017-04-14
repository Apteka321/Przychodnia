package Controller;

import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import View.Komunikaty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Konto;

import model.Osoba;
import model.Pacjent;



import model.repository.impl.MetodyRecepcjonistkaImpl;

public class ModyfikacjaPacjentaController {
	
	private Pacjent wybranyPacjent = null;

	private List<Pacjent> listaPacjentpw;
	
	
	@FXML
	public void initialize() {
		MetodyRecepcjonistkaImpl metodyRecepcjonistkaImpl = new MetodyRecepcjonistkaImpl();
		listaPacjentpw = metodyRecepcjonistkaImpl.wypiszListePacjentow();
		
		TreeItem<String> wezelPacjent = new TreeItem<String>("Pacjent");
		for (Pacjent pacjent : listaPacjentpw) {
			TreeItem<String> pacjentLisc = new TreeItem<String>(
					pacjent.getOsoba().getImie() + " " + pacjent.getOsoba().getNazwisko());
			wezelPacjent.getChildren().add(pacjentLisc);
		}
		wezelPacjent.setExpanded(true);
		


		listaPacjentowTreeView.setRoot(wezelPacjent);
		listaPacjentowTreeView.setShowRoot(false);
	
	}
    @FXML
    void aktualizacjaPacjenta(ActionEvent event) {
    	boolean zatwierdzAktualizacje = false;
		zatwierdzAktualizacje = Komunikaty.wyswietlPotwierdzenie("Potwierdzenie", "Aktualizowanie danych pracownika",
				"Na pewno chcesz zapisaæ zmiany?");
		if (zatwierdzAktualizacje) {
			MetodyRecepcjonistkaImpl metodyRecepcjonistkaImpl = new MetodyRecepcjonistkaImpl();
			Pacjent pacjent = wczytajDanePacjentaDoObiektu();
			metodyRecepcjonistkaImpl.aktualizujPacjenta(pacjent);
			initialize();
		}

    }
	private Pacjent wczytajDanePacjentaDoObiektu() {
		Pacjent pacjent = wybranyPacjent;

		Konto konto = new Konto();
		konto.setID(wybranyPacjent.getKonto().getID());
		konto.setTyp_konta(wybranyPacjent.getKonto().getTyp_konta());
		konto.setLogin(login.getText());
		konto.setHaslo(haslo.getText());
		pacjent.setKonto(konto);

		Osoba osoba = new Osoba();
		osoba.setID(pacjent.getOsoba().getID());
		osoba.setImie(imie.getText());
		osoba.setNazwisko(nazwisko.getText());
		osoba.setMiejscowosc(miejscowosc.getText());
		osoba.setNumer_domu(Integer.parseInt(numerDomu.getText()));
		osoba.setUlica(ulica.getText());
		osoba.setKod_pocztowy(kodPocztowy.getText());
		osoba.setPoczta(poczta.getText());
		osoba.setNumer_telefonu(numerTelefonu.getText());
		pacjent.setOsoba(osoba);

		return pacjent;
	}

    @FXML
	void mouseClick(MouseEvent event) {

		TreeItem<String> item = listaPacjentowTreeView.getSelectionModel().getSelectedItem();
		// sprawdza czy klikniêto na pracownika
		if (item.getValue().toString().contains(" ")) {
			String imiePracownika = item.getValue().split(" ")[0];
			String nazwiskoPracownika = item.getValue().split(" ")[1];

			wybranyPacjent = null;

			// wyszukuje wybranego pracownika
			for (Pacjent pacjent : listaPacjentpw) {
				if (imiePracownika.equals(pacjent.getOsoba().getImie())
						&& nazwiskoPracownika.equals(pacjent.getOsoba().getNazwisko())) {
					wybranyPacjent = pacjent;

				}
			}
			if (wybranyPacjent != null) {
				wczytajDanePracownikaDoFormularza();
				
			} 
		}
	}
    private void wczytajDanePracownikaDoFormularza() {

		login.setText(wybranyPacjent.getKonto().getLogin());
		haslo.setText(wybranyPacjent.getKonto().getHaslo());
		imie.setText(wybranyPacjent.getOsoba().getImie());
		nazwisko.setText(wybranyPacjent.getOsoba().getNazwisko());
		miejscowosc.setText(wybranyPacjent.getOsoba().getMiejscowosc());
		numerDomu.setText(Integer.toString(wybranyPacjent.getOsoba().getNumer_domu()));
		ulica.setText(wybranyPacjent.getOsoba().getUlica());
		kodPocztowy.setText(wybranyPacjent.getOsoba().getKod_pocztowy());
		poczta.setText(wybranyPacjent.getOsoba().getPoczta());
		numerTelefonu.setText(wybranyPacjent.getOsoba().getNumer_telefonu());
	}

    @FXML
    private JFXTreeView<String> listaPacjentowTreeView;

    @FXML
    private Pane formularzPacjentow;

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
    private JFXButton zaktualizujPacjenta;



}
