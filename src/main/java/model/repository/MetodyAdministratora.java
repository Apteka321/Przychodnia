package model.repository;

import java.math.BigDecimal;
import java.util.List;

import javafx.scene.control.ChoiceBox;

public interface MetodyAdministratora {
	
	public void dodajSpecjaliste(String nazwa, BigDecimal koszt, int czas);
	public void dodajZabieg(String nazwa, BigDecimal cena);
	public void edytujZabieg(String nazwa,String cena,ChoiceBox<String> tabelaZabiegow);
	public  List<String> wypiszNazweZabiegu();
	public String wybranegoZabieguDoEdycji(ChoiceBox<String> tabelaZabiegow);
	public void usuwanieZabiegu(ChoiceBox<String> tabelaZabiegow);
	/************************************/
	public void edycjaSpecjalizacji(String nazwa, BigDecimal cena,int czas, ChoiceBox<String> tabelaSpecjalizacji);
	public void usuwanieSpecjalizacji(ChoiceBox<String> tabelaSpecjalizacji);
	public String wybranegoSpecjalizacjiDoEdycji(ChoiceBox<String> tabelaSpecjalizacji);
	
	/************************************/
	/*
	 * Dodawanie modyfikowanie sal
	 */
	public void DodajSale(int numerSali, String opisSali);
	public List<Integer> wypiszWszystkieNazwySal();
	public void edycjaSali(int numerSali, String opisSali,ChoiceBox<Integer> listaSal);
	public Integer wybranegoSaliDoEdycji(ChoiceBox<Integer> listaSal);
	public void usunSale(ChoiceBox<Integer> listaSal);

	

}
