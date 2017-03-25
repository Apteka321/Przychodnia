package Controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeView;

import View.Komunikaty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Lekarz;
import model.Pielegniarka;
import model.Pracownik;
import model.Wolne_od_pracy;
import model.Recepcjonistka;
import model.repository.KontaRepository;
import model.repository.PlanPracyRepository;
import model.repository.impl.KontaRepositoryImpl;
import model.repository.impl.PlanPracyRepositoryImpl;

public class TworzenieUrlopowController {
	Pracownik wybranyPracownik = null;
	PlanPracyRepository planPracyRepository = new PlanPracyRepositoryImpl();
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
		wezelGlowny.getChildren().add(wezelLekarzy);
		wezelGlowny.getChildren().add(wezelPielegniarek);
		wezelGlowny.getChildren().add(wezelRecepcjonistek);

		listaPracownikowTreeView.setRoot(wezelGlowny);
		listaPracownikowTreeView.setShowRoot(false);

		// ##################### INICJALIZACJA TABELI
		formularzUrlopow.setVisible(false);
	}

	// metoda wywo³ywana po klikniêcie w treeView
	public void mouseClick(MouseEvent mouseEvent) {
		TreeItem<String> item = (TreeItem<String>) listaPracownikowTreeView.getSelectionModel().getSelectedItem();

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

			// wyœwietla formularz rejestracyjny po klikniêciu
			if (!formularzUrlopow.isVisible()) {
				formularzUrlopow.setVisible(true);
			}
		} else {
			if (formularzUrlopow.isVisible()) {
				formularzUrlopow.setVisible(false);
			}
		}

	}

	// metoda wywo³ywana po klikniêciu przycisku dodaj
	@FXML
	void dodajUrlop(ActionEvent event) {
		if (odDnia.getValue() == null || odGodziny.getValue() == null || doDnia.getValue() == null
				|| doGodziny.getValue() == null || powodZwolnienia.getText().length() == 0) {
			Komunikaty.wyswietlOstrzezenie("B³¹d!", "Uzupe³nij wszystkie pola aby wprowadziæ urlop");
		} else {
			Wolne_od_pracy wolne_od_pracy = pobierzWolneZFormularza();
			planPracyRepository.dodajWolnePracownikowi(wybranyPracownik, wolne_od_pracy);
		}
	}

	// metoda pobiera do obiektu wolneOdPracy z formularza
	private Wolne_od_pracy pobierzWolneZFormularza() {
		Wolne_od_pracy urlop = new Wolne_od_pracy();
		// Od
		urlop.setOd_dnia(new Date(odDnia.getValue().getYear() - 1900, odDnia.getValue().getMonthValue() - 1,
				odDnia.getValue().getDayOfMonth()));
		urlop.setOd_godziny(new Time(odGodziny.getValue().getHour(), odGodziny.getValue().getMinute(), 0));

		// Do
		urlop.setDo_dnia(new Date(doDnia.getValue().getYear() - 1900, doDnia.getValue().getMonthValue() - 1,
				doDnia.getValue().getDayOfMonth()));
		urlop.setDo_godziny(new Time(doGodziny.getValue().getHour(), doGodziny.getValue().getMinute(), 0));

		urlop.setPowod(powodZwolnienia.getText());
		return urlop;
	}

	@FXML
	private JFXTextField powodZwolnienia;

	@FXML
	private GridPane formularzUrlopow;

	@FXML
	private JFXTreeView<String> listaPracownikowTreeView;

	@FXML
	private JFXDatePicker odDnia;

	@FXML
	private JFXDatePicker doDnia;

	@FXML
	private JFXTimePicker odGodziny;

	@FXML
	private JFXTimePicker doGodziny;

	@FXML
	private JFXButton dodajUrlopButton;

}
