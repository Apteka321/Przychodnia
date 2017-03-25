package Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyAdministratoraImpl;

public class DodajSaleController {

	@FXML
	private JFXButton wybierzSale;

	@FXML
	private ChoiceBox<Integer> listaSal;

	@FXML
	private JFXTextField numerSali;

	@FXML
	private JFXTextArea opisSali;

	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();

	@FXML
	void initialize() {
		listaSal.getItems().addAll(metodyAdministratoraImpl.wypiszWszystkieNazwySal());
		
	}

	@FXML
	void dodajSale(ActionEvent event) {
		Integer nrSali = new Integer(numerSali.getText());
		metodyAdministratoraImpl.DodajSale(nrSali, opisSali.getText());
	
		listaSal.getSelectionModel().clearSelection();
		listaSal.getItems().clear();
		listaSal.getItems().addAll(metodyAdministratoraImpl.wypiszWszystkieNazwySal());
		numerSali.setText("");
		opisSali.setText("");
		

	}

	@FXML
	void wybierzSale(ActionEvent event) {
		numerSali.setText(Integer.toString(metodyAdministratoraImpl.wybranegoSaliDoEdycji(listaSal)));
	}

	@FXML
	void anuluj(ActionEvent event) throws IOException {
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/Admin.fxml", "Administrator");	

	}

	@FXML
	void edytujSale(ActionEvent event) {
		Integer nrSali = new Integer(numerSali.getText());
		metodyAdministratoraImpl.edycjaSali(nrSali, opisSali.getText(), listaSal);

		listaSal.getSelectionModel().clearSelection();
		listaSal.getItems().clear();
		listaSal.getItems().addAll(metodyAdministratoraImpl.wypiszWszystkieNazwySal());
		numerSali.setText("");
		opisSali.setText("");

	}

	@FXML
	void usunSale(ActionEvent event) {
		metodyAdministratoraImpl.usunSale(listaSal);

		listaSal.getSelectionModel().clearSelection();
		listaSal.getItems().clear();
		listaSal.getItems().addAll(metodyAdministratoraImpl.wypiszWszystkieNazwySal());
		numerSali.setText("");
		opisSali.setText("");

	}

}
