package model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Produkt {
	public Produkt() {
	}

	private int ID;

	private int nazwa;

	private BigDecimal cena;

	private int ilosc;

	private Set zamowienia = new HashSet();

	public void setID(int value) {
		this.ID = value;
	}

	public int getID() {
		return ID;
	}

	public int getORMID() {
		return getID();
	}

	public void setNazwa(int value) {
		this.nazwa = value;
	}

	public int getNazwa() {
		return nazwa;
	}

	public void setCena(BigDecimal value) {
		this.cena = value;
	}

	public BigDecimal getCena() {
		return cena;
	}

	public void setIlosc(int value) {
		this.ilosc = value;
	}

	public int getIlosc() {
		return ilosc;
	}

	public void setZamowienia(Set value) {
		this.zamowienia = value;
	}

	public Set getZamowienia() {
		return zamowienia;
	}

	public String toString() {
		return String.valueOf(getID());
	}

}
