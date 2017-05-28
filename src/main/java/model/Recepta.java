package model;
public class Recepta {
	public Recepta() {
	}
	
	private int ID;
	
	private java.util.Date data_waznosci;
	
	private String dawkowanie;
	
	private Wizyta wizyta;
	
	private java.util.Set leki_Recepta = new java.util.HashSet();
	
	public void setData_waznosci(java.util.Date value) {
		this.data_waznosci = value;
	}
	
	public java.util.Date getData_waznosci() {
		return data_waznosci;
	}
	
	public void setDawkowanie(String value) {
		this.dawkowanie = value;
	}
	
	public String getDawkowanie() {
		return dawkowanie;
	}
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setWizyta(Wizyta value) {
		this.wizyta = value;
	}
	
	public Wizyta getWizyta() {
		return wizyta;
	}
	
	public void setLeki_Recepta(java.util.Set value) {
		this.leki_Recepta = value;
	}
	
	public java.util.Set getLeki_Recepta() {
		return leki_Recepta;
	}
	
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
