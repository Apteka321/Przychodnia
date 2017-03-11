package Controller;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class registerController {

	public int typRejestrowanegoKonta;

	public registerController() {
		System.out.println("dziala");
	}

	@FXML
	void initialize() {

		final List<String> dostepneTypyKont = new ArrayList<String>();
		dostepneTypyKont.add("Administrator");
		dostepneTypyKont.add("Lekarz");
		dostepneTypyKont.add("Pielêgniarka");
		dostepneTypyKont.add("Recepcjonistka");
		typKonta.getItems().addAll(dostepneTypyKont);
		typKonta.setTooltip(new Tooltip("Wybierz typ tworzonego konta"));

		// listener do obs³ugi wyboru typu konta
		typKonta.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// pierwsze wybranie opcji
				if (newValue == null) {
					typRejestrowanegoKonta = (Integer) oldValue;
				} else {
					typRejestrowanegoKonta = (Integer) newValue;
				}

				System.out.println(typRejestrowanegoKonta + " " + dostepneTypyKont.get(typRejestrowanegoKonta));

				switch (typRejestrowanegoKonta) {
				case 0: {
					System.out.println("Wybrales Administratora");
					break;
				}
				case 1: {
					System.out.println("Wybrales Lekarza!");
					break;
				}
				case 2: {
					System.out.println("Wybrales Pielegniarke!");
					zarejestrujPielegniarke();
					break;
				}
				case 3: {
					System.out.println("Wybrales Recepcjonistke!");
					break;
				}
				}
			}
		});
	}

	void zarejestrujPielegniarke() {
		
	}

	@FXML
	private ChoiceBox<String> typKonta;

	@FXML
	private TextField imie;

	@FXML
	private TextField naziwsko;

	@FXML
	private TextField miejscowosc;

	@FXML
	private TextField numerDomu;

	@FXML
	private TextField ulica;

	@FXML
	private TextField poczta;

	@FXML
	private TextField numerTelefonu;

	@FXML
	private TextField login;

	@FXML
	private TextField haslo;

	@FXML
	private TextField kodPocztowy;

	@FXML
	private Button reset;

	@FXML
	private Button zarejestruj;

}
