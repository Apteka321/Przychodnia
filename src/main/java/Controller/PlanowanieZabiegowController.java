package Controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HibernateUtil;
import model.Lista_zabiegow;
import model.Pielegniarka;
import model.Recepcjonistka;
import model.Skierowanie;
import model.Specjalizacja;
import model.Zabieg;
import model.repository.impl.MetodyPielegniarkiImpl;

public class PlanowanieZabiegowController implements Initializable {

	@FXML
	private TableView<wybranyZabieg> tabelaNazwaZabiegu;

	@FXML
	private TableColumn<wybranyZabieg, Boolean> kolumnaZaznacz;

	@FXML
	private TableColumn<wybranyZabieg, String> kolumnaNazwaZabiegu;

	@FXML
	private JFXTextField numerSkierowania;

	@FXML
	private DatePicker dataWykonania;

	@FXML
	private JFXTextArea uwagi;

	public ObservableList<wybranyZabieg> listaWszystkichZabiegow;
	//public ObservableList<wybraneSkierowanie> listaSkierowan;
	MetodyPielegniarkiImpl metodyPielegniarkiImpl = new MetodyPielegniarkiImpl();
	public ObservableList<wybranyZabieg> listaWybranychZabiegow = FXCollections.observableArrayList();

	public void initialize(URL arg0, ResourceBundle arg1) {

		kolumnaZaznacz.setCellValueFactory(new PropertyValueFactory<wybranyZabieg, Boolean>("zaznaczZabieg"));
		kolumnaNazwaZabiegu.setCellValueFactory(new PropertyValueFactory<wybranyZabieg, String>("nazwa"));

		listaWszystkichZabiegow = FXCollections.observableArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Lista_zabiegow");
		List<Lista_zabiegow> lista = query.list();
		for (Lista_zabiegow lista_zabiegow : lista) {
			wybranyZabieg wZabieg = new wybranyZabieg(lista_zabiegow.getNazwa(), new CheckBox());
			listaWszystkichZabiegow.add(wZabieg);

		}
		tabelaNazwaZabiegu.setItems(listaWszystkichZabiegow);
		session.close();

	}

	public static java.util.Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@FXML
	void dodajZabieg(ActionEvent event) {

		for (wybranyZabieg wZabieg : listaWszystkichZabiegow) {
			if (wZabieg.getZaznaczZabieg().isSelected() == true) {
				listaWybranychZabiegow.addAll(wZabieg);
			}

		}
		metodyPielegniarkiImpl.dodajPielegniarke(numerSkierowania.getText(), listaWybranychZabiegow,
				dataWykonania.getValue(), uwagi.getText());

	}

	public class wybranyZabieg {
		private String nazwa;
		private CheckBox zaznaczZabieg;

		public String getNazwa() {
			return nazwa;
		}

		public void setNazwa(String nazwa) {
			this.nazwa = nazwa;
		}

		public CheckBox getZaznaczZabieg() {
			return zaznaczZabieg;
		}

		public void setZaznaczZabieg(CheckBox zaznaczZabieg) {
			this.zaznaczZabieg = zaznaczZabieg;
		}

		public wybranyZabieg(String nazwa, CheckBox zaznaczZabieg) {
			super();
			this.nazwa = nazwa;
			this.zaznaczZabieg = zaznaczZabieg;
		}

	}

}
