package model.repository.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Where;
import org.hibernate.dialect.Ingres10Dialect;
import org.hibernate.loader.plan.build.internal.returns.AbstractExpandingFetchSource;
import org.omg.CORBA.LongHolder;

import com.jfoenix.controls.JFXDatePicker;

import Controller.ModyfikowanieZapranowanejWizytyController.wiyztaOsoba;

import View.Komunikaty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javassist.compiler.ast.NewExpr;
import model.HibernateUtil;
import model.Konto;
import model.Lekarz;
import model.Leki;
import model.Leki_Recepta;
import model.Lista_zabiegow;
import model.Osoba;
import model.Pacjent;
import model.Pielegniarka;
import model.Platnosc;
import model.Recepcjonistka;
import model.Specjalizacja;
import model.Wizyta;
import model.Zabieg;
import model.repository.MetodyRecepcjonistra;

public class MetodyRecepcjonistkaImpl implements MetodyRecepcjonistra {
	KontoRepositoryImpl kontoRepositoryImpl = new KontoRepositoryImpl();

	/*
	 * Metoda zwarca wszystkie dostepne specjalziacje w bazie danych.
	 * 
	 */
	public List<String> wypisanieSpecjalizacji() {

		List<String> specjalizacja = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Specjalizacja");
		List<Specjalizacja> list = query.list();
		for (Specjalizacja typ : list) {
			specjalizacja.add(typ.getNazwa());
		}
		session.close();
		return specjalizacja;
	}

	/*
	 * Metoda zwarca id Wszystkich lekarzy którzy maja wybran¹ specjalizacje
	 * 
	 */
	public List<Integer> wypiszIDLekarza(String nazwaSecjalizacji) {
		List<Integer> idSpecjalizacja = new ArrayList<Integer>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Specjalizacja where nazwa = :zmienna");
		query.setParameter("zmienna", nazwaSecjalizacji);
		List<Specjalizacja> list = query.list();
		for (Specjalizacja typ : list) {
			Set<Lekarz> setIdLekarzy = (Set<Lekarz>) typ.getLekarz();
			for (Lekarz lekarz : setIdLekarzy) {
				idSpecjalizacja.add(lekarz.getID());
				System.out.println(lekarz.getID());
			}
			;
		}
		session.close();
		return idSpecjalizacja;

	}

	/*
	 * Metoda zwarca dane Wszystkich lekarzy którzy spe³niaja takie ID
	 * 
	 */
	public List<String> wypisanieLekarzy(List<Integer> listaIdLekarzy) {
		List<String> specjalizacja = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();

		for (int idLekarza : listaIdLekarzy) {
			Query query = session.createQuery("from Lekarz where ID = :zmienna");
			query.setParameter("zmienna", idLekarza);
			Lekarz lekarz = (Lekarz) query.list().get(0);
			specjalizacja.add(lekarz.getOsoba().getImie() + " " + lekarz.getOsoba().getNazwisko());
		}
		session.close();
		return specjalizacja;
	}
/*
 * Metod wypisuje wszystkich pacjentów.
 *
 */
	public List<String> wypisanieWszystkichPacjentow() {
		List<String> danePacjenta = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Pacjent");
		List<Pacjent> list = query.list();
		for (Pacjent pacjent : list) {
			danePacjenta.add(pacjent.getPESEL() + " " + pacjent.getOsoba().getImie() + " "
					+ pacjent.getOsoba().getNazwisko() + " " + pacjent.getOsoba().getMiejscowosc());

		}
		session.close();
		return danePacjenta;
	}

	public java.sql.Time zmienCzas(LocalTime localTime) {
		return java.sql.Time.valueOf(localTime);
	}

