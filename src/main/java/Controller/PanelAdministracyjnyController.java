package Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelAdministracyjnyController {
	FXMLLoader loader = new FXMLLoader();
	private Pane rejestracjaPracownikowPane;
	private Pane modyfikacjaPracownikowPane;
	private Pane zarzadzajSpecjalizacjamiPane;
	private Pane zarzadzajPlanamiPracyPane;
	private Pane zarzadzajUrlopamiPane;
	private Pane zarzadzajSalamiPane;
	private Pane zarzadzajLekamiPane;
	private Pane obslugaZamowienPane;
	private Pane zabiegiPane;
	private Pane raportyPane;

	private Pane poprzedniPane;

	@FXML
	public void initialize() throws IOException {


	}

	@FXML
	private JFXDrawer menu;

	@FXML
	private JFXButton dodajPracownika;

	@FXML
	private JFXButton modyfikujPracownika;

	@FXML
	private JFXButton specjalizacje;

	@FXML
	private JFXButton zabiegi;

	@FXML
	private JFXButton planPracy;

	@FXML
	private JFXButton wolne;

	@FXML
	private JFXButton sale;

	@FXML
	private JFXButton leki;

	@FXML
	private JFXButton zamowienia;

	@FXML
	private JFXButton raporty;

	@FXML
	private Pane wybranyModul;

	private void wyswietlWybranyModul(Pane wybranyPane) {
		wybranyModul.getChildren().clear();
		wybranyModul.getChildren().setAll(wybranyPane.getChildren());

	}

	@FXML
	void dodajPracownika(MouseEvent event) throws IOException {
		rejestracjaPracownikowPane = loader.load(this.getClass().getResource("/fxml/rejestracjaPracownikow.fxml"));
		wyswietlWybranyModul(rejestracjaPracownikowPane);
	}

	@FXML
	void leki(MouseEvent event) throws IOException {
		zarzadzajLekamiPane = loader.load(this.getClass().getResource("/fxml/zarzadzanieLekami.fxml"));
		wyswietlWybranyModul(zarzadzajLekamiPane);
	}

	@FXML
	void modyfikujPracownika(MouseEvent event) throws IOException {
		modyfikacjaPracownikowPane = loader.load(this.getClass().getResource("/fxml/modyfikacjaPracownikow.fxml"));
		wyswietlWybranyModul(modyfikacjaPracownikowPane);
	}

	@FXML
	void planyPracy(MouseEvent event) throws IOException {
		zarzadzajPlanamiPracyPane = loader.load(this.getClass().getResource("/fxml/tworzeniePlanuPracy.fxml"));
		wyswietlWybranyModul(zarzadzajPlanamiPracyPane);
	}

	@FXML
	void raporty(MouseEvent event) throws IOException {
		zarzadzajPlanamiPracyPane = loader.load(this.getClass().getResource("/fxml/Raporty.fxml"));
		wyswietlWybranyModul(zarzadzajPlanamiPracyPane);
	}

	@FXML
	void sale(MouseEvent event) throws IOException {
		zarzadzajSalamiPane = loader.load(this.getClass().getResource("/fxml/dodajSale.fxml"));
		wyswietlWybranyModul(zarzadzajSalamiPane);
	}

	@FXML
	void specjalizacje(MouseEvent event) throws IOException {
		zarzadzajSpecjalizacjamiPane = loader.load(this.getClass().getResource("/fxml/edytujSpecjalizacje.fxml"));
		wyswietlWybranyModul(zarzadzajSpecjalizacjamiPane);
	}

	@FXML
	void zabiegi(MouseEvent event) throws IOException {
		zabiegiPane = loader.load(this.getClass().getResource("/fxml/edytujZabieg.fxml"));
		wyswietlWybranyModul(zabiegiPane);
	}

	@FXML
	void wolne(MouseEvent event) throws IOException {
		zarzadzajUrlopamiPane = loader.load(this.getClass().getResource("/fxml/tworzenieUrlopow.fxml"));
		wyswietlWybranyModul(zarzadzajUrlopamiPane);
	}

	@FXML
	void zamowienia(MouseEvent event) throws IOException {
		obslugaZamowienPane = loader.load(this.getClass().getResource("/fxml/ListaZamowien.fxml"));
		wyswietlWybranyModul(obslugaZamowienPane);
	}
}
