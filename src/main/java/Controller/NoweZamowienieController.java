/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Pielegniarka;
import model.Sala;
import model.repository.SalaRepository;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.SalaRepositoryImpl;
import model.repository.impl.WalidacjaFormularzaImpl;
import model.repository.impl.ZamowienieImpl;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class NoweZamowienieController implements Initializable {

	@FXML
	private TableView<ProduktZamowienia> listaProduktow;
	@FXML
	private TableColumn<ProduktZamowienia, Boolean> kolumnaZaznaczProdukt;
	@FXML
	private TableColumn<ProduktZamowienia, Integer> kolumnaNrSali;
	@FXML
	private TableColumn<ProduktZamowienia, String> kolumnaNazwaProduktu;
	@FXML
	private TableColumn<ProduktZamowienia, Integer> kolumnaIloscProduktow;
	/**
	 * Zmienne pol formularza
	 */

	@FXML
	private ChoiceBox<Sala> inputNrSali;
	@FXML
	private TextField inputNazwaProduktu;
	@FXML
	private TextField inputIloscProduktu;
	@FXML
	private JFXButton dodajDoListyZamowienButton;
	@FXML
	private JFXButton usunZListyZamowienButton;
	/**
	 * Etykiety Walidacji
	 */
	@FXML
	private Label nrSaliLabel;
	@FXML
	private Label nazwaProduktuLabel;
	@FXML
	private Label iloscLabel;
	final ZamowienieImpl noweZamowieneiImpl = new ZamowienieImpl();
	final WalidacjaFormularzaImpl walidacjaFormularza = new WalidacjaFormularzaImpl();
	final KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
	final ObservableList<ProduktZamowienia> listaProduktowDoUsuniecia = FXCollections.observableArrayList();

	/**
	 * Pobranie produktów do listy zamówienia
	 */
	final ObservableList<ProduktZamowienia> listaPrzedmiotowZamowienia = FXCollections.observableArrayList();
	@FXML
	private JFXButton utworzZamowienieButton;

	/**
	 * Mapowanie pol kalsy ProduktZamowienia zapiszEdytowaneZamowienie
	 * wartoßcami w odpowiadoajácym im kolumnom tabeli formularza zamówienia
	 *
	 * @param event
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		List<Sala> listaSal = new SalaRepositoryImpl().getListaSal();
		inputNrSali.getItems().addAll(listaSal);
		inputNrSali.getSelectionModel().select(0);

		kolumnaZaznaczProdukt
				.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, Boolean>("zaznaczonyProdukt"));
		kolumnaNrSali.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, Integer>("nrSali"));
		kolumnaNazwaProduktu.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, String>("nazwa"));
		kolumnaIloscProduktow.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, Integer>("ilosc"));

		listaProduktow.setItems(listaPrzedmiotowZamowienia);
	}

	/**
	 * Metoda dodajáca produkty do listy zamówienia
	 */
	@FXML
	private void dodajDoListyZamowien(ActionEvent event) {
		/**
		 * Utworzene produktu zamówienia na podstawie danch pobranch z
		 * formularza
		 */

		boolean nazwa = walidacjaFormularza.sprawdzCzyPoleFomrularzaPuste(inputNazwaProduktu, nazwaProduktuLabel,
				"*pole wymagene");
		boolean ilosc = walidacjaFormularza.sprawdzCzyPoleFomrularzaPuste(inputIloscProduktu, iloscLabel,
				"*pole wymagene");

		if (ilosc && nazwa) {
			ProduktZamowienia produktZamowienia = new ProduktZamowienia(inputNrSali.getValue(),
					inputNazwaProduktu.getText(), Integer.parseInt(inputIloscProduktu.getText()), new CheckBox());
			// Dodanie produktu do list zamówienia
			listaPrzedmiotowZamowienia.add(produktZamowienia);
			// Wywolanie metody czyszczácej pola formularza
			clearForm();
		}
	}

	private void clearForm() {

		inputNazwaProduktu.clear();
		inputIloscProduktu.clear();

	}

	/**
	 * Metoda usuwa zaznaczone elementy zapiszEdytowaneZamowienie listy
	 * produktów formularza
	 */
	@FXML
	private void usunZListyZamowien(ActionEvent event) {

		for (ProduktZamowienia produktZamowienia : listaPrzedmiotowZamowienia) {

			if (produktZamowienia.getZaznaczonyProdukt().isSelected() == true) {
				listaProduktowDoUsuniecia.addAll(produktZamowienia);
			}
		}
		for (ProduktZamowienia produktDoUsuniecia : listaProduktowDoUsuniecia) {
			listaPrzedmiotowZamowienia.remove(produktDoUsuniecia);

		}
	}

	@FXML
	/**
	 * Metoda wysy³a do bazy danych zamówienie
	 */

	private void utworzZamowienie(ActionEvent event) throws IOException {
		Pielegniarka pielegniarka = new Pielegniarka();
		pielegniarka = noweZamowieneiImpl.pobierzPielegnierkeWedlugID(1);
		noweZamowieneiImpl.utworzZamowienie(listaPrzedmiotowZamowienia, pielegniarka);
		Stage stage = (Stage) inputNazwaProduktu.getScene().getWindow();
		stage.close();
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/ListaZamowien.fxml", "Lista zamówieñ do sal");
	}

	public class ProduktZamowienia {

		private Sala sala;
		private String nazwa;
		private int ilosc;
		private CheckBox zaznaczonyProdukt;

		@Override
		public String toString() {
			return "ProduktZamowienia{" + "nrSali=" + sala + ", nazwa=" + nazwa + ", ilosc=" + ilosc
					+ ", zaznaczonyProdukt=" + zaznaczonyProdukt + '}';
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 59 * hash + (this.nazwa != null ? this.nazwa.hashCode() : 0);
			hash = 59 * hash + this.ilosc;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final ProduktZamowienia other = (ProduktZamowienia) obj;
			if (this.sala != other.sala) {
				return false;
			}
			if (this.ilosc != other.ilosc) {
				return false;
			}
			if ((this.nazwa == null) ? (other.nazwa != null) : !this.nazwa.equals(other.nazwa)) {
				return false;
			}
			if (this.zaznaczonyProdukt != other.zaznaczonyProdukt
					&& (this.zaznaczonyProdukt == null || !this.zaznaczonyProdukt.equals(other.zaznaczonyProdukt))) {
				return false;
			}
			return true;
		}

		public Sala getNrSali() {
			return sala;
		}

		public void setNrSali(Sala nrSali) {
			this.sala = nrSali;
		}

		public String getNazwa() {
			return nazwa;
		}

		public void setNazwa(String nazwa) {
			this.nazwa = nazwa;
		}

		public int getIlosc() {
			return ilosc;
		}

		public void setIlosc(int ilosc) {
			this.ilosc = ilosc;
		}

		public CheckBox getZaznaczonyProdukt() {
			return zaznaczonyProdukt;
		}

		public void setZaznaczonyProdukt(CheckBox zaznaczonyProdukt) {
			this.zaznaczonyProdukt = zaznaczonyProdukt;
		}

		public ProduktZamowienia(Sala sala, String nazwa, int ilosc, CheckBox zaznaczonyProdukt) {
			this.sala = sala;
			this.nazwa = nazwa;
			this.ilosc = ilosc;
			this.zaznaczonyProdukt = zaznaczonyProdukt;
		}
	}

}
