package Controller;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import View.Komunikaty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Konto;
import model.Lekarz;
import model.Osoba;
import model.Pielegniarka;
import model.Plan_pracy;
import model.Pracownik;
import model.Recepcjonistka;
import model.Sala;
import model.repository.KontaRepository;
import model.repository.PlanPracyRepository;
import model.repository.SalaRepository;
import model.repository.impl.KontaRepositoryImpl;
import model.repository.impl.PlanPracyRepositoryImpl;
import model.repository.impl.SalaRepositoryImpl;

public class ModyfikacjaPracownikowController {
	private Pracownik wybranyPracownik = null;

	private List<Pracownik> listaPracownikow;
	private List<Lekarz> listaLekarzy;
	private List<Pielegniarka> listaPielegniarek;
	private List<Recepcjonistka> listaRecepcjonistek;

	@FXML
	public void initialize() {

		// ################################## INICJALIZACJA LISTY PRACOWNIKOW
		// pobranie list pracowników
		KontaRepository kontaRepository = new KontaRepositoryImpl();
		listaLekarzy = kontaRepository.getListaLekarzy();
		listaPielegniarek = kontaRepository.getListaPielegniarek();
		listaRecepcjonistek = kontaRepository.getListaRecepcjonistek();

		// stworzenie jednej listy z wszystkimi pracownikami
		listaPracownikow = new ArrayList<Pracownik>();
		listaPracownikow.addAll(listaLekarzy);
		listaPracownikow.addAll(listaPielegniarek);
		listaPracownikow.addAll(listaRecepcjonistek);

		// uzupe³nienie treeView imionami i nazwiskami pracowników
		TreeItem<String> wezelLekarzy = new TreeItem<String>("Lekarze");
		for (Lekarz lekarz : listaLekarzy) {
			TreeItem<String> lekarzLisc = new TreeItem<String>(
					lekarz.getOsoba().getImie() + " " + lekarz.getOsoba().getNazwisko());
			wezelLekarzy.getChildren().add(lekarzLisc);
		}

		TreeItem<String> wezelPielegniarek = new TreeItem<String>("Pielegniarki");
		for (Pielegniarka pielegniarka : listaPielegniarek) {
			TreeItem<String> pielegniarkaLisc = new TreeItem<String>(
					pielegniarka.getOsoba().getImie() + " " + pielegniarka.getOsoba().getNazwisko());
			wezelPielegniarek.getChildren().add(pielegniarkaLisc);
		}

		TreeItem<String> wezelRecepcjonistek = new TreeItem<String>("Recepcjonistki");
		for (Recepcjonistka recepcjonistka : listaRecepcjonistek) {
			TreeItem<String> recepcjonistkaLisc = new TreeItem<String>(
					recepcjonistka.getOsoba().getImie() + " " + recepcjonistka.getOsoba().getNazwisko());
			wezelRecepcjonistek.getChildren().add(recepcjonistkaLisc);
		}

		wezelLekarzy.setExpanded(true);
		wezelPielegniarek.setExpanded(true);
		wezelRecepcjonistek.setExpanded(true);

		// dodanie do g³ównego wêz³a wêz³ów z pracownikami
		TreeItem<String> wezelGlowny = new TreeItem<String>("Pracownicy");
		wezelGlowny.setExpanded(true);
		wezelGlowny.getChildren().addAll(wezelLekarzy, wezelPielegniarek, wezelRecepcjonistek);

		listaPracownikowTreeView.setRoot(wezelGlowny);
		listaPracownikowTreeView.setShowRoot(false);

		// ##################### INICJALIZACJA TABELI
		formularzPracownikow.setVisible(false);

	}

	@FXML
	void mouseClick(MouseEvent event) {

		TreeItem<String> item = listaPracownikowTreeView.getSelectionModel().getSelectedItem();
		// sprawdza czy klikniêto na pracownika
		if (item.getValue().toString().contains(" ")) {
			String imiePracownika = item.getValue().split(" ")[0];
			String nazwiskoPracownika = item.getValue().split(" ")[1];

			wybranyPracownik = null;

			// wyszukuje wybranego pracownika
			for (Pracownik pracownik : listaPracownikow) {
				if (imiePracownika.equals(pracownik.getOsoba().getImie())
						&& nazwiskoPracownika.equals(pracownik.getOsoba().getNazwisko())) {
					wybranyPracownik = pracownik;

				}
			}
			if (wybranyPracownik != null) {
				wczytajDanePracownikaDoFormularza();
				formularzPracownikow.setVisible(true);
			} else {
				formularzPracownikow.setVisible(false);
			}
		}
	}

	private void wczytajDanePracownikaDoFormularza() {

		login.setText(wybranyPracownik.getKonto().getLogin());
		haslo.setText(wybranyPracownik.getKonto().getHaslo());
		imie.setText(wybranyPracownik.getOsoba().getImie());
		nazwisko.setText(wybranyPracownik.getOsoba().getNazwisko());
		miejscowosc.setText(wybranyPracownik.getOsoba().getMiejscowosc());
		numerDomu.setText(Integer.toString(wybranyPracownik.getOsoba().getNumer_domu()));
		ulica.setText(wybranyPracownik.getOsoba().getUlica());
		kodPocztowy.setText(wybranyPracownik.getOsoba().getKod_pocztowy());
		poczta.setText(wybranyPracownik.getOsoba().getPoczta());
		numerTelefonu.setText(wybranyPracownik.getOsoba().getNumer_telefonu());
	}

	private Pracownik wczytajDanePracownikaDoObiektu() {
		Pracownik pracownik = wybranyPracownik;

		Konto konto = new Konto();
		konto.setID(wybranyPracownik.getKonto().getID());
		konto.setTyp_konta(wybranyPracownik.getKonto().getTyp_konta());
		konto.setLogin(login.getText());
		konto.setHaslo(haslo.getText());
		pracownik.setKonto(konto);

		Osoba osoba = new Osoba();
		osoba.setID(pracownik.getOsoba().getID());
		osoba.setImie(imie.getText());
		osoba.setNazwisko(nazwisko.getText());
		osoba.setMiejscowosc(miejscowosc.getText());
		osoba.setNumer_domu(Integer.parseInt(numerDomu.getText()));
		osoba.setUlica(ulica.getText());
		osoba.setKod_pocztowy(kodPocztowy.getText());
		osoba.setPoczta(poczta.getText());
		osoba.setNumer_telefonu(numerTelefonu.getText());
		pracownik.setOsoba(osoba);

		return pracownik;
	}

	@FXML
	void aktualizacjaPracownika(ActionEvent event) {
		boolean zatwierdzAktualizacje = false;
		zatwierdzAktualizacje = Komunikaty.wyswietlPotwierdzenie("Potwierdzenie", "Aktualizowanie danych pracownika",
				"Na pewno chcesz zapisaæ zmiany?");
		if (zatwierdzAktualizacje) {
			KontaRepositoryImpl kontaRepository = new KontaRepositoryImpl();
			Pracownik pracownik = wczytajDanePracownikaDoObiektu();
			kontaRepository.aktualizujPracownika(pracownik);
			initialize();
		}

	}

	@FXML
	private JFXTreeView<String> listaPracownikowTreeView;

	@FXML
	private Pane formularzPracownikow;

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
	private JFXButton zaktualizujPracownika;

}