	public void dodajWizyte(String PESEL, String nazwaSpecjalizacji, String nazwaLekarza, Time localTime,
			JFXDatePicker data, String czyUbezpieczony) {
		String[] parts = nazwaLekarza.split(" ");

		Session session = HibernateUtil.getSessionFactory().openSession();

		Query szukajIDLekarza = session
				.createQuery("FROM Osoba where imie = :imieLekarza and nazwisko = :nazwiskoLekarza ");
		szukajIDLekarza.setParameter("imieLekarza", parts[0]);
		szukajIDLekarza.setParameter("nazwiskoLekarza", parts[1]);
		Osoba osoba = (Osoba) szukajIDLekarza.list().get(0);
		Lekarz lekarz = osoba.getLekarz();

		String pacjentPESEL = "FROM Pacjent WHERE PESEL = " + PESEL;
		Query query2 = session.createQuery(pacjentPESEL);
		Pacjent pacjent = (Pacjent) query2.list().get(0);

		Query idSpecjalizacji = session.createQuery("FROM Specjalizacja WHERE nazwa =  :specjalziacja");
		idSpecjalizacji.setParameter("specjalziacja", nazwaSpecjalizacji);
		Specjalizacja specjalizacja = (Specjalizacja) idSpecjalizacji.list().get(0);

		String idRecepcjonistki = "FROM Recepcjonistka WHERE ID = " + 1;
		Query query3 = session.createQuery(idRecepcjonistki);
		Recepcjonistka recepcjonistka = (Recepcjonistka) query3.list().get(0);
		
		session.beginTransaction();
		Wizyta wizyta = new Wizyta();
		int ubezpieczenie;
		if (czyUbezpieczony.equals("Tak")) {
			ubezpieczenie = 1;
		} else {
			ubezpieczenie = 0;
		}

		wizyta.setGodzina(localTime);
		wizyta.setData(new Date(data.getValue().getYear() - 1900, data.getValue().getMonthValue() - 1,
				data.getValue().getDayOfMonth()));
		wizyta.setCzy_ubezpieczony(ubezpieczenie);
		wizyta.setStatus_wizyty("tak");
		wizyta.setID_Specjalizacji(specjalizacja);
		wizyta.setPESEL_pacjenta(pacjent);
		wizyta.setID_lekarza(lekarz);
		wizyta.setRecepcjonistka(recepcjonistka);
		session.save(wizyta);
		session.getTransaction().commit();



		session.close();

	}

	public static java.util.Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
/*
 * Aktualizacja daty na wizyte
 * 
 */
	public void aktualizujDateWizyty(String PESEL, JFXDatePicker data, Integer idPacjenta,Time godzina) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();

		String pacjentPESEL = "FROM Wizyta WHERE PESEL_pacjenta = " + PESEL + "and ID =" + idPacjenta;
		Query query2 = session.createQuery(pacjentPESEL);
		Wizyta wizyta = (Wizyta) query2.list().get(0);

		wizyta = (Wizyta) session.get(Wizyta.class, wizyta.getID());

		wizyta.setData(new Date(data.getValue().getYear() - 1900, data.getValue().getMonthValue() - 1,
				data.getValue().getDayOfMonth()));
		wizyta.setGodzina(godzina);
		session.update(wizyta);
		session.getTransaction().commit();
		session.close();

	}
	
	public List<Pacjent> wypiszListePacjentow() {
		List<Pacjent> listaPacjentow = new ArrayList<Pacjent>();
		String hql = "FROM Pacjent";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaPacjentow.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy Lekarzy!");
		}

		return listaPacjentow;
	}

	public void aktualizujPacjenta(Pacjent pacjent) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.update(pacjent.getClass().getSimpleName(), pacjent);
			transaction.commit();
			session.close();

		} catch (

		Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na zaktualizowaæ pracownika!");
		}
		
	}

	public void dokonajPlatnosci(Integer idWizyty, Recepcjonistka recepcjonistka, BigDecimal kwota, Date dataPlatnosci) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();

		String aktualizacjaWizyty = "FROM Wizyta WHERE ID =" + idWizyty;
		Query query2 = session.createQuery(aktualizacjaWizyty);
		Wizyta wizyta = (Wizyta) query2.list().get(0);
		wizyta = (Wizyta) session.get(Wizyta.class, wizyta.getID());
		wizyta.setStatus_wizyty("Zaplacono");
		session.update(wizyta);
		
		
	
		Platnosc platnosc = new Platnosc();
		platnosc.setData_platnosci(dataPlatnosci);
		platnosc.setID_wizyty(wizyta);
		platnosc.setKwota(kwota);
		platnosc.setRecepcjonistka(recepcjonistka);
		platnosc.setTytul_platnosci("P³atnoœæ za wizyte");
		session.save(platnosc);
		session.getTransaction().commit();
		session.close();
		
		
	}




	

}
