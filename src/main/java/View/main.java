package View;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sun.javafx.collections.MappingChange.Map;

import model.HibernateUtil;
import model.Konto;
import model.Lekarz;
import model.Osoba;
import model.Plan_dzienny;
import model.Plan_pracy;
import model.Pracownik;
import model.Specjalizacja;
import model.Sala;

public class main {

	public static void main(String[] args) {
		Pracownik prac = new Lekarz();
		System.out.println(prac.getClass().getSimpleName());
	}

}
