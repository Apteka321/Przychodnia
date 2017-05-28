/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static View.Komunikaty.wyswietlInformacje;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.repository.impl.RaportImpl;
import model.repository.impl.RaportImpl.MiesiecznyRaport;

/**
 *
 * @author Daniel
 */
public class RaportyController implements Initializable {

    @FXML
    private LineChart<Record, Number> wykresZyskowStrat;
    @FXML
    private TableColumn<Record, String> kolumnaMiesiac;
    @FXML
    private TableColumn<Record, Double> kolumnaPrzychod;
    @FXML
    private TableColumn<Record, Double> kolumnaWynagrodzenia;

    @FXML
    private TableView<Record> tabelaPrzychodu;

    private final RaportImpl metodyRaportow = new RaportImpl();
//    @FXML
//    private TableColumn<Record, ?> kolumnaKoszty;
    @FXML
    private TableColumn<Record, Double> kolumnaZamowienia;

    @FXML
    private TableColumn<Record, Double> kolumnaZW;
   
    @FXML
    private BarChart<String, Integer> wizytyBarChart;
        //Pola dla tabeli wizyt
    @FXML  
    private TableView<PozycjaTabeliWizyt> wizytyTabela;
    @FXML
    private TableColumn<PozycjaTabeliWizyt,String> wizytyTabelaMiesiacColun;
    @FXML
    private TableColumn<PozycjaTabeliWizyt, Integer> wizytyTabelaLiczbaColumn;
  
