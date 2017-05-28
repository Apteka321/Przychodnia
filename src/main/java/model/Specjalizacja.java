package model;
public class Specjalizacja {
	public Specjalizacja() {
	}
	
	private int ID;
	
	private String nazwa;
	
	private int czas_wizyty;
	
	private java.math.BigDecimal koszt_wizyty;
	
	private java.util.Set ID_lekarza = new java.util.HashSet();
	
	private java.util.Set lekarz = new java.util.HashSet();
	
	private java.util.Set wizyta = new java.util.HashSet();
	
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
	
	public void setCzas_wizyty(int value) {
		this.czas_wizyty = value;
	}
	
	public int getCzas_wizyty() {
		return czas_wizyty;
	}
	
	public void setKoszt_wizyty(java.math.BigDecimal value) {
		this.koszt_wizyty = value;
	}
	
	public java.math.BigDecimal getKoszt_wizyty() {
		return koszt_wizyty;
	}
	
	public void setID_lekarza(java.util.Set value) {
		this.ID_lekarza = value;
	}
	
	public java.util.Set getID_lekarza() {
		return ID_lekarza;
	}
	
	
	public void setLekarz(java.util.Set value) {
		this.lekarz = value;
	}
	
	public java.util.Set getLekarz() {
		return lekarz;
	}
	
	
	public void setWizyta(java.util.Set value) {
		this.wizyta = value;
	}
	
	public java.util.Set getWizyta() {
		return wizyta;
	}
	
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
