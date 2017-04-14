package Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Query;
import org.hibernate.Session;

import View.Komunikaty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.HibernateUtil;
import model.Recepcjonistka;
import model.Wizyta;
import model.Zabieg;
import model.repository.impl.KontoRepositoryImpl;
import model.repository.impl.MetodyPielegniarkiImpl;
import model.repository.impl.MetodyRecepcjonistkaImpl;

public class ObslugaPlatnosciController implements Initializable {
	@FXML
	private TableView<WizytaPlatnosc> tabelaDoZaplaty;

	@FXML
	private TableColumn<WizytaPlatnosc, Integer> kolumnaIdWizyty;

	@FXML
	private TableColumn<WizytaPlatnosc, String> kolumnaImie;

	@FXML
	private TableColumn<WizytaPlatnosc, String> kolumnaNazwisko;

	@FXML
	private TableColumn<WizytaPlatnosc, BigDecimal> kolumnaZaplata;
	@FXML
	private TableColumn<WizytaPlatnosc, String> kolumnaRodzaj;

	@FXML
	private Label sumaZaplata;

	public ObservableList<WizytaPlatnosc> listaDluznikow;

	MetodyRecepcjonistkaImpl metodyRecepcjonistkaImpl = new MetodyRecepcjonistkaImpl();

	public Recepcjonistka recepcjonistka = new KontoRepositoryImpl().getListaRecepcjonistek().get(0);

	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			kolumnaIdWizyty.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, Integer>("idWizyty"));
			kolumnaImie.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, String>("imie"));
			kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, String>("nazwisko"));
			kolumnaZaplata.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, BigDecimal>("suma"));
			kolumnaRodzaj.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, String>("tytul"));
			listaDluznikow = FXCollections.observableArrayList();

			Session session = HibernateUtil.getSessionFactory().openSession();

			Query query = session.createQuery("From Wizyta where Czy_Ubezpieczony =" + 0);

			List<Wizyta> lista = query.list();
			for (Wizyta wizyta : lista) {
				if (!(wizyta.getStatus_wizyty().equals("Zaplacono"))) {
					WizytaPlatnosc wPlatnosc = new WizytaPlatnosc(wizyta.getID(),
							wizyta.getPESEL_pacjenta().getOsoba().getImie(),
							wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(),
							wizyta.getID_Specjalizacji().getKoszt_wizyty(), "Wizyta");
					listaDluznikow.add(wPlatnosc);
				}

			}
			Query query2 = session.createQuery("From Zabieg");

			List<Zabieg> listaZ = query2.list();
			for (Zabieg zabieg : listaZ) {
				if (zabieg.getSkierowanie().getID_wizyty().getCzy_ubezpieczony() == 0) {
					if (!(zabieg.getSkierowanie().getID_wizyty().getStatus_wizyty().equals("Zaplacono"))) {
						WizytaPlatnosc wPlatnosc = new WizytaPlatnosc(zabieg.getSkierowanie().getID_wizyty().getID(),
								zabieg.getSkierowanie().getID_wizyty().getPESEL_pacjenta().getOsoba().getImie(),
								zabieg.getSkierowanie().getID_wizyty().getPESEL_pacjenta().getOsoba().getNazwisko(),
								zabieg.getLista_zabiegow().getCena(), "Zabieg");

						listaDluznikow.add(wPlatnosc);
					}
				}

			}

			tabelaDoZaplaty.setItems(listaDluznikow);
			session.close();
		} catch (Exception e) {
			Komunikaty.wyswietlInformacje("Informacja", "Brak d³u¿ników");
		}
	}

	BigDecimal cena;
	Integer idWiztyt;

	@FXML
	void poKliknieciu(MouseEvent event) {
		sumaZaplata.setText(tabelaDoZaplaty.getSelectionModel().getSelectedItem().getSuma().toString());
		cena = tabelaDoZaplaty.getSelectionModel().getSelectedItem().getSuma();
		idWiztyt = tabelaDoZaplaty.getSelectionModel().getSelectedItem().getIdWizyty();
	}

	@FXML
	void zaplac(ActionEvent event) {
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		metodyRecepcjonistkaImpl.dokonajPlatnosci(idWiztyt, recepcjonistka, cena, date);

		for (int i = 0; i < tabelaDoZaplaty.getItems().size(); i++) {
			tabelaDoZaplaty.getItems().clear();
		}
		try {

			kolumnaIdWizyty.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, Integer>("idWizyty"));
			kolumnaImie.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, String>("imie"));
			kolumnaNazwisko.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, String>("nazwisko"));
			kolumnaZaplata.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, BigDecimal>("suma"));
			kolumnaRodzaj.setCellValueFactory(new PropertyValueFactory<WizytaPlatnosc, String>("tytul"));
			listaDluznikow = FXCollections.observableArrayList();

			Session session = HibernateUtil.getSessionFactory().openSession();

			Query query = session.createQuery("From Wizyta where Czy_Ubezpieczony =" + 0);

			List<Wizyta> lista = query.list();
			for (Wizyta wizyta : lista) {
				if (!(wizyta.getStatus_wizyty().equals("Zaplacono"))) {
					WizytaPlatnosc wPlatnosc = new WizytaPlatnosc(wizyta.getID(),
							wizyta.getPESEL_pacjenta().getOsoba().getImie(),
							wizyta.getPESEL_pacjenta().getOsoba().getNazwisko(),
							wizyta.getID_Specjalizacji().getKoszt_wizyty(), "Wizyta");
					listaDluznikow.add(wPlatnosc);
				}

			}
			Query query2 = session.createQuery("From Zabieg");

			List<Zabieg> listaZ = query2.list();
			for (Zabieg zabieg : listaZ) {
				if (zabieg.getSkierowanie().getID_wizyty().getCzy_ubezpieczony() == 0) {
					if (!(zabieg.getSkierowanie().getID_wizyty().getStatus_wizyty().equals("Zaplacono"))) {
						WizytaPlatnosc wPlatnosc = new WizytaPlatnosc(zabieg.getSkierowanie().getID_wizyty().getID(),
								zabieg.getSkierowanie().getID_wizyty().getPESEL_pacjenta().getOsoba().getImie(),
								zabieg.getSkierowanie().getID_wizyty().getPESEL_pacjenta().getOsoba().getNazwisko(),
								zabieg.getLista_zabiegow().getCena(), "Zabieg");

						listaDluznikow.add(wPlatnosc);
					}
				}

			}

			tabelaDoZaplaty.setItems(listaDluznikow);
			session.close();
		} catch (Exception e) {
			Komunikaty.wyswietlInformacje("Informacja", "Brak d³u¿ników");
		}

	}

	public class WizytaPlatnosc {
		Integer idWizyty;
		String imie;
		String nazwisko;
		BigDecimal suma;
		String tytul;

		public Integer getIdWizyty() {
			return idWizyty;
		}

		public void setIdWizyty(Integer idWizyty) {
			this.idWizyty = idWizyty;
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

		public BigDecimal getSuma() {
			return suma;
		}

		public void setSuma(BigDecimal suma) {
			this.suma = suma;
		}

		public String getTytul() {
			return tytul;
		}

		public void setTytul(String tytul) {
			this.tytul = tytul;
		}

		public WizytaPlatnosc(Integer idWizyty, String imie, String nazwisko, BigDecimal suma, String tytul) {
			super();
			this.idWizyty = idWizyty;
			this.imie = imie;
			this.nazwisko = nazwisko;
			this.suma = suma;
			this.tytul = tytul;
		}

	}
}
