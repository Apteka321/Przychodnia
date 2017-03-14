package model.repository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import model.Konto;
import model.Lekarz;
import model.Pacjent;
import model.Pielegniarka;
import model.Recepcjonistka;

public interface KontaRepository {
	public void dodajKontoAdministratora(Konto kontoAdministratora);

	public void dodajKontoPielegniarki(Pielegniarka pielegniarka);

	public void dodajKontoRecepcjonistki(Recepcjonistka recepcjonistka);

	public void dodajKontoLekarza(Lekarz lekarz);

	public void dodajKontoPacjenta(Pacjent pacjent);
}
