package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import View.Komunikaty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelAdministracyjnyController {
	private Pane rejestracjaPracownikowPane;
	private Pane modyfikacjaPracownikowPane;
	private Pane zarzadzajSpecjalizacjamiPane;
	private Pane zarzadzajPlanamiPracyPane;
	private Pane zarzadzajUrlopamiPane;
	private Pane zarzadzajSalamiPane;
	private Pane zarzadzajLekamiPane;
	private Pane obslugaZamowienPane;
	private Pane raportyPane;

	@FXML
	public void initialize() {
		FXMLLoader loader = new FXMLLoader();
		try {
			rejestracjaPracownikowPane = loader.load(this.getClass().getResource("/fxml/rejestracjaPracownikow.fxml"));
			modyfikacjaPracownikowPane = loader.load(this.getClass().getResource("/fxml/modyfikacjaPracownikow.fxml"));
			zarzadzajPlanamiPracyPane = loader.load(this.getClass().getResource("/fxml/tworzeniePlanuPracy.fxml"));
			zarzadzajUrlopamiPane = loader.load(this.getClass().getResource("/fxml/tworzenieUrlopow.fxml"));
			zarzadzajLekamiPane = loader.load(this.getClass().getResource("/fxml/zarzadzanieLekami.fxml"));
			zarzadzajSpecjalizacjamiPane = loader.load(this.getClass().getResource("/fxml/EdytujSpecjalizacje.fxml"));
			zarzadzajSalamiPane = loader.load(this.getClass().getResource("/fxml/DodajSale.fxml"));
			// obslugaZamowienPane =
			// loader.load(this.getClass().getResource("/fxml/DodajSale.fxml"));

			System.out.println("ok");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d!", "Nie mo¿na wczytaæ pliku!");
			e.printStackTrace();
		}

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
	void dodajPracownika(MouseEvent event) {
		wyswietlWybranyModul(rejestracjaPracownikowPane);
	}

	@FXML
	void leki(MouseEvent event) {
		wyswietlWybranyModul(zarzadzajLekamiPane);
	}

	@FXML
	void modyfikujPracownika(MouseEvent event) {
		wyswietlWybranyModul(modyfikacjaPracownikowPane);
	}

	@FXML
	void planyPracy(MouseEvent event) {
		wyswietlWybranyModul(zarzadzajPlanamiPracyPane);
	}

	@FXML
	void raporty(MouseEvent event) {

	}

	@FXML
	void sale(MouseEvent event) {
		wyswietlWybranyModul(zarzadzajSalamiPane);
	}

	@FXML
	void sspecjalizacje(MouseEvent event) {
		wyswietlWybranyModul(zarzadzajSpecjalizacjamiPane);
	}

	@FXML
	void wolne(MouseEvent event) {
		wyswietlWybranyModul(zarzadzajUrlopamiPane);
	}

	@FXML
	void zamowienia(MouseEvent event) {
		wyswietlWybranyModul(obslugaZamowienPane);
	}
}
