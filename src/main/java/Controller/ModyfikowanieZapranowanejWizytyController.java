package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import Controller.RejestracjaPacjentaNaWizyteController.pacjentOsoba;
import View.Komunikaty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.LocalDateStringConverter;
import model.HibernateUtil;
import model.Pacjent;
import model.Wizyta;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyRecepcjonistkaImpl;

public class ModyfikowanieZapranowanejWizytyController implements Initializable {
	@FXML
	private TableView<wiyztaOsoba> listaPacjentow;

	@FXML
	private TableColumn<wiyztaOsoba, String> kolumnaPesel;

	@FXML
	private TableColumn<wiyztaOsoba, String> kolumnaImie;

	@FXML
	private TableColumn<wiyztaOsoba, String> kolumnaNazwisko;

	@FXML
	private TableColumn<wiyztaOsoba, Date> kolumnaDataWizyty;

	@FXML
	private TableColumn<wiyztaOsoba, Integer> kolumnaID;

	@FXML
	private TableColumn<wiyztaOsoba, Time> kolumnaGodzinaWizyty;

	@FXML
	private JFXTextField szukaj;

	@FXML
	private JFXDatePicker dataWizyt;

	@FXML
	private JFXTimePicker godzinaWizyty;

	public ObservableList<wiyztaOsoba> listaWizyt;

	ObservableList<wiyztaOsoba> wszyscyPacjenciX;

	String peselPoKliknieciu = null;

	Integer idPacjenta;

	MetodyRecepcjonistkaImpl metodyRecepcjonistkaImpl = new MetodyRecepcjonistkaImpl();

	public void initialize(URL arg0, ResourceBundle arg1) {

		kolumnaID.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, Integer>("id"));
		kolumnaPesel.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, String>("pesel"));
		kolumnaImie.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, String>("imie"));
		kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, String>("nazwisko"));
		kolumnaDataWizyty.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, Date>("data"));
		kolumnaGodzinaWizyty.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, Time>("godzina"));

		listaWizyt = FXCollections.observableArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Wizyta");
		wszyscyPacjenciX = FXCollections.observableArrayList();

		List<Wizyta> lista = query.list();
		for (Wizyta wizyta : lista) {
			wiyztaOsoba wOsoba = new wiyztaOsoba(wizyta.getID(), wizyta.getPESEL_pacjenta().getPESEL(),
					wizyta.getData(), wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(),
					wizyta.getPESEL_pacjenta().getOsoba().getImie(), wizyta.getGodzina());
			listaWizyt.add(wOsoba);
			wszyscyPacjenciX.add(wOsoba);
		}

		listaPacjentow.setItems(listaWizyt);
		session.close();

	}

	public static LocalDate zmianaNaLokalnaDate(Date date) {
		return date.toLocalDate();
	}

	public static LocalTime toLocalTime(java.sql.Time time) {
		return time.toLocalTime();
	}
//po klikniencciu na ktorys wiersz..
	@FXML
	void klikniecieNaTabele(MouseEvent event) {

		Date nowa = listaPacjentow.getSelectionModel().getSelectedItem().getData();
		idPacjenta = listaPacjentow.getSelectionModel().getSelectedItem().getId();
		dataWizyt.setValue(zmianaNaLokalnaDate(nowa));
		peselPoKliknieciu = listaPacjentow.getSelectionModel().getSelectedItem().getPesel();

	}