    @FXML
    private PieChart zamowieniaPieChart ;
    @FXML
    private TableView<PozycjaTabeliZamowien> zamowieniaTabela;
    @FXML
    private TableColumn<PozycjaTabeliZamowien, String> zamowieniaKolumnaMiesiac;
    @FXML
    private TableColumn<PozycjaTabeliZamowien, Integer> zamowieniaIloscZamowien;
    @FXML
    private TableColumn<?, ?> kolumnaKoszty;
    //Pola dla tabeli wizyt
    public void initialize(URL url, ResourceBundle rb) {

        List<MiesiecznyRaport> miesiecznyRaportZyskow = metodyRaportow.pobierzDaneDoRaportu("2017");
        double[] miesieczneWydatkiNaWynagrodzenia = metodyRaportow.pobierzMiesiaczneKoszty();
        double[] miesieczneWydatkiNaZamowienia = metodyRaportow.pobierzKosztyZamowien();
        int[] miesiecznaLiczbWizyt = metodyRaportow.listaWizytPacjentow();
        int [] miesiacznaLiczbaZamowien = metodyRaportow.listaZamowien();
        double[] zPlusW = new double[12];

        for (int i = 0; i < miesieczneWydatkiNaWynagrodzenia.length; i++) {
            zPlusW[i] = miesieczneWydatkiNaZamowienia[i] + miesieczneWydatkiNaWynagrodzenia[i];
        }
        //--- Prepare StackedBarChart
        List<String> dayLabels = Arrays.asList(
                "Styczeñ",
                "Luty",
                "Marzec",
                "Kwiecieñ",
                "Maj",
                "Czerwiec",
                "Lipiec",
                "Sierpieñ",
                "Wrzesieñ",
                "PaŸdziernik",
                "Listopad",
                "Grudzieñ");
        /**
         * Utworzenie obiektu Tabeli i dodanie za pomoca konstruktora wartosci
         */
        //prepare myList
        
          final ObservableList<PozycjaTabeliWizyt> listaPozycjiTabeliWizyt = FXCollections.observableArrayList();
          final ObservableList<PozycjaTabeliZamowien> listaPozycjiTabeliZamowien = FXCollections.observableArrayList();

    //Wykres slupkowy odwiedzin 
        XYChart.Series series1 = new XYChart.Series();             
        series1.getData().add(new XYChart.Data("Styczeñ",miesiecznaLiczbWizyt[0]));
        series1.getData().add(new XYChart.Data("Luty",miesiecznaLiczbWizyt[1]));
        series1.getData().add(new XYChart.Data("Marzec",miesiecznaLiczbWizyt[2]));
        series1.getData().add(new XYChart.Data("Kwiecieñ",miesiecznaLiczbWizyt[3]));
        series1.getData().add(new XYChart.Data("Maj",miesiecznaLiczbWizyt[4]));
        series1.getData().add(new XYChart.Data("Czerwiec",miesiecznaLiczbWizyt[5]));
        series1.getData().add(new XYChart.Data("Lipiec",miesiecznaLiczbWizyt[6]));
        series1.getData().add(new XYChart.Data("Sierpieñ",miesiecznaLiczbWizyt[7]));
        series1.getData().add(new XYChart.Data("Wrzesieñ",miesiecznaLiczbWizyt[8]));
        series1.getData().add(new XYChart.Data("PaŸdziernik",miesiecznaLiczbWizyt[9]));
        series1.getData().add(new XYChart.Data("Listopad",miesiecznaLiczbWizyt[10]));
        series1.getData().add(new XYChart.Data("Grudzieñ",miesiecznaLiczbWizyt[11]));
        wizytyBarChart.getData().addAll(series1);
        
        //wykres liczby zamowien
        ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList();
        if(miesiacznaLiczbaZamowien[0]!=0){
        pieChartData1.add(new PieChart.Data("Stryczeñ",miesiacznaLiczbaZamowien[0]));
        }
        if(miesiacznaLiczbaZamowien[1]!=0){
        pieChartData1.add(new PieChart.Data("Luty",miesiacznaLiczbaZamowien[1]));
        }
        if(miesiacznaLiczbaZamowien[2]!=0){
        pieChartData1.add(new PieChart.Data("Marzec",miesiacznaLiczbaZamowien[2]));
        }
        if(miesiacznaLiczbaZamowien[3]!=0){
        pieChartData1.add(new PieChart.Data("Kwiecieñ",miesiacznaLiczbaZamowien[3]));
        }
        if(miesiacznaLiczbaZamowien[4]!=0){
        pieChartData1.add(new PieChart.Data("Maj",miesiacznaLiczbaZamowien[4]));
        }
        if(miesiacznaLiczbaZamowien[5]!=0){
        pieChartData1.add(new PieChart.Data("Czerwiec",miesiacznaLiczbaZamowien[5]));
        }
        if(miesiacznaLiczbaZamowien[6]!=0){
        pieChartData1.add(new PieChart.Data("Lipiec",miesiacznaLiczbaZamowien[6]));
        }
        if(miesiacznaLiczbaZamowien[7]!=0){
        pieChartData1.add(new PieChart.Data("Sierpieñ",miesiacznaLiczbaZamowien[7]));
        }
        if(miesiacznaLiczbaZamowien[8]!=0){
        pieChartData1.add(new PieChart.Data("Wrzesieñ",miesiacznaLiczbaZamowien[8]));
        }
        if(miesiacznaLiczbaZamowien[9]!=0){
        pieChartData1.add(new PieChart.Data("PaŸdziernik",miesiacznaLiczbaZamowien[9]));
        }
        if(miesiacznaLiczbaZamowien[10]!=0){
        pieChartData1.add(new PieChart.Data("Listopad",miesiacznaLiczbaZamowien[10]));
        }
        if(miesiacznaLiczbaZamowien[11]!=0){
        pieChartData1.add(new PieChart.Data("Grudzieñ",miesiacznaLiczbaZamowien[11]));
        }


        zamowieniaPieChart.setData(pieChartData1);
        zamowieniaPieChart.setPrefWidth(400);
        zamowieniaPieChart.setTitle("Wykres Liczby zamówieñ");
        
        
        
        wizytyBarChart.setTitle("Wykres wizyt");
        series1.setName("Liczba wizyt");
        wizytyTabelaMiesiacColun.setCellValueFactory(new PropertyValueFactory<PozycjaTabeliWizyt, String>("miesiac"));
        wizytyTabelaLiczbaColumn.setCellValueFactory(new PropertyValueFactory<PozycjaTabeliWizyt, Integer>("liczbaWizyt"));
        wizytyTabela.setItems(listaPozycjiTabeliWizyt);
      
      //tabela odwiedzin 
      
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Styczeñ",miesiecznaLiczbWizyt[0]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Luty",miesiecznaLiczbWizyt[1]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Marzec",miesiecznaLiczbWizyt[2]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Kwiecieñ",miesiecznaLiczbWizyt[3]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Maj",miesiecznaLiczbWizyt[4]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Czerwiec",miesiecznaLiczbWizyt[5]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Lipiec",miesiecznaLiczbWizyt[6]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Sierpieñ",miesiecznaLiczbWizyt[7]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Wrzesieñ",miesiecznaLiczbWizyt[8]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("PaŸdziernik",miesiecznaLiczbWizyt[9]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Listopad",miesiecznaLiczbWizyt[10]));
        listaPozycjiTabeliWizyt.add(new PozycjaTabeliWizyt("Grudzieñ",miesiecznaLiczbWizyt[11]));
        
        
        //tabela zamowien
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Styczeñ",miesiacznaLiczbaZamowien[0]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Luty",miesiacznaLiczbaZamowien[1]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Marzec",miesiacznaLiczbaZamowien[2]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Kwiecieñ",miesiacznaLiczbaZamowien[3]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Maj",miesiacznaLiczbaZamowien[4]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Czerwiec",miesiacznaLiczbaZamowien[5]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Lipiec",miesiacznaLiczbaZamowien[6]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Sierpieñ",miesiacznaLiczbaZamowien[7]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Wrzesieñ",miesiacznaLiczbaZamowien[8]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("PaŸdziernik",miesiacznaLiczbaZamowien[9]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Listopad",miesiacznaLiczbaZamowien[10]));
        listaPozycjiTabeliZamowien.add(new PozycjaTabeliZamowien("Grudzieñ",miesiacznaLiczbaZamowien[11]));
        zamowieniaIloscZamowien.setCellValueFactory(new PropertyValueFactory<PozycjaTabeliZamowien, Integer>("ilosc"));
        zamowieniaKolumnaMiesiac.setCellValueFactory(new PropertyValueFactory<PozycjaTabeliZamowien, String>("miesiac"));
        zamowieniaTabela.setItems(listaPozycjiTabeliZamowien);

                                                    
       for (MiesiecznyRaport object : miesiecznyRaportZyskow) {
            myList = new MyList();
            myList.add(new Record("Styczeñ", miesiecznyRaportZyskow.get(0).getCena(), miesieczneWydatkiNaWynagrodzenia[0], miesieczneWydatkiNaZamowienia[0], zPlusW[0]));
            myList.add(new Record("Luty", miesiecznyRaportZyskow.get(1).getCena(), miesieczneWydatkiNaWynagrodzenia[1], miesieczneWydatkiNaZamowienia[1], zPlusW[1]));
            myList.add(new Record("Marzec", miesiecznyRaportZyskow.get(2).getCena(), miesieczneWydatkiNaWynagrodzenia[2], miesieczneWydatkiNaZamowienia[2], zPlusW[2]));
            myList.add(new Record("Kwiecieñ", miesiecznyRaportZyskow.get(3).getCena(), miesieczneWydatkiNaWynagrodzenia[3], miesieczneWydatkiNaZamowienia[3], zPlusW[3]));
            myList.add(new Record("Maj", miesiecznyRaportZyskow.get(4).getCena(), miesieczneWydatkiNaWynagrodzenia[4], miesieczneWydatkiNaZamowienia[4], zPlusW[4]));
            myList.add(new Record("Czerwiec", miesiecznyRaportZyskow.get(5).getCena(), miesieczneWydatkiNaWynagrodzenia[5], miesieczneWydatkiNaZamowienia[5], zPlusW[5]));
            myList.add(new Record("Lipiec", miesiecznyRaportZyskow.get(6).getCena(), miesieczneWydatkiNaWynagrodzenia[6], miesieczneWydatkiNaZamowienia[6], zPlusW[6]));
            myList.add(new Record("Sierpieñ", miesiecznyRaportZyskow.get(7).getCena(), miesieczneWydatkiNaWynagrodzenia[7], miesieczneWydatkiNaZamowienia[7], zPlusW[7]));
            myList.add(new Record("Wrzesieñ", miesiecznyRaportZyskow.get(8).getCena(), miesieczneWydatkiNaWynagrodzenia[8], miesieczneWydatkiNaZamowienia[8], zPlusW[8]));
            myList.add(new Record("PaŸdziernik", miesiecznyRaportZyskow.get(9).getCena(), miesieczneWydatkiNaWynagrodzenia[9], miesieczneWydatkiNaZamowienia[9], zPlusW[9]));
            myList.add(new Record("Listopad", miesiecznyRaportZyskow.get(10).getCena(), miesieczneWydatkiNaWynagrodzenia[10], miesieczneWydatkiNaZamowienia[10], zPlusW[10]));
            myList.add(new Record("Grudzieñ", miesiecznyRaportZyskow.get(11).getCena(), miesieczneWydatkiNaWynagrodzenia[11], miesieczneWydatkiNaZamowienia[11], zPlusW[11]));
        
            myList2 = new MyList();
            myList2.add(new Record("Styczeñ", zPlusW[0], miesieczneWydatkiNaWynagrodzenia[0], miesieczneWydatkiNaZamowienia[0], zPlusW[0]));
            myList2.add(new Record("Luty", zPlusW[1], miesieczneWydatkiNaWynagrodzenia[1], miesieczneWydatkiNaZamowienia[1], zPlusW[1]));
            myList2.add(new Record("Marzec",zPlusW[2], miesieczneWydatkiNaWynagrodzenia[2], miesieczneWydatkiNaZamowienia[2], zPlusW[2]));
            myList2.add(new Record("Kwiecieñ", zPlusW[3], miesieczneWydatkiNaWynagrodzenia[3], miesieczneWydatkiNaZamowienia[3], zPlusW[3]));
            myList2.add(new Record("Maj", zPlusW[4], miesieczneWydatkiNaWynagrodzenia[4], miesieczneWydatkiNaZamowienia[4], zPlusW[4]));
            myList2.add(new Record("Czerwiec", zPlusW[5], miesieczneWydatkiNaWynagrodzenia[5], miesieczneWydatkiNaZamowienia[5], zPlusW[5]));
            myList2.add(new Record("Lipiec", zPlusW[6], miesieczneWydatkiNaWynagrodzenia[6], miesieczneWydatkiNaZamowienia[6], zPlusW[6]));
            myList2.add(new Record("Sierpieñ", zPlusW[7], miesieczneWydatkiNaWynagrodzenia[7], miesieczneWydatkiNaZamowienia[7], zPlusW[7]));
            myList2.add(new Record("Wrzesieñ", zPlusW[8], miesieczneWydatkiNaWynagrodzenia[8], miesieczneWydatkiNaZamowienia[8], zPlusW[8]));
            myList2.add(new Record("PaŸdziernik", zPlusW[9], miesieczneWydatkiNaWynagrodzenia[9], miesieczneWydatkiNaZamowienia[9], zPlusW[9]));
            myList2.add(new Record("Listopad", zPlusW[10], miesieczneWydatkiNaWynagrodzenia[10], miesieczneWydatkiNaZamowienia[10], zPlusW[10]));
            myList2.add(new Record("Grudzieñ", zPlusW[11], miesieczneWydatkiNaWynagrodzenia[11], miesieczneWydatkiNaZamowienia[11], zPlusW[11]));
                 
         }        

        /**
         * Utworzenie obiektu Tabeli i dodanie za pomoca konstruktora wartosci
         */
        Group root = new Group();
        /**
         * Utworzenie edytowanie tabeli
         */
        tabelaPrzychodu.setEditable(true);  
     
        kolumnaMiesiac.setCellValueFactory(new PropertyValueFactory<Record, String>("kolumnaMiesiac"));
        kolumnaMiesiac.setMinWidth(60);
        kolumnaPrzychod.setCellValueFactory(
                new PropertyValueFactory<Record, Double>("kolumnaPrzychod"));
        kolumnaPrzychod.setMinWidth(60);
        kolumnaZamowienia.setCellValueFactory(new PropertyValueFactory<Record, Double>("kolumnaZamowienia"));

        kolumnaWynagrodzenia.setCellValueFactory(
                new PropertyValueFactory<Record, Double>("kolumnaWynagrodzenia"));
        kolumnaZW.setCellValueFactory(new PropertyValueFactory<Record, Double>("kolumnaZW"));
        try {
        tabelaPrzychodu.setItems(myList.dataList);
        tabelaPrzychodu.getColumns().addAll(kolumnaMiesiac, kolumnaPrzychod, kolumnaZamowienia, kolumnaWynagrodzenia, kolumnaZW);
        } catch (Exception e) {
            wyswietlInformacje("Komunikat", "Zbyt ma³o danych aby wyœwietliæ raport");
        }
   

        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Miesi¹c");
        xAxis2.setCategories(FXCollections.<String>observableArrayList(dayLabels));
        yAxis2.setLabel("Przychód");
        XYChart.Series XYSeries2 = new XYChart.Series(myList.xyList2);
        XYSeries2.setName("Zyski");

        XYChart.Series XYSeries3 = new XYChart.Series(myList2.xyList2);
        XYSeries3.setName("Straty");
        wykresZyskowStrat.setTitle("Wykres zyskow i strat");
        wykresZyskowStrat.setPrefWidth(680);
        wykresZyskowStrat.getData().addAll(XYSeries2,XYSeries3);
    }

    class MyList {

        ObservableList<Record> dataList;
        ObservableList<XYChart.Data> xyList2;

        MyList() {
            dataList = FXCollections.observableArrayList();
            xyList2 = FXCollections.observableArrayList();
        }

        public void add(Record r) {
            dataList.add(r);
//          pieChartData1.add(new PieChart.Data(r.getKolumnaMiesiac(), r.getKolumnaIlosc()));
            xyList2.add(new XYChart.Data(r.getKolumnaMiesiac(), r.getKolumnaPrzychod()));
        }

        public void update1(int pos, Double val) {
//            pieChartData1.set(pos, new PieChart.Data(pieChartData1.get(pos).getName(), val));
        }

        public void update2(int pos, Double val) {
            xyList2.set(pos, new XYChart.Data(xyList2.get(pos).getXValue(), val));
        }
                    
       
    }

    MyList myList;
    MyList myList2;

    /**
     * Rysowanie Tabeli z danymi
     */
    
    
    public class PozycjaTabeliZamowien {
     private String miesiac;
     private int ilosc;

        public String getMiesiac() {
            return miesiac;
        }

        public void setMiesiac(String miesiac) {
            this.miesiac = miesiac;
        }

        public int getIlosc() {
            return ilosc;
        }

        public void setIlosc(int ilosc) {
            this.ilosc = ilosc;
        }

        public PozycjaTabeliZamowien(String miesiac, int ilosc) {
            this.miesiac = miesiac;
            this.ilosc = ilosc;
        }
     
     
     
     
    }
    
    public class PozycjaTabeliWizyt {
        private String miesiac;

        public PozycjaTabeliWizyt(String miesiac, Integer liczbaWizyt) {
            this.miesiac = miesiac;
            this.liczbaWizyt = liczbaWizyt;
        }

        public String getMiesiac() {
            return miesiac;
        }

        public void setMiesiac(String miesiac) {
            this.miesiac = miesiac;
        }

        public int getLiczbaWizyt() {
            return liczbaWizyt;
        }

        public void setLiczbaWizyt(Integer liczbaWizyt) {
            this.liczbaWizyt = liczbaWizyt;
        }
        private Integer liczbaWizyt;
        
        
    }
    public class Record {

        private String kolumnaMiesiac;
        private Double kolumnaPrzychod;
        private Double kolumnaWynagrodzenia;
        private Double kolumnaZW;

        public Double getKolumnaZW() {
            return kolumnaZW;
        }

        public void setKolumnaZW(Double kolumnaZW) {
            this.kolumnaZW = kolumnaZW;
        }

        public Record(String kolumnaMiesiac, Double kolumnaPrzychod, Double kolumnaWynagrodzenia, Double kolumnaZamowienia, Double kolumnaZW) {
            this.kolumnaMiesiac = kolumnaMiesiac;
            this.kolumnaPrzychod = kolumnaPrzychod;
            this.kolumnaWynagrodzenia = kolumnaWynagrodzenia;
            this.kolumnaZW = kolumnaZW;
            this.kolumnaZamowienia = kolumnaZamowienia;
        }

        private Double kolumnaZamowienia;

        public Double getKolumnaZamowienia() {
            return kolumnaZamowienia;
        }

        public void setKolumnaZamowienia(Double kolumnaZamowienia) {
            this.kolumnaZamowienia = kolumnaZamowienia;
        }

        public double getKolumnaWynagrodzenia() {
            return kolumnaWynagrodzenia;
        }

        public void setKolumnaWynagrodzenia(Double kolumnaWynagrodzenia) {
            this.kolumnaWynagrodzenia = kolumnaWynagrodzenia;
        }

        public String getKolumnaMiesiac() {
            return kolumnaMiesiac;
        }

        public void setKolumnaMiesiac(String kolumnaMiesiac) {
            this.kolumnaMiesiac = kolumnaMiesiac;
        }

        public Double getKolumnaPrzychod() {
            return kolumnaPrzychod;
        }

        public void setKolumnaPrzychod(Double kolumnaPrzychod) {
            this.kolumnaPrzychod = kolumnaPrzychod;
        }

    }

}
