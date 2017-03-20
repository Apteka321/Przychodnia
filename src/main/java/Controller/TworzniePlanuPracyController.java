package Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;
import java.sql.Time;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import View.Komunikaty;

import model.Lekarz;
import model.Pielegniarka;
import model.Plan_dzienny;
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

public class TworzniePlanuPracyController {
	Pracownik wybranyPracownik = null;
	SalaRepository salaRepository = new SalaRepositoryImpl();
	PlanPracyRepository planPracyRepository = new PlanPracyRepositoryImpl();
	List<Sala> listaSal;
	private List<Pracownik> listaPracownikow;
	private List<Lekarz> listaLekarzy;
	private List<Pielegniarka> listaPielegniarek;
	private List<Recepcjonistka> listaRecepcjonistek;

	@FXML
	public void initialize() {
		// Pobranie listy sal
		listaSal = salaRepository.getListaSal();

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
		formularzPlanuPracyPane.setVisible(false);
		wczytajSaleDoChoiceBoxow();
	}

	public void mouseClick(MouseEvent mouseEvent) {
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

			// wyœwietla formularz rejestracyjny po klikniêciu
			if (!formularzPlanuPracyPane.isVisible()) {
				formularzPlanuPracyPane.setVisible(true);
			}
		} else {
			if (formularzPlanuPracyPane.isVisible()) {
				formularzPlanuPracyPane.setVisible(false);
			}
		}

	}

	// pobiera z bazy listê sal i wstawia ich numery w choiceBox
	private void wczytajSaleDoChoiceBoxow() {

		for (Sala sala : listaSal) {
			System.out.println(sala.getNumer_sali());
			sala1.getItems().add(Integer.toString(sala.getNumer_sali()));
			sala2.getItems().add(Integer.toString(sala.getNumer_sali()));
			sala3.getItems().add(Integer.toString(sala.getNumer_sali()));
			sala4.getItems().add(Integer.toString(sala.getNumer_sali()));
			sala5.getItems().add(Integer.toString(sala.getNumer_sali()));
			sala6.getItems().add(Integer.toString(sala.getNumer_sali()));
			sala7.getItems().add(Integer.toString(sala.getNumer_sali()));
		}
	}

	// metoda wywo³ywana po wciœniêciu buttona Zapisz
	@FXML
	public void dodajPlanPracownikowi(ActionEvent event) {
		Plan_pracy plan_pracy = wczytajPlanPracyZFormularza();
		// sprawdzanie czy wybrano pracownika
		if (wybranyPracownik == null || wynagrodzenie.getText().length() == 0 || dataUmowy.getValue() == null) {
			if (wybranyPracownik == null)
				Komunikaty.wyswietlOstrzezenie("B³¹d", "Wybierz pracownika!");
			if (wynagrodzenie.getText().length() == 0)
				Komunikaty.wyswietlOstrzezenie("B³¹d", "WprowadŸ wynagrodzenie!");
			if (dataUmowy.getValue() == null)
				Komunikaty.wyswietlOstrzezenie("B³¹d", "WprowadŸ datê zawarcia umowy!");

		}

		else {

			List<Plan_dzienny> listaPlanowDziennych = new ArrayList<>();

			planPracyRepository.dodajPlanPracownikowi(wybranyPracownik, plan_pracy, plan_pracy.getPlan_dzienny());
		}

	}

	// metoda pobiera dane z formularza do listy planów dziennych
	private Plan_pracy wczytajPlanPracyZFormularza() {

		boolean uzupelnionoJedenDzien = false;

		Plan_pracy plan_pracy = new Plan_pracy();
		Set<Plan_dzienny> planTygodniowy = new HashSet<Plan_dzienny>();

		// Poniedzia³ek
		if (poczatekPracy1.getValue() != null && koniecPracy1.getValue() != null && przerwaOd1.getValue() != null
				&& przerwaDo1.getValue() != null && sala1.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaPoniedzialek = new Plan_dzienny();
			planNaPoniedzialek.setDzien(1);

			Time czas = new Time((long) poczatekPracy1.getValue().getNano());
			planNaPoniedzialek.setPoczatek(czas);

			czas = new Time((long) koniecPracy1.getValue().getNano());
			planNaPoniedzialek.setKoniec(czas);

			czas = new Time((long) przerwaOd1.getValue().getNano());
			planNaPoniedzialek.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo1.getValue().getNano());
			planNaPoniedzialek.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala1.getValue().toString()));
			planNaPoniedzialek.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaPoniedzialek);

			planNaPoniedzialek.setPlan_pracy(plan_pracy);
		}

		// Wtorek
		if (poczatekPracy2.getValue() != null && koniecPracy2.getValue() != null && przerwaOd2.getValue() != null
				&& przerwaDo2.getValue() != null && sala2.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaWtorek = new Plan_dzienny();
			planNaWtorek.setDzien(2);

			Time czas = new Time((long) poczatekPracy2.getValue().getNano());
			planNaWtorek.setPoczatek(czas);

			czas = new Time((long) koniecPracy2.getValue().getNano());
			planNaWtorek.setKoniec(czas);

			czas = new Time((long) przerwaOd2.getValue().getNano());
			planNaWtorek.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo2.getValue().getNano());
			planNaWtorek.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala2.getValue().toString()));
			planNaWtorek.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaWtorek);

			planNaWtorek.setPlan_pracy(plan_pracy);
		}

		// Œroda
		if (poczatekPracy3.getValue() != null && koniecPracy3.getValue() != null && przerwaOd3.getValue() != null
				&& przerwaDo3.getValue() != null && sala3.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaSrode = new Plan_dzienny();
			planNaSrode.setDzien(3);

			Time czas = new Time((long) poczatekPracy3.getValue().getNano());
			planNaSrode.setPoczatek(czas);

			czas = new Time((long) koniecPracy3.getValue().getNano());
			planNaSrode.setKoniec(czas);

			czas = new Time((long) przerwaOd3.getValue().getNano());
			planNaSrode.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo3.getValue().getNano());
			planNaSrode.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala3.getValue().toString()));
			planNaSrode.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaSrode);

			planNaSrode.setPlan_pracy(plan_pracy);
		}

		// Czwartek
		if (poczatekPracy4.getValue() != null && koniecPracy4.getValue() != null && przerwaOd4.getValue() != null
				&& przerwaDo4.getValue() != null && sala4.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaCzwartek = new Plan_dzienny();
			planNaCzwartek.setDzien(4);

			Time czas = new Time((long) poczatekPracy4.getValue().getNano());
			planNaCzwartek.setPoczatek(czas);

			czas = new Time((long) koniecPracy4.getValue().getNano());
			planNaCzwartek.setKoniec(czas);

			czas = new Time((long) przerwaOd4.getValue().getNano());
			planNaCzwartek.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo4.getValue().getNano());
			planNaCzwartek.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala4.getValue().toString()));
			planNaCzwartek.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaCzwartek);

			planNaCzwartek.setPlan_pracy(plan_pracy);
		}

		// Piatek
		if (poczatekPracy5.getValue() != null && koniecPracy5.getValue() != null && przerwaOd5.getValue() != null
				&& przerwaDo5.getValue() != null && sala5.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaPiatek = new Plan_dzienny();
			planNaPiatek.setDzien(5);

			Time czas = new Time((long) poczatekPracy5.getValue().getNano());
			planNaPiatek.setPoczatek(czas);

			czas = new Time((long) koniecPracy5.getValue().getNano());
			planNaPiatek.setKoniec(czas);

			czas = new Time((long) przerwaOd5.getValue().getNano());
			planNaPiatek.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo5.getValue().getNano());
			planNaPiatek.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala5.getValue().toString()));
			planNaPiatek.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaPiatek);

			planNaPiatek.setPlan_pracy(plan_pracy);
		}

		// Sobota
		if (poczatekPracy6.getValue() != null && koniecPracy6.getValue() != null && przerwaOd6.getValue() != null
				&& przerwaDo6.getValue() != null && sala6.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaSobote = new Plan_dzienny();
			planNaSobote.setDzien(6);

			Time czas = new Time((long) poczatekPracy6.getValue().getNano());
			planNaSobote.setPoczatek(czas);

			czas = new Time((long) koniecPracy6.getValue().getNano());
			planNaSobote.setKoniec(czas);

			czas = new Time((long) przerwaOd6.getValue().getNano());
			planNaSobote.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo6.getValue().getNano());
			planNaSobote.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala6.getValue().toString()));
			planNaSobote.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaSobote);

			planNaSobote.setPlan_pracy(plan_pracy);
		}

		// Niedziela
		if (poczatekPracy7.getValue() != null && koniecPracy7.getValue() != null && przerwaOd7.getValue() != null
				&& przerwaDo7.getValue() != null && sala7.getValue() != null) {

			uzupelnionoJedenDzien = true;

			Plan_dzienny planNaNiedziele = new Plan_dzienny();
			planNaNiedziele.setDzien(7);

			Time czas = new Time((long) poczatekPracy7.getValue().getNano());
			planNaNiedziele.setPoczatek(czas);

			czas = new Time((long) koniecPracy7.getValue().getNano());
			planNaNiedziele.setKoniec(czas);

			czas = new Time((long) przerwaOd7.getValue().getNano());
			planNaNiedziele.setPrzerwa_od(czas);

			czas = new Time((long) przerwaDo7.getValue().getNano());
			planNaNiedziele.setPrzerwa_do(czas);

			Sala sala = getSalaZListySal(Integer.parseInt(sala7.getValue().toString()));
			planNaNiedziele.setSalaNumer_sali(sala);

			planTygodniowy.add(planNaNiedziele);

			planNaNiedziele.setPlan_pracy(plan_pracy);
		}

		// Sprawdzanie czy uzupe³niono przynajmniej jeden dzieñ
		if (uzupelnionoJedenDzien) {
			plan_pracy.setUmowa_od(new Date(dataUmowy.getValue().getYear() - 1900,
					dataUmowy.getValue().getMonthValue() - 1, dataUmowy.getValue().getDayOfMonth()));
			plan_pracy.setWyplata(new BigDecimal(wynagrodzenie.getText()));
			plan_pracy.setPlan_dzienny(planTygodniowy);
		} else {
			Komunikaty.wyswietlInformacje("B³¹d", "Nie mo¿na dodaæ planu! Uzupe³nij przynajmniej jeden dzieñ.");
		}

		return plan_pracy;
	}

	private Sala getSalaZListySal(int numerSali) {
		Sala szukanaSala = null;
		for (Sala sala : listaSal) {
			if (sala.getNumer_sali() == numerSali) {
				szukanaSala = sala;
			}
		}
		return szukanaSala;
	}

	@FXML
	private Pane formularzPlanuPracyPane;

	@FXML
	private JFXTreeView<String> listaPracownikowTreeView;

	@FXML
	private JFXTimePicker poczatekPracy1;

	@FXML
	private JFXTimePicker koniecPracy1;

	@FXML
	private JFXTimePicker poczatekPracy2;

	@FXML
	private JFXTimePicker koniecPracy2;

	@FXML
	private JFXTimePicker poczatekPracy3;

	@FXML
	private JFXTimePicker koniecPracy3;

	@FXML
	private JFXTimePicker poczatekPracy4;

	@FXML
	private JFXTimePicker koniecPracy4;

	@FXML
	private JFXTimePicker poczatekPracy5;

	@FXML
	private JFXTimePicker koniecPracy5;

	@FXML
	private JFXTimePicker poczatekPracy6;

	@FXML
	private JFXTimePicker koniecPracy6;

	@FXML
	private JFXTimePicker poczatekPracy7;

	@FXML
	private JFXTimePicker koniecPracy7;

	@FXML
	private JFXTimePicker przerwaOd1;

	@FXML
	private JFXTimePicker przerwaOd2;

	@FXML
	private JFXTimePicker przerwaOd3;

	@FXML
	private JFXTimePicker przerwaDo1;

	@FXML
	private JFXTimePicker przerwaDo2;

	@FXML
	private JFXTimePicker przerwaDo3;

	@FXML
	private JFXTimePicker przerwaOd4;

	@FXML
	private JFXTimePicker przerwaDo4;

	@FXML
	private JFXTimePicker przerwaOd5;

	@FXML
	private JFXTimePicker przerwaDo5;

	@FXML
	private JFXTimePicker przerwaOd6;

	@FXML
	private JFXTimePicker przerwaDo6;

	@FXML
	private JFXTimePicker przerwaOd7;

	@FXML
	private JFXTimePicker przerwaDo7;

	@FXML
	private ChoiceBox<String> sala1;

	@FXML
	private ChoiceBox<String> sala2;

	@FXML
	private ChoiceBox<String> sala3;

	@FXML
	private ChoiceBox<String> sala4;

	@FXML
	private ChoiceBox<String> sala5;

	@FXML
	private ChoiceBox<String> sala6;

	@FXML
	private ChoiceBox<String> sala7;

	@FXML
	private JFXTextField wynagrodzenie;

	@FXML
	private JFXDatePicker dataUmowy;

	@FXML
	private JFXButton zapiszPlan;

}
