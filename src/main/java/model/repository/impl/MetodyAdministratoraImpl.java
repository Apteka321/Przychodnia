package model.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.From;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Controller.EdytujSpecjalizacjeController.wyborSpecjalziacji;
import View.Komunikaty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import model.HibernateUtil;
import model.Konto;
import model.Lekarz;
import model.Lista_zabiegow;
import model.Osoba;
import model.Pacjent;
import model.Sala;
import model.Specjalizacja;
import model.repository.MetodyAdministratora;

public class MetodyAdministratoraImpl implements MetodyAdministratora {

	/*
	 * Metoda przyjmuje jako argumenty nazwe specajlizacji cena za wiizyte i
	 * czas. Dodaje ona specjalizacje do bazy danych.
	 * 
	 */

	public void dodajSpecjaliste(String nazwaSpecjalizacj, BigDecimal koszt, int czas) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Specjalizacja specjalizacja = new Specjalizacja();
		try {
			specjalizacja.setNazwa(nazwaSpecjalizacj);
			specjalizacja.setKoszt_wizyty(koszt);
			specjalizacja.setCzas_wizyty(czas);
			session.save(specjalizacja);
			session.getTransaction().commit();
			Komunikaty.wyswietlInformacje("Specjalizacja", "Specjalizacja pomyslnie zosta³a dodana.");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("Specjalizacja", "Wyst¹pi³ problem z dodaniem speecjalizacji.");
		}
		session.close();

	}

	/*
	 * Metoda przyjmuje jako wartosci nazwe zabiegu iraz cene. Dodaje ona do
	 * bazy danych podany zabieg.
	 */

	public void dodajZabieg(String nazwaZabiegu, BigDecimal cenazZaZabieg) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Lista_zabiegow lista_zabiegow = new Lista_zabiegow();
		try {

			lista_zabiegow.setNazwa(nazwaZabiegu);
			lista_zabiegow.setCena(cenazZaZabieg);
			session.save(lista_zabiegow);
			session.getTransaction().commit();
			Komunikaty.wyswietlInformacje("Zabieg", "Zabieg pomyslnie zosta³a dodana.");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("Zabieg", "Wyst¹pi³ problem z dodaniem zabiegu.");
		}
		session.close();

	}

	/*
	 * Metoda zwraca nazwe wszystkich zabiegów posortowanych rosnaco.
	 *
	 */

	public List<String> wypiszNazweZabiegu() {
		String nazwa = null;
		final List<String> zabieg = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Lista_zabiegow ORDER BY nazwa");
		List<Lista_zabiegow> list = query.list();
		for (Lista_zabiegow typ : list) {
			nazwa = typ.getNazwa();
			zabieg.add(nazwa);
		}
		session.close();
		return zabieg;
	}

	public String wybranegoZabieguDoEdycji(ChoiceBox<String> tabelaZabiegow) {

		return tabelaZabiegow.getValue();

	}
	/*
	 * Metoda jako agrumenty przyjmuje nazwe zabiegu,cene i tabele zabiegow,
	 * które nastepne sotaja wykorzystane do aktualizacji danych o zabiegu.
	 * tabelaZabiegów bedzie przyjmowal nazwe zabiegu ktory chcemy edytowac.
	 */

	public void edytujZabieg(String nazwaZabiegu, String cena, ChoiceBox<String> tabelaZabiegow) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		Query query = session
				.createSQLQuery("update `Lista zabiegow` set cena = :cena, nazwa = :nazwa" + " where nazwa = :nazwa2 ");

		BigDecimal nowacena = new BigDecimal(cena);
		query.setParameter("nazwa", nazwaZabiegu);
		query.setParameter("cena", nowacena);
		query.setParameter("nazwa2", tabelaZabiegow.getValue());
		int result = query.executeUpdate();
		if (result == 0) {
			Komunikaty.wyswietlOstrzezenie("Zabieg", "Zabieg o takiej nazwie nie istnieje");
		} else {
			Komunikaty.wyswietlInformacje("Zabieg", "Pomyœlnie zaktualizowano zabieg");
		}
		session.getTransaction().commit();
		session.close();

	}
	/*
	 * 
	 */

	public void usuwanieZabiegu(ChoiceBox<String> tabelaZabiegow) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		Query query = session.createSQLQuery("Delete from `Lista zabiegow` where nazwa = :nazwa2");
		query.setParameter("nazwa2", tabelaZabiegow.getValue());
		int result = query.executeUpdate();
		if (result == 0) {
			Komunikaty.wyswietlOstrzezenie("Zabieg", "Zabieg o takiej nazwie nie istnieje");
		} else {
			Komunikaty.wyswietlInformacje("Zabieg", "Pomyœlnie usuniêto zabieg");
		}
		session.getTransaction().commit();
		session.close();
	}

	/*
	 * 
	 * Modyfikacja specjalizacji
	 * 
	 */

	/*
	 * Metoda zwraca liste wszystkich specjalizacji znajdujacych sie w bazie
	 * danych. Specjalizacje sa posortowane rosnaco.
	 * 
	 */

	public List<String> wypiszNazwySpecjalizacji() {
		String nazwa = null;
		final List<String> specjalizacja = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Specjalizacja ORDER BY nazwa");
		List<Specjalizacja> list = query.list();
		for (Specjalizacja typ : list) {
			nazwa = typ.getNazwa();
			specjalizacja.add(nazwa);
		}
		session.close();
		return specjalizacja;

	}
	/*
	 * Metoda przyjmuje jako argumenty nazwe specjalizacji, cene, czas oraz
	 * tablice specjalizacji. tablicaSpecjalizacji przechowuje nazyw
	 * specajlziacji do edycji.
	 * 
	 */

	public void edycjaSpecjalizacji(String nazwaSpecjalizacji, BigDecimal cena, int czas,
			ChoiceBox<String> tabelaSpecjalizacji) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.getTransaction().begin();

		Query query = session
				.createSQLQuery("update Specjalizacja set koszt_wizyty = :cena, nazwa = :nazwa, czas_wizyty = :czas"
						+ " where nazwa = :nazwa2 ");

		query.setParameter("nazwa", nazwaSpecjalizacji);
		query.setParameter("cena", cena);
		query.setParameter("czas", czas);
		query.setParameter("nazwa2", tabelaSpecjalizacji.getValue());
		int result = query.executeUpdate();
		if (result == 0) {
			Komunikaty.wyswietlOstrzezenie("Specjalizcja", "Specjazlicja o takiej nazwie nie istnieje");
		} else {

			Komunikaty.wyswietlInformacje("Specjalizcja", "Pomyœlnie zaktualizowano specjalizacje");
		}
		session.getTransaction().commit();
		session.close();
	}

	/*
	 * Usuwa wybran¹ z listy specjazlizacje.
	 * 
	 */

	public void usuwanieSpecjalizacji(ChoiceBox<String> tabelaSpecjalizacji) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.getTransaction().begin();

		Query query = session.createSQLQuery("Delete from Specjalizacja where nazwa = :nazwa2");

		query.setParameter("nazwa2", tabelaSpecjalizacji.getValue());
		int result = query.executeUpdate();

		if (result == 0) {
			Komunikaty.wyswietlOstrzezenie("Specjalizcja", "Specjazlicja o takiej nazwie nie istnieje");
		} else {
			Komunikaty.wyswietlInformacje("Specjalizcja", "Pomyœlnie usunieta specjalizacje");
		}

		session.getTransaction().commit();
		session.close();
	}

	public String wybranegoSpecjalizacjiDoEdycji(ChoiceBox<String> tabelaSpecjalizacji) {
		return tabelaSpecjalizacji.getValue();
	}

	/*
	 * Dodaj modyfikuj sale
	 * 
	 */
	public void DodajSale(int numerSali, String opisSali) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Sala sala = new Sala();
		try {
			sala.setNumer_sali(numerSali);
			sala.setSala_dla(opisSali);
			session.save(sala);
			session.getTransaction().commit();
			Komunikaty.wyswietlInformacje("Sukces", "Dodano sale");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Sala nie mo¿e zostaæ dodana");
		}
		session.close();

	}

	public List<Integer> wypiszWszystkieNazwySal() {

		final List<Integer> numerSali = new ArrayList<Integer>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Sala");
		List<Sala> list = query.list();
		for (Sala typ : list) {
			numerSali.add(typ.getNumer_sali());

		}
		session.close();
		return numerSali;
	}

	public void edycjaSali(int numerSali, String opisSali, ChoiceBox<Integer> listaSal) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.getTransaction().begin();

		Query query = session.createSQLQuery("update Sala set numer_sali = :numerSali, sala_dla = :opisSali"
				+ " where numer_sali = :numerWybranejSali ");

		query.setParameter("numerSali", numerSali);
		query.setParameter("opisSali", opisSali);
		query.setParameter("numerWybranejSali", listaSal.getValue());
		int result = query.executeUpdate();
		if (result == 0) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "ala o takim numerze nie istnieje");

		} else {
			Komunikaty.wyswietlInformacje("Sukces", "Zaktualizowano sale");
		}
		session.close();
		session.getTransaction().commit();

	}

	public Integer wybranegoSaliDoEdycji(ChoiceBox<Integer> listaSal) {
		return listaSal.getValue();
	}

	public void usunSale(ChoiceBox<Integer> listaSal) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		Query query = session.createSQLQuery("Delete from Sala where numer_sali = :numerSali");
		query.setParameter("numerSali", listaSal.getValue());
		int result = query.executeUpdate();
		if (result == 0) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Sala o takim numerze nie istnieje");

		} else {
			Komunikaty.wyswietlInformacje("Sukces", "Sala zosta³a usuniêta");
		}
		session.getTransaction().commit();
		session.close();

	}

	public List<String> wypiszLekarz() {
		final List<String> nazwaLekarzy = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Lekarz");
		List<Lekarz> list = query.list();
		for (Lekarz typ : list) {
			nazwaLekarzy.add(typ.getOsoba().getImie() + " " + typ.getOsoba().getNazwisko());

		}
		session.close();
		return nazwaLekarzy;
	}

	public void dodajSpecjalizacje(List<wyborSpecjalziacji> nawaSpecjalizacji, String nazwaLekarza) {
		String[] parts = nazwaLekarza.split(" ");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		Query zapytanieOLekarza = session
				.createQuery("from Osoba where imie = :imieLekarza and nazwisko = :nazwiskoLekarza");
		zapytanieOLekarza.setParameter("imieLekarza", parts[0]);
		zapytanieOLekarza.setParameter("nazwiskoLekarza", parts[1]);
		Osoba osoba = (Osoba) zapytanieOLekarza.list().get(0);
		System.out.println(osoba.getLekarz().getID());
		Query zapytanieOIdLekarza = session.createQuery("from Lekarz where ID = :idLekarza");
		zapytanieOIdLekarza.setParameter("idLekarza", osoba.getLekarz().getID());
		Lekarz lekarz = (Lekarz) zapytanieOIdLekarza.list().get(0);


		for (wyborSpecjalziacji wSpecjalziacji : nawaSpecjalizacji) {
			Query query = session.createQuery("from Specjalizacja where nazwa= :nazwaSpec");
			query.setParameter("nazwaSpec", wSpecjalziacji.getNazwa());
			Specjalizacja specjalizacja = (Specjalizacja) query.list().get(0);
			lekarz.getSpecjalizacja().add(specjalizacja);

		}
		session.getTransaction().commit();
		session.close();

	}

}
