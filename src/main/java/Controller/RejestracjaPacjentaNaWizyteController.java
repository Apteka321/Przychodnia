package Controller;

import java.io.IOException;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.print.DocFlavor.URL;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
//import com.mchange.v2.sql.filter.SynchronizedFilterCallableStatement;
import com.jfoenix.controls.JFXTimePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.HibernateUtil;
import model.Lekarz;
import model.Leki;
import model.Leki_Recepta;
import model.Lista_zabiegow;
import model.Osoba;
import model.Pacjent;
import model.Recepcjonistka;
import model.Recepta;
import model.Skierowanie;
import model.Wizyta;
import model.Zabieg;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyAdministratoraImpl;
import model.repository.impl.MetodyRecepcjonistkaImpl;

public class RejestracjaPacjentaNaWizyteController implements Initializable {

	@FXML
	private ChoiceBox<String> lekarz;
	@FXML
	private JFXTextField PESEL;

	@FXML
	private JFXDatePicker data;
	@FXML
	private ChoiceBox<String> ubezpieczenie;
	@FXML
	private ChoiceBox<String> specjalizacja;

	@FXML
	private JFXTimePicker godzina;

	@FXML
	private JFXTextField szukaj;

	@FXML
	private TableView<pacjentOsoba> tabelaPacjentow;
	@FXML
	private TableColumn<pacjentOsoba, String> tabelaPESEL;
	@FXML
	private TableColumn<pacjentOsoba, String> tabelaImie;
	@FXML
	private TableColumn<pacjentOsoba, String> tabelaNazwisko;

	String peselPoKliknieciu = null;

	MetodyRecepcjonistkaImpl metodyRecepcjonistkaImpl = new MetodyRecepcjonistkaImpl();

	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();

	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();

	public ObservableList<pacjentOsoba> listaPacjentow;

	ObservableList<pacjentOsoba> wszyscyPacjenciX;

	// metoda do szukanie elementu w tablicy.
	@FXML
	void metodaSzukaj(KeyEvent event) {
		ObservableList<pacjentOsoba> znalezieniPacjenci = FXCollections.observableArrayList();
		listaPacjentow.clear();
		listaPacjentow.addAll(wszyscyPacjenciX);
		String wpisanaFraza = szukaj.getText();

		String[] tablicaFraz = wpisanaFraza.split(" ");
		int ileSpacji = wpisanaFraza.length() - wpisanaFraza.replace(" ", "").length();
		System.out.println(ileSpacji);
		System.out.println("\n Szukana Fraza:" + wpisanaFraza);

		if (wpisanaFraza.length() != 0) {

			for (pacjentOsoba pacjentOsoba : listaPacjentow) {
				pacjentOsoba pacjentWPetli = pacjentOsoba;
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
			listaPacjentow.clear();
			listaPacjentow.addAll(znalezieniPacjenci);
		} else {
			listaPacjentow.clear();
			listaPacjentow.addAll(wszyscyPacjenciX);
		}
	}

	@FXML
	void klikniecieNaTabele(MouseEvent event) {

		peselPoKliknieciu = tabelaPacjentow.getSelectionModel().getSelectedItem().getPesel();

	}

	public void initialize(java.net.URL arg0, ResourceBundle arg1) {

		// uzupe³niene tabeli pacjentami (Imie,Nazwisko<PESEL)
		tabelaPESEL.setCellValueFactory(new PropertyValueFactory<pacjentOsoba, String>("pesel"));
		tabelaImie.setCellValueFactory(new PropertyValueFactory<pacjentOsoba, String>("imie"));
		tabelaNazwisko.setCellValueFactory(new PropertyValueFactory<pacjentOsoba, String>("nazwisko"));
		wszyscyPacjenciX = FXCollections.observableArrayList();
		listaPacjentow = FXCollections.observableArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Pacjent");
		List<Pacjent> lista = query.list();
		for (Pacjent pacjent : lista) {
			pacjentOsoba pOsoba = new pacjentOsoba(pacjent.getOsoba().getImie(), pacjent.getOsoba().getNazwisko(),
					pacjent.getPESEL());
			listaPacjentow.add(pOsoba);
			wszyscyPacjenciX.add(pOsoba);
		}

		tabelaPacjentow.setItems(listaPacjentow);
		
		// wypisanie wszystkich specjalizacji
		specjalizacja.getItems().addAll(metodyRecepcjonistkaImpl.wypisanieSpecjalizacji());
		specjalizacja.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// po wybraniu wpecjalizacji pojawi¹ sie tylko ci lekarze o
				// takiej specjalizacji.
				lekarz.getSelectionModel().clearSelection();
				lekarz.getItems().clear();
				lekarz.getItems().addAll(metodyRecepcjonistkaImpl
						.wypisanieLekarzy(metodyRecepcjonistkaImpl.wypiszIDLekarza(specjalizacja.getValue())));
			}
		});

		final List<String> czyUbezpieczony = new ArrayList<String>();
		czyUbezpieczony.add("Tak");
		czyUbezpieczony.add("Nie");
		ubezpieczenie.getItems().addAll(czyUbezpieczony);
		session.close();
	}

	@FXML
	void dodajWizyte(ActionEvent event) {
		Time czas = new Time(godzina.getValue().getHour(), godzina.getValue().getMinute(), 0);
		metodyRecepcjonistkaImpl.dodajWizyte(peselPoKliknieciu, specjalizacja.getValue(), lekarz.getValue(), czas, data,
				ubezpieczenie.getValue());

	}


	// klasa pomocnicza przechowuje elemeny z klasy pacjent i osoba.
	public class pacjentOsoba {
		public pacjentOsoba(String imie, String nazwisko, String pesel) {
			super();
			this.imie = imie;
			this.nazwisko = nazwisko;
			this.pesel = pesel;
		}

		private String imie;
		private String nazwisko;
		private String pesel;

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

		public String getPesel() {
			return pesel;
		}

		public void setPesel(String pesel) {
			this.pesel = pesel;
		}

	}

}
