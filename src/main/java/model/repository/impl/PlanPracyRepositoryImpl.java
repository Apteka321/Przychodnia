package model.repository.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import View.Komunikaty;
import model.HibernateUtil;
import model.Pielegniarka;
import model.Plan_dzienny;
import model.Plan_pracy;
import model.Pracownik;
import model.Wolne_od_pracy;
import model.repository.PlanPracyRepository;

public class PlanPracyRepositoryImpl implements PlanPracyRepository {

	@Override
	public void dodajPlanPracownikowi(Pracownik pracownik, Plan_pracy plan_pracy,
			Set<Plan_dzienny> setPlanowDziennych) {

		boolean dodacPlan = true;
		if (pracownik.getPlan_pracy() != null) {
			dodacPlan = Komunikaty.wyswietlPotwierdzenie("Uwaga!", "Wybrany pracownik ma ju¿ plan pracy!",
					"Chcesz zast¹piæ plan pracy?");
		}

		if (dodacPlan) {
			try {
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

			} catch (Exception e) {
				Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ planu pracy!");
			}
		}

	}

	@Override
	public void dodajWolnePracownikowi(Pracownik pracownik, Wolne_od_pracy wolne_od_pracy) {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Plan_pracy plan_pracy = pracownik.getPlan_pracy();
			wolne_od_pracy.setPlan_pracy(plan_pracy);
			session.save(wolne_od_pracy);

			pracownik.setPlan_pracy(plan_pracy);
			session.update(pracownik);

			session.getTransaction().commit();
			session.close();
			Komunikaty.wyswietlInformacje("Sukces", "Pomyœlnie dodano wolne!");

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na dodaæ wolnego!");
		}
	}

	@Override
	public Plan_pracy wczytajPlanPracyPracownika(Pracownik pracownik) {

		Plan_pracy plan_pracy = new Plan_pracy();

		try {
			// pobranie planu pracy
			Session session = HibernateUtil.getSessionFactory().openSession();
			String hqlPlanuPracy = "FROM Plan_pracy WHERE ID = :planPracyId";
			Query queryPlanuPracy = session.createQuery(hqlPlanuPracy);
			queryPlanuPracy.setParameter("planPracyId", pracownik.getPlan_pracy().getID());
			plan_pracy = (Plan_pracy) queryPlanuPracy.list().get(0);
			System.out.println(plan_pracy.getWyplata().toString());

			// pobranie planów dziennych wybranego planu pracy
			Set<Plan_dzienny> setPlanowDziennych = new HashSet<Plan_dzienny>();
			String hqlPlanowDziennych = "FROM Plan_dzienny WHERE Plan_pracyID = :planPracyId";
			Query queryPlanowDziennych = session.createQuery(hqlPlanowDziennych);
			queryPlanowDziennych.setParameter("planPracyId", pracownik.getPlan_pracy().getID());
			List<Plan_dzienny> listaPlanowDziennych = queryPlanowDziennych.list();

			// konwersja z list na set
			for (Plan_dzienny plan_dzienny : listaPlanowDziennych) {
				setPlanowDziennych.add(plan_dzienny);
			}

			plan_pracy.setPlan_dzienny(setPlanowDziennych);

			session.close();

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Nie mo¿na pobraæ planu tego pracownika");
		}

		return plan_pracy;

	}

}
