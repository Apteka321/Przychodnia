package model.repository;

import java.util.Set;

import model.Plan_dzienny;
import model.Plan_pracy;
import model.Pracownik;
import model.Wolne_od_pracy;

public interface PlanPracyRepository {
	
	public void dodajPlanPracownikowi(Pracownik pracownik, Plan_pracy plan_pracy, Set<Plan_dzienny> setPlanowDziennych);

	public void dodajWolnePracownikowi(Pracownik pracownik, Wolne_od_pracy wolne_od_pracy);
	
	public Plan_pracy wczytajPlanPracyPracownika(Pracownik pracownik);
}
