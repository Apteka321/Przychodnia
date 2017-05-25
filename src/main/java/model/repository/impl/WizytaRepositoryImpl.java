package model.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;

import View.Komunikaty;
import model.HibernateUtil;
import model.Pacjent;
import model.Leki_Recepta;
import model.Plan_dzienny;
import model.Recepta;
import model.Skierowanie;
import model.Wizyta;
import model.repository.WizytaRepository;

public class WizytaRepositoryImpl implements WizytaRepository {

	@Override
	public List<Wizyta> getListaWizyt() {
		List<Wizyta> listaWizyt = new ArrayList<Wizyta>();
		String hql = "FROM Wizyta";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaWizyt.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy wizyt!");
		}

		return listaWizyt;
	}

	@Override
	public void dodajRecepte(Recepta recepta) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			//wy³¹czenie update aby nie aktualizowa³o wizyty
			session.setFlushMode(FlushMode.MANUAL);

			session.saveOrUpdate(recepta);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na zapisaæ recepty!!");
		}

	}

	@Override
	public void dodajSkierowanie(Skierowanie skierowanie) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			//wy³¹czenie update aby nie aktualizowa³o wizyty
			session.setFlushMode(FlushMode.MANUAL);

			session.saveOrUpdate(skierowanie);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na zapisaæ skierowania!!");
		}
		
	}

}
