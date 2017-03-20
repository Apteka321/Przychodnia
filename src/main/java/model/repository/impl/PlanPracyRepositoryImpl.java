package model.repository.impl;

import java.util.Set;

import org.hibernate.Session;

import View.Komunikaty;
import model.HibernateUtil;
import model.Plan_dzienny;
import model.Plan_pracy;
import model.Pracownik;
import model.repository.PlanPracyRepository;

public class PlanPracyRepositoryImpl implements PlanPracyRepository {

	@Override
	public void dodajPlanPracownikowi(Pracownik pracownik, Plan_pracy plan_pracy,
			Set<Plan_dzienny> setPlanowDziennych) {

		// try {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(plan_pracy);

		for (Plan_dzienny plan_dzienny : setPlanowDziennych) {
			plan_dzienny.setPlan_pracy(plan_pracy);
			session.save(plan_dzienny);
		}
		plan_pracy.setPlan_dzienny(null);

		pracownik.setPlan_pracy(plan_pracy);
		session.update(pracownik);

		session.getTransaction().commit();
		session.close();
		Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano plan pracy!");
		/*
		 * } catch (Exception e) { Komunikaty.wyswietlOstrzezenie("B³¹d",
		 * "Nie mo¿na dodaæ planu pracy!"); }
		 */
	}

}
