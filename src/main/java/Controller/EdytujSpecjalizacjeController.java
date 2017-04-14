package Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfoenix.controls.JFXTextField;

import Controller.RejestracjaPacjentaNaWizyteController.pacjentOsoba;
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
import model.HibernateUtil;
import model.Pacjent;
import model.Specjalizacja;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyAdministratoraImpl;


public class EdytujSpecjalizacjeController implements Initializable {

	@FXML
	private ChoiceBox<String> tabelaSpecjalizacji;

	@FXML
	private JFXTextField nazwaSpecjalizacji;

	@FXML
	private JFXTextField cenaWizyty;

	@FXML
	private JFXTextField czasEizyty;

	@FXML
	private TableView<wyborSpecjalziacji> tabelaDodajSpecjalizacej;

	@FXML
	private TableColumn<wyborSpecjalziacji, String> kolumnaNazwaSpecjalizacji;

	@FXML
	private TableColumn<wyborSpecjalziacji, Boolean> kolumnaPrzyciski;

	@FXML
	private ChoiceBox<String> listaLekarzy;

	 public ObservableList<wyborSpecjalziacji> listaSpecjalizacjiLekarza = FXCollections.observableArrayList();

	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();

	public ObservableList<wyborSpecjalziacji> listaSpecjalizacji;

	public void initialize(URL arg0, ResourceBundle arg1) {
		tabelaSpecjalizacji.getItems().addAll(metodyAdministratoraImpl.wypiszNazwySpecjalizacji());
		listaLekarzy.getItems().addAll(metodyAdministratoraImpl.wypiszLekarz());
		kolumnaPrzyciski
				.setCellValueFactory(new PropertyValueFactory<wyborSpecjalziacji, Boolean>("zaznaczSpecjalizacje"));
		kolumnaNazwaSpecjalizacji.setCellValueFactory(new PropertyValueFactory<wyborSpecjalziacji, String>("nazwa"));

		listaSpecjalizacji = FXCollections.observableArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Specjalizacja");
		List<Specjalizacja> lista = query.list();
		for (Specjalizacja specjalizacja : lista) {
			wyborSpecjalziacji wSpecjalziacji = new wyborSpecjalziacji(new CheckBox(), specjalizacja.getNazwa());
			listaSpecjalizacji.add(wSpecjalziacji);

		}

		tabelaDodajSpecjalizacej.setItems(listaSpecjalizacji);
		

	}

	@FXML
	void edytujSpecjalizacje(ActionEvent event) {
		BigDecimal nowacena = new BigDecimal(cenaWizyty.getText());
		Integer nowyczas = new Integer(czasEizyty.getText());
		metodyAdministratoraImpl.edycjaSpecjalizacji(nazwaSpecjalizacji.getText(), nowacena, nowyczas,
				tabelaSpecjalizacji);
		tabelaSpecjalizacji.getSelectionModel().clearSelection();
		tabelaSpecjalizacji.getItems().clear();
		tabelaSpecjalizacji.getItems().addAll(metodyAdministratoraImpl.wypiszNazwySpecjalizacji());
		czasEizyty.setText("");
		cenaWizyty.setText("");
		nazwaSpecjalizacji.setText("");

	}

	@FXML
	void usunSpecjalizacje(ActionEvent event) {
		metodyAdministratoraImpl.usuwanieSpecjalizacji(tabelaSpecjalizacji);
		tabelaSpecjalizacji.getSelectionModel().clearSelection();
		tabelaSpecjalizacji.getItems().clear();
		tabelaSpecjalizacji.getItems().addAll(metodyAdministratoraImpl.wypiszNazwySpecjalizacji());
		czasEizyty.setText("");
		cenaWizyty.setText("");
		nazwaSpecjalizacji.setText("");

	}

	@FXML
	void wybierzSpecjalizacje(ActionEvent event) {
		nazwaSpecjalizacji.setText(metodyAdministratoraImpl.wybranegoSpecjalizacjiDoEdycji(tabelaSpecjalizacji));
	}

	@FXML
	void dodajSpecjalizacje(ActionEvent event) {
		BigDecimal nowacena = new BigDecimal(cenaWizyty.getText());
		Integer nowyczas = new Integer(czasEizyty.getText());
		metodyAdministratoraImpl.dodajSpecjaliste(nazwaSpecjalizacji.getText(), nowacena, nowyczas);
		tabelaSpecjalizacji.getSelectionModel().clearSelection();
		tabelaSpecjalizacji.getItems().clear();
		tabelaSpecjalizacji.getItems().addAll(metodyAdministratoraImpl.wypiszNazwySpecjalizacji());
		czasEizyty.setText("");
		cenaWizyty.setText("");
		nazwaSpecjalizacji.setText("");
	}

	@FXML
	void dodajLekarzowiSpecjalizacje(ActionEvent event) {

		for (wyborSpecjalziacji wSpecjalziacji : listaSpecjalizacji) {
			if (wSpecjalziacji.getZaznaczSpecjalizacje().isSelected() == true) {
				listaSpecjalizacjiLekarza.addAll(wSpecjalziacji);
			
			}
		}
		metodyAdministratoraImpl.dodajSpecjalizacje(listaSpecjalizacjiLekarza, listaLekarzy.getValue());

	}

	public class wyborSpecjalziacji {
		private String nazwa;
		private CheckBox zaznaczSpecjalizacje;

		public wyborSpecjalziacji(CheckBox zaznaczSpecjalizacje, String nazwa) {
			super();
			this.nazwa = nazwa;
			this.zaznaczSpecjalizacje = zaznaczSpecjalizacje;
		}

		public String getNazwa() {
			return nazwa;
		}

		public void setNazwa(String nazwa) {
			this.nazwa = nazwa;
		}

		public CheckBox getZaznaczSpecjalizacje() {
			return zaznaczSpecjalizacje;
		}

		public void setZaznaczSpecjalizacje(CheckBox zaznaczSpecjalizacje) {
			this.zaznaczSpecjalizacje = zaznaczSpecjalizacje;
		}

	}

}
