package model.repository.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
//import com.mchange.v3.filecache.FileNotCachedException;

import View.Komunikaty;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.HibernateUtil;
import model.Konto;
import model.Lekarz;
import model.Pacjent;
import model.Pielegniarka;
import model.Recepcjonistka;
import model.repository.KontoRepository;

public class KontoRepositoryImpl implements KontoRepository {

	public void zapamietanieLoginuDoPliku(String adresDoPliku, String login) throws FileNotFoundException {
		PrintWriter zapis = new PrintWriter(adresDoPliku);
		zapis.println(login);
		zapis.close();
	}

	public String wczytanieLoginuZPliku(String adresDoPliku) throws FileNotFoundException {
		File file = new File(adresDoPliku);
		String zdanie = null;
		if (file.isFile()) {
			Scanner in = new Scanner(file);
			zdanie = in.nextLine();
		} 
		return zdanie;
	}

	public int zwrocenieTypuKonta(String login, String haslo) {
		int typ_konta = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery("from Konto where Login = :logi and Haslo = :haslo");
		query.setParameter("logi", login);
		query.setParameter("haslo", haslo);
		List<Konto> list = query.list();
		for (Konto typ : list) {
			typ_konta = typ.getTyp_konta();
		}

		return typ_konta;
	}

	public void otwarcieNowejScenyZAdresu(String adresSceny, String nazwaSceny) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource(adresSceny));
		Stage primaryStage = new Stage();
		Scene scena = new Scene(root);

		primaryStage.setTitle(nazwaSceny);
		primaryStage.setScene(scena);
		primaryStage.show();

	}
	
	/*
	 * #########################################################
	 *     OD Arka
	 * #########################################################
	 */
	
	
	public void dodajKontoAdministratora(Konto kontoAdministratora) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(kontoAdministratora);

			session.getTransaction().commit();
			
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto administratora!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ administratora!");
		}

	}

	public void dodajKontoPielegniarki(Pielegniarka pielegniarka) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(pielegniarka);

			session.getTransaction().commit();
		
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto pielegniarki!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ pielêgniarki!");
		}

	}

	public void dodajKontoRecepcjonistki(Recepcjonistka recepcjonistka) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(recepcjonistka);

			session.getTransaction().commit();
		
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto recepcjonistki!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ recepcjonistki!");
		}

	}

	public void dodajKontoLekarza(Lekarz lekarz) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(lekarz);

			session.getTransaction().commit();

			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto lekarza!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ lekarza!");
		}

	}

	public void dodajKontoPacjenta(Pacjent pacjent) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(pacjent);

			session.getTransaction().commit();

			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto Pacjenta!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ Pacjenta!");
		}

	}

	public List<Pielegniarka> getListaPielegniarek() {
		List<Pielegniarka> listaPielegniarek = new ArrayList<Pielegniarka>();
		String hql = "FROM Pielegniarka";

		//try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = (Query) session.createQuery(hql);
			listaPielegniarek.addAll(((org.hibernate.Query) query).list());


/*		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy pielegniarek!");
		}*/
		// sortowanie listy
		if (listaPielegniarek.size() > 0) {
			listaPielegniarek.sort(new Comparator<Pielegniarka>() {
				public int compare(Pielegniarka o1, Pielegniarka o2) {
					return o1.getOsoba().getNazwisko().compareTo(o2.getOsoba().getNazwisko());
				}
			});
		}
		return listaPielegniarek;
	}

	public List<Lekarz> getListaLekarzy() {
		List<Lekarz> listaLekarzy = new ArrayList<Lekarz>();
		String hql = "FROM Lekarz";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = (Query) session.createQuery(hql);
			listaLekarzy.addAll(((org.hibernate.Query) query).list());
	

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy Lekarzy!");
		}

		// sortowanie listy
		if (listaLekarzy.size() > 0) {
			listaLekarzy.sort(new Comparator<Lekarz>() {
				public int compare(Lekarz o1, Lekarz o2) {
					return o1.getOsoba().getNazwisko().compareTo(o2.getOsoba().getNazwisko());
				}
			});
		}
		return listaLekarzy;
	}

	public List<Recepcjonistka> getListaRecepcjonistek() {
		List<Recepcjonistka> listaRecepcjonistek = new ArrayList<Recepcjonistka>();
		String hql = "FROM Recepcjonistka";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaRecepcjonistek.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy recepcjonistek!");
		}

		// sortowanie listy
		if (listaRecepcjonistek.size() > 0) {
			listaRecepcjonistek.sort(new Comparator<Recepcjonistka>() {
				public int compare(Recepcjonistka o1, Recepcjonistka o2) {
					return o1.getOsoba().getNazwisko().compareTo(o2.getOsoba().getNazwisko());
				}
			});
		}
		return listaRecepcjonistek;
	}

	

}
