package model;
public class Leki {
	public Leki() {
	}
	
	private int ID;
	
	private String nazwa;
	
	private String producent;
	
	private String ilosc;
	
	private java.util.Set leki_Recepta = new java.util.HashSet();
	
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
	
	public void setProducent(String value) {
		this.producent = value;
	}
	
	public String getProducent() {
		return producent;
	}
	
	public void setIlosc(String value) {
		this.ilosc = value;
	}
	
	public String getIlosc() {
		return ilosc;
	}
	
	public void setLeki_Recepta(java.util.Set value) {
		this.leki_Recepta = value;
	}
	
	public java.util.Set getLeki_Recepta() {
		return leki_Recepta;
	}
	
	
	private int procent_refundacji;
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
