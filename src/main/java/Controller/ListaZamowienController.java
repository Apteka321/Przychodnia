/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Produkt_Zamowienia;
import model.Zamowienia;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.ZamowienieImpl;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class ListaZamowienController implements Initializable {

    /**
     * Obiekty Tabeli
     *
     * @author Daniel
     */
    @FXML
    public TableView<PozycjaZamowienia> tabelaZamowien;
    @FXML
    private TableColumn<PozycjaZamowienia, Boolean> kolumnaZaznaczZamowienie;
    @FXML
    private TableColumn<PozycjaZamowienia, Integer> kolumnaIdZamowienia;
    @FXML
    private TableColumn<PozycjaZamowienia, Integer> kolumnaNumerSali;
    @FXML
    private TableColumn<PozycjaZamowienia, Date> kolumnaDataZamowienia;
    @FXML
    private TableColumn<PozycjaZamowienia, Time> kolumnaGodzina;
    @FXML
    private TableColumn<PozycjaZamowienia, String> kolumnaZleceniodawca;
    @FXML
    private TableColumn<PozycjaZamowienia, String> kolumnaStatus;
    @FXML
    private JFXButton modyfikujZaznaczoneZamowienieButton;
    @FXML
    private JFXButton usunZaznaczoneZamowieniaButton;
    @FXML
    private JFXButton utworzNoweZamowienieButton;

    KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();

    ZamowienieImpl zamowienieImpl = new  ZamowienieImpl();
    final ObservableList<PozycjaZamowienia> listaZamowienDoWyswietlenia = FXCollections.observableArrayList();
    final ObservableList<PozycjaZamowienia> listaZamowienDoUsuniecia = FXCollections.observableArrayList();

    public static PozycjaZamowienia zamowieniAdministratora;

    List<PozycjaZamowienia> pozycjaListyZamowien;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (kontoRepositoryImpl.zwrocenieTypuKonta("1", "1") == 1) {
            tabelaZamowien.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            try {
                                zamowieniAdministratora = tabelaZamowien.getSelectionModel().getSelectedItem();
                                System.out.println(zamowieniAdministratora);
                                ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
                                kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/ZatwierdzenieZamowienia.fxml", "Tworzenie nowego zamówienia");
                            } catch (IOException ex) {
                                Logger.getLogger(ListaZamowienController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            });
        }
        
        
        List<Zamowienia> pobranaListaZamowienZbazyDanych=zamowienieImpl.pobierzZamowienia();

        for (Zamowienia zamowienie : pobranaListaZamowienZbazyDanych) {
            PozycjaZamowienia listaZamowien = new PozycjaZamowienia(zamowienie.getID(), zamowienie.getSalaNr_sali().getNumer_sali(), zamowienie.getData_zamowienia(), zamowienie.getGodzina(), "Pani Basia",
                    "Oczekuje na realizacje", new CheckBox());
            Object produkt = zamowienie.getProdukt_Zamowienia();
      

            listaZamowienDoWyswietlenia.add(listaZamowien);
        }

        kolumnaZaznaczZamowienie.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, Boolean>("zaznaczonyZamowienie"));
        kolumnaNumerSali.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, Integer>("nrSali"));

        kolumnaIdZamowienia.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, Integer>("idZamowienia"));
        kolumnaDataZamowienia.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, Date>("dataZamowienia"));
        kolumnaGodzina.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, Time>("godzinaZamowienia"));
        kolumnaZleceniodawca.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, String>("Zleceniodawca"));
        kolumnaStatus.setCellValueFactory(new PropertyValueFactory<PozycjaZamowienia, String>("Status"));
        tabelaZamowien.setItems(listaZamowienDoWyswietlenia);
      
    }

    @FXML
    private void modyfikujZaznaczoneZamowienie(ActionEvent event) {

    }

    @FXML
    private void usunZaznaczoneZamowienia(ActionEvent event) {
        for (PozycjaZamowienia pozycjaZamowienia : listaZamowienDoWyswietlenia) {

            if (pozycjaZamowienia.getZaznaczonyZamowienie().isSelected() == true) {
                listaZamowienDoUsuniecia.addAll(pozycjaZamowienia);

            }
        }
        for (PozycjaZamowienia zamowienieDoUsuniecia : listaZamowienDoUsuniecia) {
            
            List<Produkt_Zamowienia> listaProduktowZamowieniawedlugId;
            listaProduktowZamowieniawedlugId=zamowienieImpl.pobierzProduktZamowienia(zamowienieDoUsuniecia.getIdZamowienia());
            
            zamowienieImpl.usunZaznaczoneZamowienie(listaProduktowZamowieniawedlugId);
            listaZamowienDoWyswietlenia.remove(zamowienieDoUsuniecia);
        }
    
    }

    @FXML
    private void utworzNoweZamowienie(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/NoweZamowienie.fxml", "Tworzenie nowego zamówienia");
    }

    public class PozycjaZamowienia {

        private Integer idZamowienia;
        private Integer nrSali;
        private Date dataZamowienia;
        private Time godzinaZamowienia;
        private String Zleceniodawca;
        private String Status;
        private CheckBox zaznaczonyZamowienie;

        @Override
        public int hashCode() {
            int hash = 7;
            return hash;
        }

        @Override
        public String toString() {
            return "PozycjaZamowienia{" + "idZamowienia=" + idZamowienia + ", nrSali=" + nrSali + ", dataZamowienia=" + dataZamowienia + ", godzinaZamowienia=" + godzinaZamowienia + ", Zleceniodawca=" + Zleceniodawca + ", Status=" + Status + ", zaznaczonyZamowienie=" + zaznaczonyZamowienie + '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PozycjaZamowienia other = (PozycjaZamowienia) obj;
            if ((this.Zleceniodawca == null) ? (other.Zleceniodawca != null) : !this.Zleceniodawca.equals(other.Zleceniodawca)) {
                return false;
            }
            if ((this.Status == null) ? (other.Status != null) : !this.Status.equals(other.Status)) {
                return false;
            }
            if (this.idZamowienia != other.idZamowienia && (this.idZamowienia == null || !this.idZamowienia.equals(other.idZamowienia))) {
                return false;
            }
            if (this.nrSali != other.nrSali && (this.nrSali == null || !this.nrSali.equals(other.nrSali))) {
                return false;
            }
            if (this.dataZamowienia != other.dataZamowienia && (this.dataZamowienia == null || !this.dataZamowienia.equals(other.dataZamowienia))) {
                return false;
            }
            if (this.godzinaZamowienia != other.godzinaZamowienia && (this.godzinaZamowienia == null || !this.godzinaZamowienia.equals(other.godzinaZamowienia))) {
                return false;
            }
            if (this.zaznaczonyZamowienie != other.zaznaczonyZamowienie && (this.zaznaczonyZamowienie == null || !this.zaznaczonyZamowienie.equals(other.zaznaczonyZamowienie))) {
                return false;
            }
            return true;
        }

        public Integer getIdZamowienia() {
            return idZamowienia;
        }

        public void setIdZamowienia(Integer idZamowienia) {
            this.idZamowienia = idZamowienia;
        }

        public Integer getNrSali() {
            return nrSali;
        }

        public void setNrSali(Integer nrSali) {
            this.nrSali = nrSali;
        }

        public Date getDataZamowienia() {
            return dataZamowienia;
        }

        public void setDataZamowienia(Date dataZamowienia) {
            this.dataZamowienia = dataZamowienia;
        }

        public Time getGodzinaZamowienia() {
            return godzinaZamowienia;
        }

        public void setGodzinaZamowienia(Time godzinaZamowienia) {
            this.godzinaZamowienia = godzinaZamowienia;
        }

        public String getZleceniodawca() {
            return Zleceniodawca;
        }

        public void setZleceniodawca(String Zleceniodawca) {
            this.Zleceniodawca = Zleceniodawca;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public CheckBox getZaznaczonyZamowienie() {
            return zaznaczonyZamowienie;
        }

        public void setZaznaczonyZamowienie(CheckBox zaznaczonyZamowienie) {
            this.zaznaczonyZamowienie = zaznaczonyZamowienie;
        }

        public PozycjaZamowienia(Integer idZamowienia, Integer nrSali, Date dataZamowienia, Time godzinaZamowienia, String Zleceniodawca, String Status, CheckBox zaznaczonyZamowienie) {
            this.idZamowienia = idZamowienia;
            this.nrSali = nrSali;
            this.dataZamowienia = dataZamowienia;
            this.godzinaZamowienia = godzinaZamowienia;
            this.Zleceniodawca = Zleceniodawca;
            this.Status = Status;
            this.zaznaczonyZamowienie = zaznaczonyZamowienie;
        }
    }
}
