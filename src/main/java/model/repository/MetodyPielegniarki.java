package model.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import Controller.PlanowanieZabiegowController.wybranyZabieg;

public interface MetodyPielegniarki {
	public void dodajPielegniarke(String idSkierowania,List<wybranyZabieg> idZabiegu, LocalDate dataWykonania,String uwagi);

}
