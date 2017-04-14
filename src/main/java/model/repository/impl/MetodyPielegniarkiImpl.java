package model.repository.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import Controller.PlanowanieZabiegowController.wybranyZabieg;
import View.Komunikaty;
import model.HibernateUtil;
import model.Lista_zabiegow;
import model.Pielegniarka;
import model.Skierowanie;
import model.Zabieg;
import model.repository.MetodyPielegniarki;

public class MetodyPielegniarkiImpl implements MetodyPielegniarki {

	public void dodajPielegniarke(String idSkierowania, List<wybranyZabieg> idZabiegu, LocalDate dataWykonania,
			String uwagi) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String idPielegniarki = "FROM Pielegniarka WHERE ID = " + 1;
			Query query3 = session.createQuery(idPielegniarki);
			Pielegniarka pielegniarka = (Pielegniarka) query3.list().get(0);
			
			String id = "FROM Skierowanie WHERE ID = " + idSkierowania;
			Query querySkierowanie = session.createQuery(id);
			Skierowanie skierowanie = (Skierowanie) querySkierowanie.list().get(0);
			
			Zabieg zabieg = new Zabieg();
			for (wybranyZabieg wZabieg : idZabiegu) {
				Query query = session.createQuery("from Lista_zabiegow where nazwa= :nazwaZabiegu");
				query.setParameter("nazwaZabiegu", wZabieg.getNazwa());
				Lista_zabiegow lista_zabiegow= (Lista_zabiegow) query.list().get(0);
				zabieg.setLista_zabiegow(lista_zabiegow);
				zabieg.setData_wykonania(asDate(dataWykonania));
				zabieg.setUwagi(uwagi);
				zabieg.setPielegniarka(pielegniarka);
				zabieg.setSkierowanie(skierowanie);
				session.save(zabieg);
				
			}
		
		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("Coœ nie dzia³a poprawnie","-Niew³aœciwy numer skierowania\n-Niew³aœciwnie uzupe³nione pola\n-I wiele innnych b³êdów\n chyba");
		}
		
		session.close();

	}
	public static java.util.Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
