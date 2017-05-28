package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;
import org.omg.CORBA.PRIVATE_MEMBER;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javassist.compiler.ast.NewExpr;
import model.HibernateUtil;
import model.Lista_zabiegow;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyAdministratoraImpl;

import java.net.URL;
import org.xml.sax.SAXException;

public class EdytujZabiegController {

	@FXML
	private ChoiceBox<String> tabelaZabiegow;
	@FXML
	private JFXTextField nazwaZabiegu;

	@FXML
	private JFXTextField cenaZabiegu;

	private ObservableList<Lista_zabiegow> lista_Zabiegow = FXCollections.observableArrayList();
	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
	@FXML
	public void initialize() {
		
		tabelaZabiegow.getItems().addAll(metodyAdministratoraImpl.wypiszNazweZabiegu());
	}

	@FXML
	 void wybranieOpcji(ActionEvent event) {
		nazwaZabiegu.setText(metodyAdministratoraImpl.wybranegoZabieguDoEdycji(tabelaZabiegow));
	}

    @FXML
    void edytujZabieg(ActionEvent event) {
    	metodyAdministratoraImpl.edytujZabieg(nazwaZabiegu.getText(), cenaZabiegu.getText(),tabelaZabiegow);
    	tabelaZabiegow.getSelectionModel().clearSelection();
    	tabelaZabiegow.getItems().clear();
    	tabelaZabiegow.getItems().addAll(metodyAdministratoraImpl.wypiszNazweZabiegu());
    	nazwaZabiegu.setText("");
    	cenaZabiegu.setText("");

    }
    
    @FXML
    void usunZabieg(ActionEvent event) {
    	metodyAdministratoraImpl.usuwanieZabiegu(tabelaZabiegow);
    	tabelaZabiegow.getSelectionModel().clearSelection();
    	tabelaZabiegow.getItems().clear();
    	tabelaZabiegow.getItems().addAll(metodyAdministratoraImpl.wypiszNazweZabiegu());
    }

    @FXML
    void wstecz(ActionEvent event) throws IOException {
    
    	((Node) (event.getSource())).getScene().getWindow().hide();
    	kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/Admin.fxml","Administrator");

    }
    @FXML
    void dodajZabieg(ActionEvent event) {
    	MetodyAdministratoraImpl metodyAdministratoraImpl = new MetodyAdministratoraImpl();
    	
		BigDecimal koszt = new BigDecimal(cenaZabiegu.getText());
		metodyAdministratoraImpl.dodajZabieg(nazwaZabiegu.getText(), koszt);
		
		tabelaZabiegow.getSelectionModel().clearSelection();
    	tabelaZabiegow.getItems().clear();
    	tabelaZabiegow.getItems().addAll(metodyAdministratoraImpl.wypiszNazweZabiegu());
    	nazwaZabiegu.setText("");
    	cenaZabiegu.setText("");

    }
	

}
