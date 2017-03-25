package Controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.acl.Permission;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.swing.RootPaneContainer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.omg.IOP.ENCODING_CDR_ENCAPS;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.HibernateUtil;
import model.Konto;
import model.repository.impl.KontoRepositoryImpl;

import org.hibernate.*;
import java.util.*;

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

    @FXML
    void Login(ActionEvent event) throws IOException {
    	KontoRepositoryImpl kontoRepository = new KontoRepositoryImpl();
		typ_konta = kontoRepository.zwrocenieTypuKonta(txtUsername.getText(), txtPassword.getText());
		switch (typ_konta) {
		case 1:
			((Node) (event.getSource())).getScene().getWindow().hide();
			 kontoRepository.otwarcieNowejScenyZAdresu("/fxml/Admin.fxml","Logowanie");
	      

	    	   
			break;
		case 2:
			((Node) (event.getSource())).getScene().getWindow().hide();
			
			break;
		case 3:
			((Node) (event.getSource())).getScene().getWindow().hide();
			//kontoRepository.wczytanieNowejScenyZPliku("/application/Pielegniarka.fxml");
			break;
		case 4:
			((Node) (event.getSource())).getScene().getWindow().hide();
			//kontoRepository.wczytanieNowejScenyZPliku("/application/Recepcjonistka.fxml");
			break;

		default:
			lblStatus.setText("B³êdna nazwa u¿ytkownika lub has³o");
			break;
		}

    }
	

	@FXML
	void pamietaj(ActionEvent event) throws Exception{ 
		KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
		kontoRepositoryImpl.zapamietanieLoginuDoPliku("login.txt", txtUsername.getText()); 
	}
	
	@FXML
	void initialize() throws FileNotFoundException {
		KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
		txtUsername.setText( kontoRepositoryImpl.wczytanieLoginuZPliku("login.txt"));
	}
	
}
