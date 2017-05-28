/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.ListaZamowienController.zamowieniAdministratora;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.HibernateUtil;
import model.Produkt;
import model.Produkt_Zamowienia;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.ZamowienieImpl;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class ZatwierdzenieZamowieniaController implements Initializable {

    @FXML
    private TableView<ZatwierdzenieZamowienia> listaProduktow;
    @FXML
    private TableColumn<ZatwierdzenieZamowienia, Boolean> kolumnaZaznaczProdukt;
    @FXML
    private TableColumn<ZatwierdzenieZamowienia, String> kolumnaNazwaProduktu;
    @FXML
    private TableColumn<ZatwierdzenieZamowienia, Integer> kolumnaIloscProduktu;
    @FXML
    private TableColumn<ZatwierdzenieZamowienia, BigDecimal> kolumnaCenaProduktu;
    @FXML
    private TextField inputNazwaProduktu;
    @FXML
    private TextField inputIloscProduktu;
    @FXML
    private JFXButton usunZListyZamowienButton;
    @FXML
    private JFXButton dodajDoListyZamowienButton;
    @FXML
    private Label nrSaliLabel;
    @FXML
    private Label nazwaProduktuLabel;
    @FXML
    private Label iloscLabel;
    @FXML
    private Label labelNumerZamowienia;
    @FXML
    private Label labelNumerSali;
    @FXML
    private JFXButton zapiszZamowienieButton;

    /**
     * Initializes the controller class.
     */
    final ObservableList<ZatwierdzenieZamowienia> listaProduktowDoWyswietlenia = FXCollections.observableArrayList();

    List<Produkt_Zamowienia> listaProduktowZamowieniawedlugId = null;
    KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();
    ZamowienieImpl zamowienieImpl = new ZamowienieImpl();
    

    public void initialize(URL url, ResourceBundle rb) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Object produktDoUsuniecia = null;
        Query listaProduktowZamowienia = session.createQuery("from Produkt_Zamowienia where ZamowieniaID=" + zamowieniAdministratora.getIdZamowienia() + "order by ProduktID");

        listaProduktowZamowieniawedlugId = listaProduktowZamowienia.list();
        Produkt produkt = null;
        for (Produkt_Zamowienia produktDodawanyDoTabeli : listaProduktowZamowieniawedlugId) {
            produkt = session.load(Produkt.class, produktDodawanyDoTabeli.getProdukt().getID());
            ZatwierdzenieZamowienia listaProduktow = new ZatwierdzenieZamowienia(new CheckBox(), produkt.getNazwa(), produkt.getCena(), produktDodawanyDoTabeli.getIlosc(), produktDodawanyDoTabeli.getProdukt().getID(), produktDodawanyDoTabeli.getZamowienia().getID());
            listaProduktowDoWyswietlenia.addAll(listaProduktow);
        }
        kolumnaNazwaProduktu.setCellFactory(TextFieldTableCell.<ZatwierdzenieZamowienia>forTableColumn());
        kolumnaNazwaProduktu.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ZatwierdzenieZamowienia, String>>() {
       
            public void handle(TableColumn.CellEditEvent<ZatwierdzenieZamowienia, String> t) {
                t.getRowValue().setNazwaProduktu(t.getNewValue());
            }
        });
        kolumnaCenaProduktu.setCellFactory(TextFieldTableCell.<ZatwierdzenieZamowienia, BigDecimal>forTableColumn(new BigDecimalStringConverter()));

        kolumnaCenaProduktu.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ZatwierdzenieZamowienia, BigDecimal>>() {
       
            public void handle(TableColumn.CellEditEvent<ZatwierdzenieZamowienia, BigDecimal> t) {
                t.getRowValue().setCenaProduktu(t.getNewValue());
            }
        });

        kolumnaIloscProduktu.setCellFactory(TextFieldTableCell.<ZatwierdzenieZamowienia, Integer>forTableColumn(new IntegerStringConverter()));
        kolumnaIloscProduktu.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ZatwierdzenieZamowienia, Integer>>() {

            public void handle(TableColumn.CellEditEvent<ZatwierdzenieZamowienia, Integer> t) {
                t.getRowValue().setIloscProduktu(t.getNewValue());
            }
        });
        listaProduktow.setEditable(true);
        kolumnaZaznaczProdukt.setCellValueFactory(new PropertyValueFactory<ZatwierdzenieZamowienia, Boolean>("zaznaczonyProdukt"));
        kolumnaNazwaProduktu.setCellValueFactory(new PropertyValueFactory<ZatwierdzenieZamowienia, String>("nazwaProduktu"));
        kolumnaCenaProduktu.setCellValueFactory(new PropertyValueFactory<ZatwierdzenieZamowienia, BigDecimal>("cenaProduktu"));
        kolumnaIloscProduktu.setCellValueFactory(new PropertyValueFactory<ZatwierdzenieZamowienia, Integer>("iloscProduktu"));

        labelNumerZamowienia.setText(Integer.toString(zamowieniAdministratora.getIdZamowienia()));
        labelNumerSali.setText(Integer.toString(zamowieniAdministratora.getNrSali()));
        listaProduktow.setItems(listaProduktowDoWyswietlenia);
    }

    @FXML
    private void usunZListyZamowien(ActionEvent event) {
        System.err.println(zamowieniAdministratora);
    }

    @FXML
    private void dodajDoListyZamowien(ActionEvent event) {
    }

    @FXML
    private void zapiszZamowienie(ActionEvent event) throws IOException {
        zamowienieImpl.zapiszEdytowaneZamowienie(listaProduktowDoWyswietlenia);
        ((Node) (event.getSource())).getScene().getWindow().hide();
        kontoRepositoryImpl.otwarcieNowejScenyZAdresu("/fxml/ListaZamowien.fxml", "Lista zamówien do sal");

    }

    public class ZatwierdzenieZamowienia {

        private CheckBox zaznaczonyProdukt;
        private String nazwaProduktu;
        private BigDecimal cenaProduktu;
        private Integer iloscProduktu;
        private Integer idProduktu;
        private Integer idZamowienia;

        public ZatwierdzenieZamowienia(CheckBox zaznaczonyProdukt, String nazwaProduktu, BigDecimal cenaProduktu, Integer iloscProduktu, Integer idProduktu, Integer idZamowienia) {
            this.zaznaczonyProdukt = zaznaczonyProdukt;
            this.nazwaProduktu = nazwaProduktu;
            this.cenaProduktu = cenaProduktu;
            this.iloscProduktu = iloscProduktu;
            this.idProduktu = idProduktu;
            this.idZamowienia = idZamowienia;
        }

        public CheckBox getZaznaczonyProdukt() {
            return zaznaczonyProdukt;
        }

        public void setZaznaczonyProdukt(CheckBox zaznaczonyProdukt) {
            this.zaznaczonyProdukt = zaznaczonyProdukt;
        }

        public String getNazwaProduktu() {
            return nazwaProduktu;
        }

        public void setNazwaProduktu(String nazwaProduktu) {
            this.nazwaProduktu = nazwaProduktu;
        }

        public BigDecimal getCenaProduktu() {
            return cenaProduktu;
        }

        public void setCenaProduktu(BigDecimal cenaProduktu) {
            this.cenaProduktu = cenaProduktu;
        }

        public Integer getIloscProduktu() {
            return iloscProduktu;
        }

        public void setIloscProduktu(Integer iloscProduktu) {
            this.iloscProduktu = iloscProduktu;
        }

        public Integer getIdProduktu() {
            return idProduktu;
        }

        public void setIdProduktu(Integer idProduktu) {
            this.idProduktu = idProduktu;
        }

        public Integer getIdZamowienia() {
            return idZamowienia;
        }

        public void setIdZamowienia(Integer idZamowienia) {
            this.idZamowienia = idZamowienia;
        }

    }

}
