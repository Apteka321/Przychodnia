package model;
public class Zamowienia {
	public Zamowienia() {
	}
	
	private int ID;
	
	private java.util.Date data_zamowienia;
	
	private java.sql.Time godzina;
	
	private Pielegniarka pielegniarka;
	
	private Sala salaNr_sali;
	
	private java.util.Set produkt = new java.util.HashSet();
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setData_zamowienia(java.util.Date value) {
		this.data_zamowienia = value;
	}
	
	public java.util.Date getData_zamowienia() {
		return data_zamowienia;
	}
	
	public void setGodzina(java.sql.Time value) {
		this.godzina = value;
	}
	
	public java.sql.Time getGodzina() {
		return godzina;
	}
	
	public void setProdukt(java.util.Set value) {
		this.produkt = value;
	}
	
	public java.util.Set getProdukt() {
		return produkt;
	}
	
	
	public void setSalaNr_sali(Sala value) {
		this.salaNr_sali = value;
	}
	
	public Sala getSalaNr_sali() {
		return salaNr_sali;
	}
	
	public void setPielegniarka(Pielegniarka value) {
		this.pielegniarka = value;
	}
	
	public Pielegniarka getPielegniarka() {
		return pielegniarka;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
