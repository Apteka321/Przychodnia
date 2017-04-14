package model.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import com.jfoenix.controls.JFXDatePicker;

import Controller.ObslugaPlatnosciController.WizytaPlatnosc;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import model.Lekarz;
import model.Pacjent;
import model.Recepcjonistka;
import model.Specjalizacja;


public interface MetodyRecepcjonistra {
	public List<String> wypisanieSpecjalizacji();

	public List<Integer> wypiszIDLekarza(String specjalizacja);

	public List<String> wypisanieLekarzy(List<Integer> ID);
	
	public List<String> wypisanieWszystkichPacjentow(); 
	
	public void dodajWizyte(String PESEL,String nazwaSpecjalizacji,String nazwaLekarza,Time godzina, JFXDatePicker data,String czyUbezpieczony);
	
	public java.sql.Time zmienCzas(LocalTime localTime) ;
	
	public void aktualizujDateWizyty(String PESEL,JFXDatePicker dataWizyt,Integer idPacjenta,Time godzina);
	
	public void aktualizujPacjenta(Pacjent pacjent);
	
	public void dokonajPlatnosci(Integer idWizyty, Recepcjonistka recepcjonistka,BigDecimal kwota,Date dataPlatnosci);
	
	
	


}
