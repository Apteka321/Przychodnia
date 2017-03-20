package model.repository;

import java.util.Set;

import model.Plan_dzienny;
import model.Plan_pracy;
import model.Pracownik;

public interface PlanPracyRepository {
	public void dodajPlanPracownikowi(Pracownik pracownik, Plan_pracy plan_pracy, Set<Plan_dzienny> setPlanowDziennych);
}
