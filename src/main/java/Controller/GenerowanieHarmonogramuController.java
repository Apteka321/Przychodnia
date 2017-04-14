package Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.Session;

import Controller.RejestracjaPacjentaNaWizyteController.pacjentOsoba;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HibernateUtil;
import model.Pacjent;
import model.Zabieg;

public class GenerowanieHarmonogramuController implements Initializable {

	@FXML
	private TableView<DaneZabiegu> tabelaZabiegow;

	@FXML
	private TableColumn<DaneZabiegu, String> kolumnaNazwaZabiegu;

	@FXML
	private TableColumn<DaneZabiegu, DecimalFormat> kolumnaCenaZabiegu;

	@FXML
	private TableColumn<DaneZabiegu, Date> kolumnaDataWykonaniaZabiegu;

	@FXML
	private ChoiceBox<String> wykonanieZabiegu;

	public ObservableList<DaneZabiegu> listaZabiegow;

	public void initialize(URL arg0, ResourceBundle arg1) {
		kolumnaNazwaZabiegu.setCellValueFactory(new PropertyValueFactory<DaneZabiegu, String>("nazwa"));
		kolumnaCenaZabiegu.setCellValueFactory(new PropertyValueFactory<DaneZabiegu, DecimalFormat>("cena"));
		kolumnaDataWykonaniaZabiegu.setCellValueFactory(new PropertyValueFactory<DaneZabiegu, Date>("dataWykonania"));
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		listaZabiegow = FXCollections.observableArrayList();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From Zabieg where Data_wykonania = :datawykonania");
		query.setParameter("datawykonania", date);
		List<Zabieg> lista = query.list();
		for (Zabieg zabieg : lista) {
			DaneZabiegu daneZabiegu = new DaneZabiegu(zabieg.getLista_zabiegow().getNazwa(),
					zabieg.getLista_zabiegow().getCena(), zabieg.getData_wykonania());
			listaZabiegow.add(daneZabiegu);
		}
		tabelaZabiegow.setItems(listaZabiegow);
		final List<String> dzien = new ArrayList<String>();
		dzien.add("biez¹cy dzieñ");
		dzien.add("aktualny tydzieñ");
		wykonanieZabiegu.getItems().addAll(dzien);
		wykonanieZabiegu.getSelectionModel().select(0);
	
		wykonanieZabiegu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				listaZabiegow = FXCollections.observableArrayList();
				Session session = HibernateUtil.getSessionFactory().openSession();
				if (wykonanieZabiegu.getValue().equals("Tygodnia")) {

					Query query = session.createQuery("From Zabieg where Data_wykonania = :datawykonania");
					query.setParameter("datawykonania", date);
					List<Zabieg> lista = query.list();
					for (Zabieg zabieg : lista) {
						DaneZabiegu daneZabiegu = new DaneZabiegu(zabieg.getLista_zabiegow().getNazwa(),
								zabieg.getLista_zabiegow().getCena(), zabieg.getData_wykonania());
						listaZabiegow.add(daneZabiegu);
					}
					tabelaZabiegow.setItems(listaZabiegow);
					session.close();
				}
				else{
				    java.sql.Date jadwalPengobatan = new java.sql.Date(date.getTime() + 7l*24l*60l*60l*1000l);
				    
				    Query query = session.createQuery("From Zabieg where Data_wykonania BETWEEN :data AND :datanastepna");
				    query.setParameter("data", date);
					query.setParameter("datanastepna", jadwalPengobatan);
					List<Zabieg> lista = query.list();
					for (Zabieg zabieg : lista) {
						DaneZabiegu daneZabiegu = new DaneZabiegu(zabieg.getLista_zabiegow().getNazwa(),
								zabieg.getLista_zabiegow().getCena(), zabieg.getData_wykonania());
						listaZabiegow.add(daneZabiegu);
						
					}
					tabelaZabiegow.setItems(listaZabiegow);
					session.close();
				}
				
				
				
			}
		
		});
		
		session.close();
		

	}
	public static Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	  }
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		
		return cal.getTime();
	}

	public class DaneZabiegu {
		String nazwa;
		BigDecimal cena;
		Date dataWykonania;

		public String getNazwa() {
			return nazwa;
		}

		public void setNazwa(String nazwa) {
			this.nazwa = nazwa;
		}

		public BigDecimal getCena() {
			return cena;
		}

		public void setCena(BigDecimal cena) {
			this.cena = cena;
		}

		public Date getDataWykonania() {
			return dataWykonania;
		}

		public void setDataWykonania(Date dataWykonania) {
			this.dataWykonania = dataWykonania;
		}

		public DaneZabiegu(String nazwa, BigDecimal cena, Date dataWykonania) {
			super();
			this.nazwa = nazwa;
			this.cena = cena;
			this.dataWykonania = dataWykonania;
		}

	}

}