//aktualizuje date i czas wizyty
	@FXML
	void aktualizuj(ActionEvent event) {
		try {
			Time czas = new Time(godzinaWizyty.getValue().getHour(), godzinaWizyty.getValue().getMinute(), 0);
			metodyRecepcjonistkaImpl.aktualizujDateWizyty(peselPoKliknieciu, dataWizyt, idPacjenta, czas);
			Komunikaty.wyswietlInformacje("Aktualizajca", "Data wizyty i godzina zosta³a zmieniona");
			for (int i = 0; i < listaPacjentow.getItems().size(); i++) {
				listaPacjentow.getItems().clear();
			}
			kolumnaID.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, Integer>("id"));
			kolumnaPesel.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, String>("pesel"));
			kolumnaImie.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, String>("imie"));
			kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, String>("nazwisko"));
			kolumnaDataWizyty.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, Date>("data"));
			kolumnaGodzinaWizyty.setCellValueFactory(new PropertyValueFactory<wiyztaOsoba, Time>("godzina"));

			listaWizyt = FXCollections.observableArrayList();
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("From Wizyta");
			wszyscyPacjenciX = FXCollections.observableArrayList();

			List<Wizyta> lista = query.list();
			for (Wizyta wizyta : lista) {
				wiyztaOsoba wOsoba = new wiyztaOsoba(wizyta.getID(), wizyta.getPESEL_pacjenta().getPESEL(),
						wizyta.getData(), wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(),
						wizyta.getPESEL_pacjenta().getOsoba().getImie(), wizyta.getGodzina());
				listaWizyt.add(wOsoba);
				wszyscyPacjenciX.add(wOsoba);
			}

			listaPacjentow.setItems(listaWizyt);
			session.close();
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("Aktualizacja", "Wyst¹pi³ b³¹d w czasie aktyalizacji");
		}

	}
//Metoda przeszukuje tabele z pacjentami
	@FXML
	void metodaSzukaj(KeyEvent event) {
		ObservableList<wiyztaOsoba> znalezieniPacjenci = FXCollections.observableArrayList();
		listaWizyt.clear();
		listaWizyt.addAll(wszyscyPacjenciX);
		String wpisanaFraza = szukaj.getText();

		String[] tablicaFraz = wpisanaFraza.split(" ");
		int ileSpacji = wpisanaFraza.length() - wpisanaFraza.replace(" ", "").length();
		System.out.println(ileSpacji);
		System.out.println("\n Szukana Fraza:" + wpisanaFraza);

		if (wpisanaFraza.length() != 0) {

			for (wiyztaOsoba pacjentOsoba : listaWizyt) {

				boolean flaga = true;
				for (String string : tablicaFraz) {
					String danePacjenta = pacjentOsoba.getImie() + " " + pacjentOsoba.getNazwisko() + " "
							+ pacjentOsoba.getPesel();
					if (!danePacjenta.toUpperCase().contains(string.toUpperCase())) {
						flaga = false;
					}
				}
				if (flaga) {
					znalezieniPacjenci.add(pacjentOsoba);
				}
			}
			listaWizyt.clear();
			listaWizyt.addAll(znalezieniPacjenci);
		} else {
			listaWizyt.clear();
			listaWizyt.addAll(wszyscyPacjenciX);
		}
	}

	@FXML
	void anuluj(ActionEvent event) throws IOException {
		KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
		((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/Admin.fxml", "Dodaj sale");

	}

	public class wiyztaOsoba {
		Integer id;
		String pesel;
		String imie;
		String nazwisko;
		Date data;
		Time godzina;

		public Time getGodzina() {
			return godzina;
		}

		public void setGodzina(Time godzina) {
			this.godzina = godzina;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPesel() {
			return pesel;
		}

		public void setPesel(String pesel) {
			this.pesel = pesel;
		}

		public String getImie() {
			return imie;
		}

		public void setImie(String imie) {
			this.imie = imie;
		}

		public String getNazwisko() {
			return nazwisko;
		}

		public void setNazwisko(String nazwisko) {
			this.nazwisko = nazwisko;
		}

		public Date getData() {
			return data;
		}

		public void setData(Date data) {
			this.data = data;
		}

		public wiyztaOsoba(Integer id, String pesel, java.util.Date date, String imie, String nazwisko, Time godzina) {
			super();
			this.id = id;
			this.pesel = pesel;
			this.imie = imie;
			this.nazwisko = nazwisko;
			this.data = (Date) date;
			this.godzina = godzina;
		}

	}

}
