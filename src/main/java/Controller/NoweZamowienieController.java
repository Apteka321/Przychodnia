/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Pielegniarka;
import model.repository.impl.KontoRepositoryImpl;
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
	private TextField inputNrSali;
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
	 * Pobranie produkt�w do listy zam�wienia
	 */
	final ObservableList<ProduktZamowienia> listaPrzedmiotowZamowienia = FXCollections.observableArrayList(
			new ProduktZamowienia(1, "Czopki extra", 10, new CheckBox()),
			new ProduktZamowienia(1, "Czopki junior", 100, new CheckBox()));
	@FXML
	private JFXButton utworzZamowienieButton;

	/**
	 * Mapowanie pol kalsy ProduktZamowienia zapiszEdytowaneZamowienie
	 * warto�cami w odpowiadoaj�cym im kolumnom tabeli formularza zam�wienia
	 *
	 * @param event
	 */
	public void initialize(URL url, ResourceBundle rb) {
		kolumnaZaznaczProdukt
				.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, Boolean>("zaznaczonyProdukt"));
		kolumnaNrSali.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, Integer>("nrSali"));
		kolumnaNazwaProduktu.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, String>("nazwa"));
		kolumnaIloscProduktow.setCellValueFactory(new PropertyValueFactory<ProduktZamowienia, Integer>("ilosc"));

		listaProduktow.setItems(listaPrzedmiotowZamowienia);

	}

	/**
	 * Metoda dodaj�ca produkty do listy zam�wienia
	 */
	@FXML
	private void dodajDoListyZamowien(ActionEvent event) {
		/**
		 * Utworzene produktu zam�wienia na podstawie danch pobranch z
		 * formularza
		 */

		boolean sala = walidacjaFormularza.sprawdzCzyPoleFomrularzaPuste(inputNrSali, nrSaliLabel, "*pole wymagene");
		boolean nazwa = walidacjaFormularza.sprawdzCzyPoleFomrularzaPuste(inputNazwaProduktu, nazwaProduktuLabel,
				"*pole wymagene");
		boolean ilosc = walidacjaFormularza.sprawdzCzyPoleFomrularzaPuste(inputIloscProduktu, iloscLabel,
				"*pole wymagene");

		if (sala && ilosc && nazwa) {
			ProduktZamowienia produktZamowienia = new ProduktZamowienia(Integer.parseInt(inputNrSali.getText()),
					inputNazwaProduktu.getText(), Integer.parseInt(inputIloscProduktu.getText()), new CheckBox());
			// Dodanie produktu do list zam�wienia
			listaPrzedmiotowZamowienia.add(produktZamowienia);
			// Wywolanie metody czyszcz�cej pola formularza
			clearForm();
		}
	}

	private void clearForm() {

		inputNrSali.clear();
		inputNazwaProduktu.clear();
		inputIloscProduktu.clear();

	}

	/**
	 * Metoda usuwa zaznaczone elementy zapiszEdytowaneZamowienie listy
	 * produkt�w formularza
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
	 * Metoda wysy�a do bazy danych zam�wienie
	 */

	private void utworzZamowienie(ActionEvent event) throws IOException {
		Pielegniarka pielegniarka = new Pielegniarka();
		pielegniarka = noweZamowieneiImpl.pobierzPielegnierkeWedlugID(1);
		noweZamowieneiImpl.utworzZamowienie(listaPrzedmiotowZamowienia, pielegniarka);
		((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/ListaZamowien.fxml", "Lista zam�wie� do sal");
	}

	public class ProduktZamowienia {

		private int nrSali;
		private String nazwa;
		private int ilosc;
		private CheckBox zaznaczonyProdukt;

		@Override
		public String toString() {
			return "ProduktZamowienia{" + "nrSali=" + nrSali + ", nazwa=" + nazwa + ", ilosc=" + ilosc
					+ ", zaznaczonyProdukt=" + zaznaczonyProdukt + '}';
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 59 * hash + this.nrSali;
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
			if (this.nrSali != other.nrSali) {
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

		public int getNrSali() {
			return nrSali;
		}

		public void setNrSali(int nrSali) {
			this.nrSali = nrSali;
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

		public ProduktZamowienia(int nrSali, String nazwa, int ilosc, CheckBox zaznaczonyProdukt) {
			this.nrSali = nrSali;
			this.nazwa = nazwa;
			this.ilosc = ilosc;
			this.zaznaczonyProdukt = zaznaczonyProdukt;
		}
	}

}
