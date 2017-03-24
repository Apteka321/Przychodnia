package Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
import javafx.util.converter.LocalDateStringConverter;
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

	private Pracownik wybranyPracownik = null;
	private SalaRepository salaRepository = new SalaRepositoryImpl();
	private PlanPracyRepository planPracyRepository = new PlanPracyRepositoryImpl();
	private List<Sala> listaSal;
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
		wyczyscFormularz();
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
					if (wybranyPracownik.getPlan_pracy() != null) {
						boolean wczytacPlanZBazyDanych = Komunikaty.wyswietlPotwierdzenie("Uwaga!",
								"Wybrany pracownik posiada ju¿ plan pracy.", "Chcesz wczytaæ istniej¹cy plan pracy?");
						wyczyscFormularz();
						if (wczytacPlanZBazyDanych) {
							wczytajPlanPracyDoFormularza(wybranyPracownik);
						}
					}
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

	private void wyczyscFormularz() {
		wynagrodzenie.setText("");
		dataUmowy.setValue(null);

		poczatekPracy1.setValue(null);
		koniecPracy1.setValue(null);
		przerwaOd1.setValue(null);
		przerwaDo1.setValue(null);
		sala1.getSelectionModel().select(null);

		poczatekPracy2.setValue(null);
		koniecPracy2.setValue(null);
		przerwaOd2.setValue(null);
		przerwaDo2.setValue(null);
		sala2.getSelectionModel().select(null);

		poczatekPracy3.setValue(null);
		koniecPracy3.setValue(null);
		przerwaOd3.setValue(null);
		przerwaDo3.setValue(null);
		sala3.getSelectionModel().select(null);

		poczatekPracy4.setValue(null);
		koniecPracy4.setValue(null);
		przerwaOd4.setValue(null);
		przerwaDo4.setValue(null);
		sala4.getSelectionModel().select(null);

		poczatekPracy5.setValue(null);
		koniecPracy5.setValue(null);
		przerwaOd5.setValue(null);
		przerwaDo5.setValue(null);
		sala5.getSelectionModel().select(null);

		poczatekPracy6.setValue(null);
		koniecPracy6.setValue(null);
		przerwaOd6.setValue(null);
		przerwaDo6.setValue(null);
		sala6.getSelectionModel().select(null);

		poczatekPracy7.setValue(null);
		koniecPracy7.setValue(null);
		przerwaOd7.setValue(null);
		przerwaDo7.setValue(null);
		sala7.getSelectionModel().select(null);
	}

	private void wczytajPlanPracyDoFormularza(Pracownik wybranyPracownik2) {

		Plan_pracy plan_pracy = planPracyRepository.wczytajPlanPracyPracownika(wybranyPracownik);
		Set<Plan_dzienny> setPlanowDziennych = plan_pracy.getPlan_dzienny();

		wynagrodzenie.setText(plan_pracy.getWyplata().toString());
		// dataUmowy.setValue(dateToLocalDate(plan_pracy.getUmowa_od()));
		dataUmowy.setValue(LocalDate.of(plan_pracy.getUmowa_od().getYear() + 1900, plan_pracy.getUmowa_od().getMonth(),
				plan_pracy.getUmowa_od().getDay()));

		for (Plan_dzienny plan_dzienny : setPlanowDziennych) {
			// plan na poniedzia³ek
			if (plan_dzienny.getDzien() == 1) {
				poczatekPracy1.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy1.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd1.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo1.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala1.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}

			// plan na wtorek
			if (plan_dzienny.getDzien() == 2) {
				poczatekPracy2.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy2.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd2.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo2.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala2.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}

			// plan na œrode
			if (plan_dzienny.getDzien() == 3) {
				poczatekPracy3.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy3.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd3.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo3.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala3.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}

			// plan na czwartek
			if (plan_dzienny.getDzien() == 4) {
				poczatekPracy4.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy4.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd4.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo4.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala4.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}

			// plan na piatek
			if (plan_dzienny.getDzien() == 5) {
				poczatekPracy5.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy5.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd5.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo5.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala5.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}

			// plan na sobote
			if (plan_dzienny.getDzien() == 6) {
				poczatekPracy6.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy6.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd6.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo6.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala6.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}

			// plan na sobote
			if (plan_dzienny.getDzien() == 7) {
				poczatekPracy7.setValue(plan_dzienny.getPoczatek().toLocalTime());
				koniecPracy7.setValue(plan_dzienny.getKoniec().toLocalTime());
				przerwaOd7.setValue(plan_dzienny.getPrzerwa_od().toLocalTime());
				przerwaDo7.setValue(plan_dzienny.getPrzerwa_do().toLocalTime());
				sala7.getSelectionModel().select(plan_dzienny.getSalaNr_sali());
			}
		}
	}

	// konwersja date na localDate
	private LocalDate dateToLocalDate(Date date) {
		String dateString = date.toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(dateString, formatter);

		return localDate;

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
			planNaPoniedzialek.setPoczatek(
					new Time(poczatekPracy1.getValue().getHour(), poczatekPracy1.getValue().getMinute(), 0));
			planNaPoniedzialek
					.setKoniec(new Time(koniecPracy1.getValue().getHour(), koniecPracy1.getValue().getMinute(), 0));
			planNaPoniedzialek
					.setPrzerwa_od(new Time(przerwaOd1.getValue().getHour(), przerwaOd1.getValue().getMinute(), 0));
			planNaPoniedzialek
					.setPrzerwa_do(new Time(przerwaDo1.getValue().getHour(), przerwaDo1.getValue().getMinute(), 0));

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
			planNaWtorek.setPoczatek(
					new Time(poczatekPracy2.getValue().getHour(), poczatekPracy2.getValue().getMinute(), 0));
			planNaWtorek.setKoniec(new Time(koniecPracy2.getValue().getHour(), koniecPracy2.getValue().getMinute(), 0));
			planNaWtorek.setPrzerwa_od(new Time(przerwaOd2.getValue().getHour(), przerwaOd2.getValue().getMinute(), 0));
			planNaWtorek.setPrzerwa_do(new Time(przerwaDo2.getValue().getHour(), przerwaDo2.getValue().getMinute(), 0));

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
			planNaSrode.setPoczatek(
					new Time(poczatekPracy3.getValue().getHour(), poczatekPracy3.getValue().getMinute(), 0));
			planNaSrode.setKoniec(new Time(koniecPracy3.getValue().getHour(), koniecPracy3.getValue().getMinute(), 0));
			planNaSrode.setPrzerwa_od(new Time(przerwaOd3.getValue().getHour(), przerwaOd3.getValue().getMinute(), 0));
			planNaSrode.setPrzerwa_do(new Time(przerwaDo3.getValue().getHour(), przerwaDo3.getValue().getMinute(), 0));

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

			planNaCzwartek.setPoczatek(
					new Time(poczatekPracy4.getValue().getHour(), poczatekPracy4.getValue().getMinute(), 0));
			planNaCzwartek
					.setKoniec(new Time(koniecPracy4.getValue().getHour(), koniecPracy4.getValue().getMinute(), 0));
			planNaCzwartek
					.setPrzerwa_od(new Time(przerwaOd4.getValue().getHour(), przerwaOd4.getValue().getMinute(), 0));
			planNaCzwartek
					.setPrzerwa_do(new Time(przerwaDo4.getValue().getHour(), przerwaDo4.getValue().getMinute(), 0));

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

			planNaPiatek.setPoczatek(
					new Time(poczatekPracy5.getValue().getHour(), poczatekPracy5.getValue().getMinute(), 0));
			planNaPiatek.setKoniec(new Time(koniecPracy5.getValue().getHour(), koniecPracy5.getValue().getMinute(), 0));
			planNaPiatek.setPrzerwa_od(new Time(przerwaOd5.getValue().getHour(), przerwaOd5.getValue().getMinute(), 0));
			planNaPiatek.setPrzerwa_do(new Time(przerwaDo5.getValue().getHour(), przerwaDo5.getValue().getMinute(), 0));

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

			planNaSobote.setPoczatek(
					new Time(poczatekPracy6.getValue().getHour(), poczatekPracy6.getValue().getMinute(), 0));
			planNaSobote.setKoniec(new Time(koniecPracy6.getValue().getHour(), koniecPracy6.getValue().getMinute(), 0));
			planNaSobote.setPrzerwa_od(new Time(przerwaOd6.getValue().getHour(), przerwaOd6.getValue().getMinute(), 0));
			planNaSobote.setPrzerwa_do(new Time(przerwaDo6.getValue().getHour(), przerwaDo6.getValue().getMinute(), 0));

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
			planNaNiedziele.setPoczatek(
					new Time(poczatekPracy7.getValue().getHour(), poczatekPracy7.getValue().getMinute(), 0));
			planNaNiedziele
					.setKoniec(new Time(koniecPracy7.getValue().getHour(), koniecPracy7.getValue().getMinute(), 0));
			planNaNiedziele
					.setPrzerwa_od(new Time(przerwaOd7.getValue().getHour(), przerwaOd7.getValue().getMinute(), 0));
			planNaNiedziele
					.setPrzerwa_do(new Time(przerwaDo7.getValue().getHour(), przerwaDo7.getValue().getMinute(), 0));

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
