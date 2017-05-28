/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.HibernateUtil;
import model.Konto;
import model.Wizyta;
import model.repository.Wizyty;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.RaportImpl;
import model.repository.impl.WizytyImpl;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class WizytyController implements Initializable {

    public static String PESEL;
    KontoRepositoryImpl kontoRepo = new KontoRepositoryImpl();

    public WizytyImpl wizyty = new WizytyImpl();

    public RaportImpl metodyRaportow = new RaportImpl();
    @FXML
    TableView<pacjentWizyta> listaPacjentow;

    @FXML
    private TableColumn<pacjentWizyta, String> kolumnaPesel;

    @FXML
    private TableColumn<pacjentWizyta, String> kolumnaImie;

    @FXML
    private TableColumn<pacjentWizyta, String> kolumnaNazwisko;

    @FXML
    private TableColumn<pacjentWizyta, Time> kolumnaGodzinaWizyty;

    public ObservableList<pacjentWizyta> listaPacjentowNaWizyte;

    public static List<Wizyta> listaPacjentowDoSpecjalisty;

    public static pacjentWizyta pacjentNaWizyte;
    @FXML
    private Label dataWizytyLabel;
    @FXML
    private DatePicker timePicker;
    @FXML
    private JFXButton pokazWizytyButton;

    
    public void initialize(URL location, ResourceBundle resources) {

//        ReferencjaKartaPacjenta.init(this);
        kolumnaPesel.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, String>("pesel"));
        kolumnaImie.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, String>("imie"));
        kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, String>("nazwisko"));
        kolumnaGodzinaWizyty.setCellValueFactory(new PropertyValueFactory<pacjentWizyta, Time>("godzina"));

        listaPacjentowNaWizyte = FXCollections.observableArrayList();
        wizyty.pobierzListeWizytWedugDaty(new java.sql.Date(2017));


        for (Wizyta wizyta : listaPacjentowDoSpecjalisty) {
            if (wizyta.getID_lekarza().getID() == LoginController.kontoUzytkownika.getLekarz().getID()) {
                pacjentWizyta pWizyta = new pacjentWizyta(wizyta.getPESEL_pacjenta().getPESEL(),
                        wizyta.getPESEL_pacjenta().getOsoba().getImie(),
                        wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(), wizyta.getGodzina());
                listaPacjentowNaWizyte.add(pWizyta);
            }
            
        }
        
        System.out.println(listaPacjentowDoSpecjalisty);
        listaPacjentow.setItems(listaPacjentowNaWizyte);

        listaPacjentow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {

                            pacjentNaWizyte = listaPacjentow.getSelectionModel().getSelectedItem();
                            Stage stage = (Stage) listaPacjentow.getScene().getWindow();
                            stage.close();
                            kontoRepo.otwarcieNowejScenyZAdresu("/fxml/Gabinet.fxml", "Gabinet");
                        } catch (IOException ex) {
                        }
                    }
                }
            }
        });

    }

    @FXML
    private void pokazWizyty(ActionEvent event) {
       String dataWiztyty = (timePicker.getValue().toString());

        for (pacjentWizyta wizyta : listaPacjentowNaWizyte) {           
                listaPacjentow.getItems().removeAll(listaPacjentowNaWizyte);
                listaPacjentow.refresh();          
        }

        Date data = metodyRaportow.zamianaStringData(dataWiztyty);
        pacjentWizyta pWizyta = null;

        List<Wizyta> listaWizytNaWybranyDzien = wizyty.pobierzListeWizytWedugDaty(data);
        for (Wizyta wizyta : listaPacjentowDoSpecjalisty) {

            if (wizyta.getID_lekarza().getID() == LoginController.kontoUzytkownika.getLekarz().getID()) {
                pWizyta = new pacjentWizyta(wizyta.getPESEL_pacjenta().getPESEL(),
                        wizyta.getPESEL_pacjenta().getOsoba().getImie(),
                        wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(), wizyta.getGodzina());
                listaPacjentowNaWizyte.add(pWizyta);
                
                          
            }
            listaPacjentow.setItems(listaPacjentowNaWizyte);
            listaPacjentow.refresh();
        }

    }

//    @FXML
//    private void dodajWizyte(ActionEvent event) throws IOException {
//        Stage stage = (Stage) dodajWizyteButton.getScene().getWindow();
//        stage.close();
//        kontoRepo.otwarcieNowejScenyZAdresu("/fxml/Gabinet.fxml", "Gabinet");
//
//    }
    public class pacjentWizyta {

        String pesel;
        String imie;
        String nazwisko;
        Time godzina;

        public pacjentWizyta(String pesel, String imie, String nazwisko, Time godzina) {
            super();
            this.pesel = pesel;
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.godzina = godzina;
        }

        public String getPesel() {
            return pesel;
        }

        public void setPesel(String pesel) {
            this.pesel = pesel;
        }

        public String getImie() {
            return imie;
        }

        public void setImie(String imie) {
            this.imie = imie;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public void setNazwisko(String nazwisko) {
            this.nazwisko = nazwisko;
        }

        public Time getGodzina() {
            return godzina;
        }

        public void setGodzina(Time godzina) {
            this.godzina = godzina;
        }

    }

}
