package Controller;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import model.repository.impl.MetodyAdministratoraImpl;

public class EdycjaDanychPracownikController {
	

    @FXML
    private JFXTextField poczta;
   
    @FXML
    private JFXTextField imie;

    @FXML
    private JFXTextField nazwisko;

    @FXML
    private JFXTextField miejscowosc;

    @FXML
    private JFXTextField numerDomu;

    @FXML
    private JFXTextField ulica;

    @FXML
    private JFXTextField kodPocztowy;

    @FXML
    private JFXTextField numerTelefonu;
    @FXML
    private ChoiceBox<String> typKonta;
    @FXML
    private ChoiceBox<String> dane;
	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
	/*
    public void initialize()
    {
		final List<String> dostepneTypyKont = new ArrayList<String>();
		dostepneTypyKont.add("Administrator");
		dostepneTypyKont.add("Lekarza");
		dostepneTypyKont.add("Pielêgniarke");
		dostepneTypyKont.add("Recepcjonistka");
	
		typKonta.getItems().addAll(dostepneTypyKont);
		typKonta.getSelectionModel().select(1);
		typKonta.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				int wybranyTypRejestrowanegoKonta;
				if (newValue == null) {
					// pierwsze klikniêcie
					wybranyTypRejestrowanegoKonta = (Integer) oldValue;
				} else {
					wybranyTypRejestrowanegoKonta = (Integer) newValue;
				}

				//rejestrowaneKonto = wybranyTypRejestrowanegoKonta + 1;

				switch (wybranyTypRejestrowanegoKonta) {
				case 0:
					
					
					break;

				case 1:
					//dane.getItems().addAll();
					System.out.println(metodyAdministratoraImpl.wypiszLekarzy());
					break;

				case 2:
					//wyswietlFormularzRejestracyjnyPracownika();
					break;

				case 3:
					//wyswietlFormularzRejestracyjnyPracownika();
					break;
				}
			}
		});
    }	
			*/
  
			
		
}
