package model.repository.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import View.Komunikaty;
import model.HibernateUtil;
import model.Konto;
import model.Lekarz;
import model.Pacjent;
import model.Pielegniarka;
import model.Pracownik;
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
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto administratora!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ administratora!");
		}

	}

	public void dodajKontoPielegniarki(Pielegniarka pielegniarka) {
		//try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(pielegniarka);

			session.getTransaction().commit();
			session.close();
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto pielegniarki!");

/*		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ pielêgniarki!");
		}*/

	}

	public void dodajKontoRecepcjonistki(Recepcjonistka recepcjonistka) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(recepcjonistka);

			session.getTransaction().commit();
			session.close();
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
			session.close();
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
			session.close();
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano konto Pacjenta!");
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ Pacjenta!");
		}

	}

	public List<Pielegniarka> getListaPielegniarek() {
		List<Pielegniarka> listaPielegniarek = new ArrayList<Pielegniarka>();
		String hql = "FROM Pielegniarka";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaPielegniarek.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy pielegniarek!");
		}
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
			Query query = session.createQuery(hql);
			listaLekarzy.addAll(query.list());
			session.close();

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

	public void aktualizujPracownika(Pracownik pracownik) {

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.update(pracownik.getClass().getSimpleName(), pracownik);
			transaction.commit();
			session.close();
		} catch (

		Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na zaktualizowaæ pracownika!");
		}
	}

	public List<Pacjent> getListaPacjentow() {
		List<Pacjent> listaPacjentow = new ArrayList<Pacjent>();
		String hql = "FROM Pacjent";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaPacjentow.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy pacjentow!");
		}

		return listaPacjentow;
	}

	public Pacjent getPacjentowByPESEL(String PESEL) {
		Pacjent pacjent = new Pacjent();
		String hql = "FROM Pacjent P WHERE P.PESEL = :pesel";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			query.setParameter("pesel", PESEL);
			pacjent = (Pacjent) query.list().get(0);
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy pacjentow!");
		}

		return pacjent;
	}

}
