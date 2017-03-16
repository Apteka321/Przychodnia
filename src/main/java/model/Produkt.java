package model;
public class Produkt {
	public Produkt() {
	}
	
	private int ID;
	
	private String nazwa;
	
	private java.math.BigDecimal cena;
	
	private int ilosc;
	
	private java.util.Set zamowienia = new java.util.HashSet();
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setNazwa(String value) {
		this.nazwa = value;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	
	public void setCena(java.math.BigDecimal value) {
		this.cena = value;
	}
	
	public java.math.BigDecimal getCena() {
		return cena;
	}
	
	public void setIlosc(int value) {
		this.ilosc = value;
	}
	
	public int getIlosc() {
		return ilosc;
	}
	
	public void setZamowienia(java.util.Set value) {
		this.zamowienia = value;
	}
	
	public java.util.Set getZamowienia() {
		return zamowienia;
	}
	
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
