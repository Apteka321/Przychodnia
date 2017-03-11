package model;

public class Lista_zabiegow {
	public Lista_zabiegow() {
	}
	
	private int ID;
	
	private String nazwa;
	
	private java.math.BigDecimal cena;
	
	private java.util.Set zabieg = new java.util.HashSet();
	
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
	
	public void setZabieg(java.util.Set value) {
		this.zabieg = value;
	}
	
	public java.util.Set getZabieg() {
		return zabieg;
	}
	
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
