package model;

public class Produkt {
	public Produkt() {
	}
	
	private int ID;
	
	private String nazwa;
	
	private java.math.BigDecimal cena;
	
	private java.util.Set produkt_Zamowienia = new java.util.HashSet();
	
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
	
	public void setProdukt_Zamowienia(java.util.Set value) {
		this.produkt_Zamowienia = value;
	}
	
	public java.util.Set getProdukt_Zamowienia() {
		return produkt_Zamowienia;
	}
	
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
