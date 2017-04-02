/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import Controller.NoweZamowienieController;
import Controller.ZatwierdzenieZamowieniaController.ZatwierdzenieZamowienia;
import java.util.List;
import javafx.collections.ObservableList;
import model.Pielegniarka;
import model.Produkt_Zamowienia;
import model.Sala;
import model.Zamowienia;




/**
 *
 * @author Daniel
 */
public interface Zamowienie {

    public Pielegniarka pobierzPielegnierkeWedlugID(int identyfikatorPielegniarki);
    public String utworzZamowienie(ObservableList<NoweZamowienieController.ProduktZamowienia> listaProduktowZamowienia, Pielegniarka pielegniarka);
    public List<Zamowienia> pobierzZamowienia();
    public List<Produkt_Zamowienia> pobierzProduktZamowienia(Integer idZamowienia);
    public String usunZaznaczoneZamowienie(List<Produkt_Zamowienia> listaProduktowZamowieniawedlugId);
    public String zapiszEdytowaneZamowienie(ObservableList<ZatwierdzenieZamowienia> listaProduktowDoWyswietleni);
    public List<Sala> pobierzListeSal();

}