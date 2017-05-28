package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.Konto;
import model.repository.impl.KontoRepositoryImpl;

public class LoginController {

	@FXML
	private JFXTextField txtUsername;

	@FXML
	private JFXPasswordField txtPassword;

	@FXML
	private Label lblStatus;

	@FXML
	private Label lblStatus1;

	public int typ_konta;
	
	public static Konto kontoUzytkownika;

	@FXML
	void Login(ActionEvent event) throws IOException {
		KontoRepositoryImpl kontoRepository = new KontoRepositoryImpl();
		typ_konta = kontoRepository.zwrocenieTypuKonta(txtUsername.getText(), txtPassword.getText());
		kontoUzytkownika =kontoRepository.getKonto(txtUsername.getText(), txtPassword.getText());
		switch (typ_konta) {
		case 1:
			FXMLLoader fxmlLoader = new FXMLLoader();
			Parent root;
//			root = fxmlLoader.load(this.getClass().getResource("/fxml/ListaZamowien.fxml").openStream());
//			ListaZamowienController listaZamowienController = (ListaZamowienController) fxmlLoader
//					.getController();
//			listaZamowienController.haslo = txtPassword.getText();
//			listaZamowienController.login = txtUsername.getText();								
			kontoRepository.otwarcieNowejScenyZAdresu("/fxml/panelAdministracyjny.fxml", "Administrator");
			((Node) (event.getSource())).getScene().getWindow().hide();
			break;
		case 2:
			System.out.println(kontoUzytkownika.getLekarz().getID());
			kontoRepository.otwarcieNowejScenyZAdresu("/fxml/Wizyty.fxml", "Lista wizyt Pacjentów");
			((Node) (event.getSource())).getScene().getWindow().hide();
			break;
		case 3:
			((Node) (event.getSource())).getScene().getWindow().hide();
			// kontoRepository.wczytanieNowejScenyZPliku("/application/Pielegniarka.fxml");
			break;
		case 4:
			((Node) (event.getSource())).getScene().getWindow().hide();
			kontoRepository.otwarcieNowejScenyZAdresu("/fxml/panelRecepcjonistki.fxml", "Recepcjonistka");
			break;
		default:
			lblStatus.setText("B³êdna nazwa u¿ytkownika lub has³o");
			break;
	}
}
	@FXML
	void pamietaj(ActionEvent event) throws Exception {
		KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
		kontoRepositoryImpl.zapamietanieLoginuDoPliku("login.txt", txtUsername.getText());
	}

	@FXML
	void initialize() throws FileNotFoundException {
		KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
		txtUsername.setText(kontoRepositoryImpl.wczytanieLoginuZPliku("login.txt"));
	}

}
