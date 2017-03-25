package Controller;

import java.io.IOException;
import java.math.BigDecimal;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyAdministratoraImpl;



public class DodajSpecjalizacjeController {
	
	@FXML
	private JFXTextField nazwaSpecjalizacji;
	@FXML
	private JFXTextField kosztWizyty;
	@FXML
	private JFXTextField czasWizyty;

	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
	

	@FXML
	public void dodajSpecjalizacje(ActionEvent event){
		MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
		BigDecimal koszt = new BigDecimal(kosztWizyty.getText());
		Integer czas = new Integer(czasWizyty.getText());
		metodyAdministratoraImpl.dodajSpecjaliste(nazwaSpecjalizacji.getText(), koszt, czas);

				
	}
	
	@FXML
	public void wstecz(ActionEvent event) throws IOException{
		((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/Admin.fxml","Administrator");
	}
	

}
