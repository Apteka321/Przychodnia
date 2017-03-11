package model;

public class Platnosc {
	public Platnosc() {
	}
	
	private int ID;
	
	private java.math.BigDecimal kwota;
	
	private java.util.Date data_platnosci;
	
	private String tytul_platnosci;
	
	private Wizyta ID_wizyty;
	
	private Recepcjonistka recepcjonistka;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setKwota(java.math.BigDecimal value) {
		this.kwota = value;
	}
	
	public java.math.BigDecimal getKwota() {
		return kwota;
	}
	
	public void setData_platnosci(java.util.Date value) {
		this.data_platnosci = value;
	}
	
	public java.util.Date getData_platnosci() {
		return data_platnosci;
	}
	
	public void setTytul_platnosci(String value) {
		this.tytul_platnosci = value;
	}
	
	public String getTytul_platnosci() {
		return tytul_platnosci;
	}
	
	public void setID_wizyty(Wizyta value) {
		this.ID_wizyty = value;
	}
	
	public Wizyta getID_wizyty() {
		return ID_wizyty;
	}
	
	public void setRecepcjonistka(Recepcjonistka value) {
		this.recepcjonistka = value;
	}
	
	public Recepcjonistka getRecepcjonistka() {
		return recepcjonistka;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
