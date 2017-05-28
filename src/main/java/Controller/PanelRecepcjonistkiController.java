package Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PanelRecepcjonistkiController {

	@FXML
	private JFXDrawer menu;

	@FXML
	private JFXButton rejestracjaPacjenta;

	@FXML
	private JFXButton planPracy;

	@FXML
	private JFXButton rejestracjaNaWIzyte;

	@FXML
	private JFXButton edytujWizyte;

	@FXML
	private JFXButton planowanieZabiegu;

	@FXML
	private JFXButton obslugaPlatnosci;

	@FXML
	private Pane wybranyModul;

	FXMLLoader loader = new FXMLLoader();
	private Pane rejestracjaPacjentaPane;
	private Pane rejestracjaPacjentaNaWizytePane;
	private Pane edycjaDanychPacjentaPane;
	private Pane edycjaWizytPane;
	private Pane obslugaPlatnosciPane;
	private Pane planowanieZabieguPane;

	private void wyswietlWybranyModul(Pane wybranyPane) {

		wybranyModul.getChildren().clear();
		wybranyModul.getChildren().setAll(wybranyPane.getChildren());

	}

	@FXML
	void dodajPacjenta(MouseEvent event) throws IOException {
		rejestracjaPacjentaPane = loader.load(this.getClass().getResource("/fxml/rejestracjaPacjenta.fxml"));
		wyswietlWybranyModul(rejestracjaPacjentaPane);
	}

	@FXML
	void edycjaDanychPacjenta(MouseEvent event) throws IOException {
		edycjaDanychPacjentaPane = loader.load(this.getClass().getResource("/fxml/ModyfikowanieDanychPacjenta.fxml"));
		wyswietlWybranyModul(edycjaDanychPacjentaPane);
	}

	@FXML
	void edycjaWizyt(MouseEvent event) throws IOException {
		edycjaWizytPane = loader.load(this.getClass().getResource("/fxml/ModyfikowanieZapranowanejWizyty.fxml"));
		wyswietlWybranyModul(edycjaWizytPane);
	}

	@FXML
	void rejestracjaNaWIzyte(MouseEvent event) throws IOException {
		rejestracjaPacjentaNaWizytePane = loader
				.load(this.getClass().getResource("/fxml/RejestracjaPacjentaNaWizyte.fxml"));
		wyswietlWybranyModul(rejestracjaPacjentaNaWizytePane);
	}


	@FXML
	void obslugaPlatnosci(MouseEvent event) throws IOException {
		obslugaPlatnosciPane = loader.load(this.getClass().getResource("/fxml/ObslugaPlatnosci.fxml"));
		wyswietlWybranyModul(obslugaPlatnosciPane);
	}

	@FXML
	void planowanieZabiegu(MouseEvent event) throws IOException {
		planowanieZabieguPane = loader.load(this.getClass().getResource("/fxml/PlanowanieZabiegow.fxml"));
		wyswietlWybranyModul(planowanieZabieguPane);
	}


}
