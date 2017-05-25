/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository.impl;

import static Controller.WizytyController.listaPacjentowDoSpecjalisty;
import java.util.Date;



import java.util.List;
import model.HibernateUtil;
import model.Wizyta;
import model.repository.Wizyty;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Daniel
 */
public class WizytyImpl implements Wizyty{

    public List<Wizyta> pobierzListeWizytWedugDaty(Date dataWizyty) {
        Session session = HibernateUtil.getSessionFactory().openSession();
//        Date date2 = new Date(Calendar.getInstance().getTime().getTime());
//        System.out.println("data z repo"+date2);
        Date date = new Date(dataWizyty.getTime());        
        Query query = session.createQuery("From Wizyta where data = :zmienna");
        query.setParameter("zmienna", date);

        listaPacjentowDoSpecjalisty = query.list();
        session.close();
        return listaPacjentowDoSpecjalisty;
    }

    public Wizyta pobierzWizytePoId(int idWizyty) {
        Session session = HibernateUtil.getSessionFactory().openSession();        
        Wizyta wizyta = session.get(Wizyta.class,idWizyty);        
        session.close();
        return wizyta;
    }
    
}
