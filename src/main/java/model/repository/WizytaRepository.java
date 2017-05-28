package model.repository;

import java.util.List;

import model.Recepta;
import model.Skierowanie;
import model.Wizyta;

public interface WizytaRepository {

	public List<Wizyta> getListaWizyt();
	
	public void dodajRecepte(Recepta recepta);

	public void dodajSkierowanie(Skierowanie skierowanie);
}
