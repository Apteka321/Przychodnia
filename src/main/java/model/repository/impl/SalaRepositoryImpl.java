package model.repository.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import View.Komunikaty;
import model.HibernateUtil;
import model.Lekarz;
import model.Sala;
import model.repository.SalaRepository;

public class SalaRepositoryImpl implements SalaRepository {

	@Override
	public List<Sala> getListaSal() {
		List<Sala> listaSal = new ArrayList<Sala>();
		String hql = "FROM Sala";

		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			listaSal.addAll(query.list());
			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na Pobraæ listy sal!");
		}

		return listaSal;
	}

}
