package model.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import View.Komunikaty;
import model.HibernateUtil;
import model.Leki;
import model.repository.LekiRepository;

public class LekiRepositoryImpl implements LekiRepository {

	public List<Leki> pobierzListeLekow() {
		List<Leki> listaLekow = new ArrayList<Leki>();
		String hql = "FROM Leki";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaLekow.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B��d", "Nie mo�na Pobra� listy lek�w!");
		}

		return listaLekow;
	}

	public boolean dodajLek(Leki lek) {
		boolean dodanoPomyslnie = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.save(lek);
			session.close();
			Komunikaty.wyswietlInformacje("Powodzenie", "Pomy�lnie dodano lek do bazy!");
			dodanoPomyslnie = true;
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B��d", "Nie mo�na doda� leku do bazy!");
		}
		return dodanoPomyslnie;

	}

	public void usunLeki(List<Leki> listaLekow) {
		try {

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			for (Leki leki : listaLekow) {
				session.delete(leki);
				System.out.println("Kasowanie: " + leki.getID());
			}
			session.getTransaction().commit();
			session.close();
			Komunikaty.wyswietlInformacje("Powodzenie", "Pomy�lnie skasowano leki!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B��d", "Nie uda�o skasowa� si� lek�w!");
		}
	}

	public boolean modyfikujLek(Leki lek) {
		boolean zmodyfikowano = false;
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();

			// pobranie leku
			String hql = "FROM Leki WHERE ID = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", lek.getID());
			System.out.println("Modyfikowany lek: " + lek.getID());
			Leki lekZBazy = (Leki) query.list().get(0);

			// aktualizacja p�l
			lekZBazy.setIlosc(lek.getIlosc());
			lekZBazy.setNazwa(lek.getNazwa());
			lekZBazy.setProducent(lek.getProducent());

			// zapisanie do bazy zmodyfikowanego leku
			session.update(lekZBazy);
			System.out.println(lekZBazy.getID());
			transaction.commit();
			session.close();
			Komunikaty.wyswietlInformacje("Powodzenie", "Pomy�lnie zmodyfikowano lek.");
			zmodyfikowano = true;
		} catch (

		Exception e) {
			Komunikaty.wyswietlOstrzezenie("B��d", "Nie mo�na zaktualizowa� leku!");
		}

		return zmodyfikowano;

	}
}
