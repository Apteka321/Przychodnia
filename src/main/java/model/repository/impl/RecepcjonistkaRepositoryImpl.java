package model.repository.impl;

import org.hibernate.Session;

import model.HibernateUtil;
import model.Pielegniarka;
import model.repository.RecepcjonistkaRepository;

public class RecepcjonistkaRepositoryImpl implements RecepcjonistkaRepository {

	public void dodajRecepcjonistke(Pielegniarka pielegniarka) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.save(pielegniarka);
		
		session.getTransaction().commit();
		session.close();

	}

}
