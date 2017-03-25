package Controller;

import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HibernateUtil;
import model.Wizyta;

public class PrzegladanieZaplanowanychWizytController implements Initializable {
	@FXML
	private TableView<pacjentWizyta> listaPacjentow;

	@FXML
	private TableColumn<pacjentWizyta, String> kolumnaPesel;

	@FXML
	private TableColumn<pacjentWizyta, String> kolumnaImie;

	@FXML
	private TableColumn<pacjentWizyta, String> kolumnaNazwisko;

	@FXML
	private TableColumn<pacjentWizyta, Time> kolumnaGodzinaWizyty;

	public ObservableList<pacjentWizyta> listaPacjentowNaWizyte;

	public void initialize(URL location, ResourceBundle resources) {

		kolumnaPesel.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, String>("pesel"));
		kolumnaImie.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, String>("imie"));
		kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, String>("nazwisko"));
		kolumnaGodzinaWizyty.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, Time>("godzina"));

		listaPacjentowNaWizyte = FXCollections.observableArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();

		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		Query query = session.createQuery("From Wizyta where data = :zmienna");
		query.setParameter("zmienna", date);

		List<Wizyta> lista = query.list();
		for (Wizyta wizyta : lista) {
			if (wizyta.getID_lekarza().getID() == 1) {
				pacjentWizyta pWizyta = new pacjentWizyta(wizyta.getPESEL_pacjenta().getPESEL(),
						wizyta.getPESEL_pacjenta().getOsoba().getImie(),
						wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(), wizyta.getGodzina());
				listaPacjentowNaWizyte.add(pWizyta);
			}
			
		}

		listaPacjentow.setItems(listaPacjentowNaWizyte);
		session.close();

	}

	public class pacjentWizyta {
		String pesel;
		String imie;
		String nazwisko;
		Time godzina;

		public pacjentWizyta(String pesel, String imie, String nazwisko, Time godzina) {
			super();
			this.pesel = pesel;
			this.imie = imie;
			this.nazwisko = nazwisko;
			this.godzina = godzina;
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

		public Time getGodzina() {
			return godzina;
		}

		public void setGodzina(Time godzina) {
			this.godzina = godzina;
		}

	}

}
