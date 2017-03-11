package model;

public class Skierowanie {
	public Skierowanie() {
	}
	
	private int ID;
	
	private Wizyta ID_wizyty;
	
	private String skierowanie_do;
	
	private String rozpoznanie;
	
	private String skierowanie_na;
	
	private Zabieg zabieg;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setSkierowanie_do(String value) {
		this.skierowanie_do = value;
	}
	
	public String getSkierowanie_do() {
		return skierowanie_do;
	}
	
	public void setRozpoznanie(String value) {
		this.rozpoznanie = value;
	}
	
	public String getRozpoznanie() {
		return rozpoznanie;
	}
	
	public void setSkierowanie_na(String value) {
		this.skierowanie_na = value;
	}
	
	public String getSkierowanie_na() {
		return skierowanie_na;
	}
	
	public void setID_wizyty(Wizyta value) {
		this.ID_wizyty = value;
	}
	
	public Wizyta getID_wizyty() {
		return ID_wizyty;
	}
	
	public void setZabieg(Zabieg value) {
		this.zabieg = value;
	}
	
	public Zabieg getZabieg() {
		return zabieg;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
