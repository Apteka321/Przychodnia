/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository.impl;

import java.util.ArrayList;
import java.util.List;
import model.HibernateUtil;
import model.Osoba;
import model.Wizyta;
import model.repository.Gabinet;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Daniel
 */
public class GabinetImpl implements Gabinet {

    public Osoba pobirzPacjentaPoNumerzePesel(String PESEL) {
        Osoba pacjentWizyty = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        String hql = "from Pacjent,Osoba Where Pacjent.PESEL=" + PESEL + "AND Pacjent.OsobaID =Osoba.ID;";
        Query query = session.createQuery(hql);
        pacjentWizyty = (Osoba) query.list();
        session.close();

        return pacjentWizyty;
    }

    public List<Wizyta> pobierzHistorieWizytPacjenta(String PESEL) {
        List<Wizyta> historaiaWizytPacjenta = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        String numerPESEL = PESEL;
        String hql = "from Wizyta WHERE PESEL_pacjenta="+numerPESEL;
        Query query = session.createQuery(hql);
        historaiaWizytPacjenta= query.list();
        session.close();

        return historaiaWizytPacjenta;
    }
    
    public void zapiszWizyte(Wizyta wizytaDoZapisania ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction().begin();
        session.saveOrUpdate(wizytaDoZapisania);
        session.beginTransaction().commit();
        session.close();
    }

}
