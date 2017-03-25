package Controller;

import java.io.IOException;
import java.math.BigDecimal;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyAdministratoraImpl;

public class EdytujSpecjalizacjeController {

	@FXML
	private ChoiceBox<String> tabelaSpecjalizacji;

	@FXML
	private JFXTextField nazwaSpecjalizacji;

	@FXML
	private JFXTextField cenaWizyty;

	@FXML
	private JFXTextField czasEizyty;

	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();

	@FXML
	ChoiceBox tabelaZabiegow;

	@FXML
	public void initialize() {

		tabelaSpecjalizacji.getItems().addAll(metodyAdministratoraImpl.wypiszNazwySpecjalizacji());
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
	void wstecz(ActionEvent event) throws IOException {
		((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/Admin.fxml", "Administrator");

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

}
