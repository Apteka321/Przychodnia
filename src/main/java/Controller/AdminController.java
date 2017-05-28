package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import model.repository.impl.KontoRepositoryImpl;

public class AdminController {
	
	KontoRepositoryImpl kontoRepository = new KontoRepositoryImpl();
	public void edytujZabieg(ActionEvent event) throws IOException
	{
		((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepository.otwarcieNowejScenyZAdresu("/fxml/EdytujZabieg.fxml", "Zabieg");		   
	}
    public void edytujSpecjalizacje(ActionEvent event) throws IOException {
    	((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepository.otwarcieNowejScenyZAdresu("/fxml/EdytujSpecjalizacje.fxml", "Specjalizacja");		
    }

    public void dodajSale(ActionEvent event) throws IOException {
    	((Node) (event.getSource())).getScene().getWindow().hide();
		kontoRepository.otwarcieNowejScenyZAdresu("/fxml/DodajSale.fxml", "Dodaj sale");
    }

}
