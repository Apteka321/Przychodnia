package model.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.stage.Stage;
import model.Konto;
import model.Lekarz;
import model.Pacjent;
import model.Pielegniarka;
import model.Recepcjonistka;

public interface KontoRepository {
	public int zwrocenieTypuKonta(String login, String haslo);

	public void zapamietanieLoginuDoPliku(String url, String login) throws FileNotFoundException;

	public String wczytanieLoginuZPliku(String url) throws FileNotFoundException;
	
	public void otwarcieNowejScenyZAdresu(String adresSceny, String nazwaSceny) throws IOException;
	
	public void dodajKontoAdministratora(Konto kontoAdministratora);

	public void dodajKontoPielegniarki(Pielegniarka pielegniarka);

	public void dodajKontoRecepcjonistki(Recepcjonistka recepcjonistka);

	public void dodajKontoLekarza(Lekarz lekarz);

	public void dodajKontoPacjenta(Pacjent pacjent);

	public List<Pielegniarka> getListaPielegniarek();

	public List<Lekarz> getListaLekarzy();

	public List<Recepcjonistka> getListaRecepcjonistek();
			

}
