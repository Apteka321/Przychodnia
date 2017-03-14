package model.repository.impl;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.Session;

import View.RejestracjaPracownikowMain;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import model.HibernateUtil;
import model.Konto;
import model.Lekarz;
import model.Pacjent;
import model.Pielegniarka;
import model.Recepcjonistka;
import model.repository.KontaRepository;

public class KontaRepositoryImpl implements KontaRepository {

	public void dodajKontoAdministratora(Konto kontoAdministratora) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(kontoAdministratora);

			session.getTransaction().commit();
			session.close();
			wyswietlInformacje("Sukces", "Pomy�lnie dodano konto administratora!");
		} catch (Exception e) {
			wyswietlOstrzezenie("B��d", "Nie mo�na doda� administratora!");
		}

	}

	public void dodajKontoPielegniarki(Pielegniarka pielegniarka) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(pielegniarka);

			session.getTransaction().commit();
			session.close();
			wyswietlInformacje("Sukces", "Pomy�lnie dodano konto pielegniarki!");
		} catch (Exception e) {
			wyswietlOstrzezenie("B��d", "Nie mo�na doda� piel�gniarki!");
		}

	}

	public void dodajKontoRecepcjonistki(Recepcjonistka recepcjonistka) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(recepcjonistka);

			session.getTransaction().commit();
			session.close();
			wyswietlInformacje("Sukces", "Pomy�lnie dodano konto recepcjonistki!");
		} catch (Exception e) {
			wyswietlOstrzezenie("B��d", "Nie mo�na doda� recepcjonistki!");
		}

	}

	public void dodajKontoLekarza(Lekarz lekarz) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(lekarz);

			session.getTransaction().commit();
			session.close();
			wyswietlInformacje("Sukces", "Pomy�lnie dodano konto lekarza!");
		} catch (Exception e) {
			wyswietlOstrzezenie("B��d", "Nie mo�na doda� lekarza!");
		}

	}

	public void dodajKontoPacjenta(Pacjent pacjent) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(pacjent);

			session.getTransaction().commit();
			session.close();
			wyswietlInformacje("Sukces", "Pomy�lnie dodano konto Pacjenta!");
		} catch (Exception e) {
			wyswietlOstrzezenie("B��d", "Nie mo�na doda� Pacjenta!");
		}

	}

	public static void wyswietlOstrzezenie(String naglowek, String tresc) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Ostrze�enie");
		alert.setHeaderText(naglowek);
		alert.setContentText(tresc);
		alert.initStyle(StageStyle.UTILITY);

		alert.showAndWait();
	}

	public static void wyswietlInformacje(String naglowek, String tresc) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacja");
		alert.setHeaderText(naglowek);
		alert.setContentText(tresc);
		alert.initStyle(StageStyle.UTILITY);

		alert.showAndWait();
	}

}
