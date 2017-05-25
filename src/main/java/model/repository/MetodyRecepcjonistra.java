package model.repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import com.jfoenix.controls.JFXDatePicker;

import javafx.scene.control.ChoiceBox;
import model.Lekarz;
import model.Specjalizacja;


public interface MetodyRecepcjonistra {
	public List<String> wypisanieSpecjalizacji();

	public List<Integer> wypiszIDLekarza(String specjalizacja);

	public List<String> wypisanieLekarzy(List<Integer> ID);
	
	public List<String> wypisanieWszystkichPacjentow(); 
	
	public void dodajWizyte(String PESEL,String nazwaSpecjalizacji,String nazwaLekarza,Time godzina, JFXDatePicker data,String czyUbezpieczony);
	
	public java.sql.Time zmienCzas(LocalTime localTime) ;
	
	public void aktualizujDateWizyty(String PESEL,JFXDatePicker dataWizyt,Integer idPacjenta,Time godzina);
	


}
