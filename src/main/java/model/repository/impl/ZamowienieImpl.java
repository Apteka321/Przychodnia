/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository.impl;

import static Controller.ListaZamowienController.zamowieniAdministratora;
import Controller.NoweZamowienieController;
import Controller.NoweZamowienieController.ProduktZamowienia;
import Controller.ZatwierdzenieZamowieniaController;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import model.HibernateUtil;
import model.Pielegniarka;
import model.Produkt;
import model.Produkt_Zamowienia;
import model.Sala;
import model.Zamowienia;
import org.hibernate.Query;
import org.hibernate.Session;
import model.repository.Zamowienie;

/**
 *
 * @author Daniel
 */
public class ZamowienieImpl implements Zamowienie {

    public Pielegniarka pobierzPielegnierkeWedlugID(int identyfikatorPielegniarki) {

        //Utworzenie nowego po³¹czenia zapiszEdytowaneZamowienie baz¹ danych 
        Session session = HibernateUtil.getSessionFactory().openSession();
        int id = identyfikatorPielegniarki;
        //Orzygotowanie zapytania pobieraj¹cego dane pielegniarki o wskazanym id
        String hql = "FROM Pielegniarka WHERE ID = " + id;
        //wys³anie zapytania do Bazy danych
        Query query = session.createQuery(hql);
        //Pobranie rezultatu zapytania do obiektu typu Pielegniarka
        Pielegniarka pielegniarka = (Pielegniarka) query.list().get(0);
        session.close();
        return pielegniarka;
    }

    public String utworzZamowienie(ObservableList<ProduktZamowienia> listaProduktowZamowienia, Pielegniarka pielegniarka) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Set<Produkt_Zamowienia> zbiorPozycjiZamowienia = new HashSet<Produkt_Zamowienia>();
        //Przygotowanie obiektu zamówienia 
        Zamowienia noweZamowienie = new Zamowienia();

