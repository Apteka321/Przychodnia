/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.*;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Wizyta;
import model.repository.impl.GabinetImpl;
import static Controller.WizytyController.listaPacjentowDoSpecjalisty;
import static Controller.WizytyController.pacjentNaWizyte;
import View.Komunikaty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.HibernateUtil;
import model.Produkt;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.WizytyImpl;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class GabinetController implements Initializable {

    KontoRepositoryImpl kontoRepo = new KontoRepositoryImpl();
    public static int idHistoriWizytyPacjenta;
   
    @FXML
    private Label imieLabel;
    @FXML
    private Label nazwiskoLabel;
    @FXML
    private Label dataWizytyLabel;
    @FXML
    private Label peselLabel;
    @FXML
    private TableView<TabelaHistoriWizyt> tabelaHistoriWizytPacjenta;
    @FXML
    private TableColumn<TabelaHistoriWizyt, Integer> kolumnaIdWizyty;
    @FXML
    private TableColumn<TabelaHistoriWizyt, String> kolumnaImie;
    @FXML
    private TableColumn<TabelaHistoriWizyt, String> kolumnaNazwisko;
    @FXML
    private TableColumn<TabelaHistoriWizyt, Date> kolumnaDataWizyty;
    @FXML
    private TableColumn<TabelaHistoriWizyt, Time> kolumnaGodzinaWizyty;
    @FXML
    private TableColumn<TabelaHistoriWizyt, String> kolumnaSpecjalizacja;

    /**
     * Initializes the controller class.
     */
    final ObservableList<TabelaHistoriWizyt> historiaPacjenta = FXCollections.observableArrayList();

    GabinetImpl gabinetimpl = new GabinetImpl();
    @FXML
    private JFXButton buttonZapiszWizyte;
    @FXML
    private Label imiePacjentaLabel;
    @FXML
    private Label nazwiskoPacjentaLabel;
    @FXML
    private Label miejscowoscLabel;
    @FXML
    private Label numerDomuLabel;
    @FXML
    private Label ulicaLabel;
    @FXML
    private Label kodPocztowyLabel;
    @FXML
    private Label nrTelefonuLabel;
    @FXML
    private TextArea objawyArea;
    @FXML
    private TextArea wywiadArea;
    int idWizyty;

    WizytyImpl historiaWizyty = new WizytyImpl();
    Wizyta wizytaPacjenta = new Wizyta();
List<Wizyta> historiaWizytPacjenta = null;
    @FXML
    private JFXButton drukujRecepteButton;
    @FXML
    private JFXButton drukujSkierowanieButton;
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println(listaPacjentowDoSpecjalisty);
        System.out.println("Controller.GabinerKartaPacjentaController.initialize()");
        List<Wizyta> lista = listaPacjentowDoSpecjalisty;
        WizytyController.pacjentWizyta pacjent = pacjentNaWizyte;
        

        for (Wizyta wizyta : lista) {
            imieLabel.setText(pacjent.getImie());
            nazwiskoLabel.setText(pacjent.getNazwisko());
            dataWizytyLabel.setText(wizyta.getData().toString());
            peselLabel.setText(pacjent.getPesel());
            historiaWizytPacjenta = gabinetimpl.pobierzHistorieWizytPacjenta(pacjent.getPesel());

        }
        
        
        for (Wizyta wizyta : lista) {
            imieLabel.setText(pacjent.getImie());
            nazwiskoLabel.setText(pacjent.getNazwisko());
            dataWizytyLabel.setText(wizyta.getData().toString());
            peselLabel.setText(pacjent.getPesel());
            historiaWizytPacjenta = gabinetimpl.pobierzHistorieWizytPacjenta(pacjent.getPesel());

        }

        kolumnaIdWizyty.setCellValueFactory(new PropertyValueFactory<TabelaHistoriWizyt, Integer>("kolumnaIdWizyty"));
        kolumnaImie.setCellValueFactory(new PropertyValueFactory<TabelaHistoriWizyt, String>("kolumnaImie"));
        kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<TabelaHistoriWizyt, String>("kolumnaNazwisko"));
        kolumnaDataWizyty.setCellValueFactory(new PropertyValueFactory<TabelaHistoriWizyt, Date>("kolumnaDataWizyty"));
        kolumnaGodzinaWizyty.setCellValueFactory(new PropertyValueFactory<TabelaHistoriWizyt, Time>("kolumnaGodzinaWizyty"));
        kolumnaSpecjalizacja.setCellValueFactory(new PropertyValueFactory<TabelaHistoriWizyt, String>("kolumnaSprcjalizacja"));

        for (Wizyta wizyta : historiaWizytPacjenta) {

            imiePacjentaLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getImie());
            nazwiskoPacjentaLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getNazwisko());
            miejscowoscLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getMiejscowosc());
            numerDomuLabel.setText(Integer.toString(wizyta.getPESEL_pacjenta().getOsoba().getNumer_domu()));
            kodPocztowyLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getKod_pocztowy());
            ulicaLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getUlica());
            nrTelefonuLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getNumer_telefonu());
            nazwiskoPacjentaLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getNazwisko());

            if (objawyArea.getText() == null || objawyArea.getText().trim().isEmpty()) {
                objawyArea.setText(wizyta.getDiagnoza());
            }
            if (wywiadArea.getText() == null || wywiadArea.getText().trim().isEmpty()) {
                wywiadArea.setText(wizyta.getObjawy());
            }

            wizytaPacjenta = wizyta;

            TabelaHistoriWizyt listaWizytPacjenta = new TabelaHistoriWizyt(wizyta.getID(),
                    wizyta.getID_lekarza().getOsoba().getImie(),
                    wizyta.getID_lekarza().getOsoba().getNazwisko(),
                    wizyta.getData(),
                    wizyta.getGodzina(),
                    wizyta.getID_Specjalizacji().getNazwa());
            historiaPacjenta.add(listaWizytPacjenta);

        }
        tabelaHistoriWizytPacjenta.setItems(historiaPacjenta);
        
                tabelaHistoriWizytPacjenta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {

                            idHistoriWizytyPacjenta = tabelaHistoriWizytPacjenta.getSelectionModel().getSelectedItem().getKolumnaIdWizyty();
                            Wizyta wizyta =historiaWizyty.pobierzWizytePoId(idWizyty);                          
                            kontoRepo.otwarcieNowejScenyZAdresu("/fxml/HistoriaWizyty.fxml", "Historia wizyty Pacjenta");
                        } catch (IOException ex) {
                        }
                    }
                }
            }
        });


    }

    @FXML
    private void zapiszWizyte(ActionEvent event) {
        
        try {
        String objawy;
        String diagnoza;
        objawy = objawyArea.getText();
        diagnoza = wywiadArea.getText();
        Wizyta wizytaDoZapisania = new Wizyta();
        wizytaDoZapisania = wizytaPacjenta;
        wizytaDoZapisania.setObjawy(objawy);
        wizytaDoZapisania.setDiagnoza(diagnoza);
        gabinetimpl.zapiszWizyte(wizytaDoZapisania);
            Komunikaty.wyswietlInformacje("Zapisano", "Wizyta zapisana");
        } catch (Exception e) {
            Komunikaty.wyswietlOstrzezenie("B³¹d zapsu", "Nie mo¿na zapisaæ wizyty");
        }
        

    }

    @FXML
    private void drukujRecepte(ActionEvent event) throws IOException {
           kontoRepo.otwarcieNowejScenyZAdresu("/fxml/wystawRecepte.fxml", "Recepta");
    }
    
    @FXML
    private void drukujSkierowanie(ActionEvent event) throws IOException {
         kontoRepo.otwarcieNowejScenyZAdresu("/fxml/wystawSkierowanie.fxml", "Skierowanie");
    }
    
  
 

    public class TabelaHistoriWizyt {

        private int kolumnaIdWizyty;

        private String kolumnaImie;

        private String kolumnaNazwisko;

        private Date kolumnaDataWizyty;

        private Time kolumnaGodzinaWizyty;

        private String kolumnaSprcjalizacja;

        public TabelaHistoriWizyt(int kolumnaIdWizyty, String kolumnaImie, String kolumnaNazwisko, Date kolumnaDataWizyty, Time kolumnaGodzinaWizyty, String kolumnaSprcjalizacja) {
            this.kolumnaIdWizyty = kolumnaIdWizyty;
            this.kolumnaImie = kolumnaImie;
            this.kolumnaNazwisko = kolumnaNazwisko;
            this.kolumnaDataWizyty = kolumnaDataWizyty;
            this.kolumnaGodzinaWizyty = kolumnaGodzinaWizyty;
            this.kolumnaSprcjalizacja = kolumnaSprcjalizacja;
        }

        public int getKolumnaIdWizyty() {
            return kolumnaIdWizyty;
        }

        public void setKolumnaIdWizyty(int kolumnaIdWizyty) {
            this.kolumnaIdWizyty = kolumnaIdWizyty;
        }

        public String getKolumnaImie() {
            return kolumnaImie;
        }

        public void setKolumnaImie(String kolumnaImie) {
            this.kolumnaImie = kolumnaImie;
        }

        public String getKolumnaNazwisko() {
            return kolumnaNazwisko;
        }

        public void setKolumnaNazwisko(String kolumnaNazwisko) {
            this.kolumnaNazwisko = kolumnaNazwisko;
        }

        public Date getKolumnaDataWizyty() {
            return kolumnaDataWizyty;
        }

        public void setKolumnaDataWizyty(Date kolumnaDataWizyty) {
            this.kolumnaDataWizyty = kolumnaDataWizyty;
        }

        public Time getKolumnaGodzinaWizyty() {
            return kolumnaGodzinaWizyty;
        }

        public void setKolumnaGodzinaWizyty(Time kolumnaGodzinaWizyty) {
            this.kolumnaGodzinaWizyty = kolumnaGodzinaWizyty;
        }

        public String getKolumnaSprcjalizacja() {
            return kolumnaSprcjalizacja;
        }

        public void setKolumnaSprcjalizacja(String kolumnaSprcjalizacja) {
            this.kolumnaSprcjalizacja = kolumnaSprcjalizacja;
        }

    }

}