        //W ka¿dym cyklu pêtli pobierany jest obiekt Pozycji Zamówienia zapiszEdytowaneZamowienie listy Zamowieñ przygotowanych 
        //przez u¿ytkownika 
        for (NoweZamowienieController.ProduktZamowienia produktZamowienia : listaProduktowZamowienia) {
            session.beginTransaction();
            //Przygotowanie obiektu Sali repreztowanej w bazie danych
            Sala sala = new Sala();
            //Ustawienie informacji o sali
            sala.setSala_dla("1");
            //Pobranie nr sali przypisanej do zamówionego produktu
            sala.setNumer_sali(produktZamowienia.getNrSali());
            //Przygotowanie obiektu Reprezentuj¹cego Produkt w bazie Danych
            Produkt produkt = new Produkt();
            produkt.setCena(new BigDecimal(0));
            //Pobranie nazwy produktu zapiszEdytowaneZamowienie listy lisyt przedmiotow zamówienia
            produkt.setNazwa(produktZamowienia.getNazwa());
            //Zapisanie produktu w bazie dancyh

            session.save(produkt);
            //Uzupe³nienie danych nowego zamówienia
            noweZamowienie.setData_zamowienia(new Date());
            noweZamowienie.setGodzina(new Time(3));
            noweZamowienie.setPielegniarka(pielegniarka);
            noweZamowienie.setSalaNr_sali(sala);
            //Utworzenie obiektu nowej pozycji zamowienia
            Produkt_Zamowienia nowaPozycjaZamowienia = new Produkt_Zamowienia();
            //Ustawienie produktu dla nowej pozycji zamowienia
            nowaPozycjaZamowienia.setProdukt(produkt);
            //przypisanie zamowienia do pozycji zamowienia
            nowaPozycjaZamowienia.setZamowienia(noweZamowienie);
            //Podanie ilosci sztuk dla pozycji zamowenia
            nowaPozycjaZamowienia.setIlosc(produktZamowienia.getIlosc());
            //dodanie pozycji zamowienia do zbioru pozycji Zamowienia
            zbiorPozycjiZamowienia.add(nowaPozycjaZamowienia);
        }
        //przypisanie wszystkich produktow  do zamówienia
        noweZamowienie.setProdukt_Zamowienia(zbiorPozycjiZamowienia);
        //Utworzenie nowego zamowienia w bazie danych
        session.save(noweZamowienie);
        session.getTransaction().commit();
        //Zamkniecie po³¹czenia zapiszEdytowaneZamowienie baz¹ danych
        session.close();
        return "Zapis do bazy zakonczony poweodzeniem";

    }

    public List<Zamowienia> pobierzZamowienia() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Zamowienia";
        Query query = session.createQuery(hql);

        List<Zamowienia> pobranaListaZamowienZbazyDanych = query.list();
        session.close();
        return pobranaListaZamowienZbazyDanych;
    }

    public List<Produkt_Zamowienia> pobierzProduktZamowienia(Integer idZamowienia) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query listaProduktowZamowienia = session.createQuery("from Produkt_Zamowienia where ZamowieniaID=" + idZamowienia + "order by ProduktID");
        List<Produkt_Zamowienia> listaProduktowZamowieniawedlugId = listaProduktowZamowienia.list();
        session.close();
        return listaProduktowZamowieniawedlugId;
    }

    public String usunZaznaczoneZamowienie(List<Produkt_Zamowienia> listaProduktowZamowieniawedlugId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Object usuwaneZamowienie = null;
        Object produktDoUsuniecia = null;
        for (Produkt_Zamowienia produktZamowieniaDoUsuniecia : listaProduktowZamowieniawedlugId) {
            produktDoUsuniecia = session.load(Produkt.class, produktZamowieniaDoUsuniecia.getProdukt().getID());
            usuwaneZamowienie = session.load(Zamowienia.class, produktZamowieniaDoUsuniecia.getZamowienia().getID());
            session.getTransaction().begin();
            session.delete(produktZamowieniaDoUsuniecia);
            session.delete(produktDoUsuniecia);
            session.getTransaction().commit();

        }
        session.getTransaction().begin();
        session.delete(usuwaneZamowienie);
        session.getTransaction().commit();
        session.close();
        return "Usuwanie zakoñczone powodzeniem";
    }

    public String zapiszEdytowaneZamowienie(ObservableList<ZatwierdzenieZamowieniaController.ZatwierdzenieZamowienia> listaProduktowDoWyswietlenia) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        for (ZatwierdzenieZamowieniaController.ZatwierdzenieZamowienia aktualiyowantProdukt : listaProduktowDoWyswietlenia) {
            Produkt produkt = new Produkt();
            Produkt_Zamowienia produktZamowienia = new Produkt_Zamowienia();
            Zamowienia zamowienie = new Zamowienia();

            produkt.setCena(aktualiyowantProdukt.getCenaProduktu());
            produkt.setID(aktualiyowantProdukt.getIdProduktu());
            produkt.setNazwa(aktualiyowantProdukt.getNazwaProduktu());

            zamowienie.setID(zamowieniAdministratora.getIdZamowienia());

            produktZamowienia.setZamowienia(zamowienie);
            produktZamowienia.setIlosc(aktualiyowantProdukt.getIloscProduktu());
            produktZamowienia.setProdukt(produkt);

            session.beginTransaction().begin();
            session.saveOrUpdate(produkt);
            session.saveOrUpdate(produktZamowienia);
            session.beginTransaction().commit();

        }
        session.close();
        return "Zapis zakoñczony poweodzeniem";
    }

    public String sprawdzStatusZamowienia() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Produkt_Zamowienia,Produkt";
        Query query = session.createQuery(hql);
        
        
        List<Zamowienia> pobranaListaZamowienZbazyDanych = query.list();
        
        session.close();
        return "Zrealizowano";
    }

}
