package model;
public class Leki_Recepta {
	public Leki_Recepta() {
	}
	
	private int ID;
	
	private Leki leki;
	
	private Recepta recepta;
	
	private String dawkowanie;
	
	private int procent_refundacji;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setDawkowanie(String value) {
		this.dawkowanie = value;
	}
	
	public String getDawkowanie() {
		return dawkowanie;
	}
	
	public void setProcent_refundacji(int value) {
		this.procent_refundacji = value;
	}
	
	public int getProcent_refundacji() {
		return procent_refundacji;
	}
	
	public void setLeki(Leki value) {
		this.leki = value;
	}
	
	public Leki getLeki() {
		return leki;
	}
	
	public void setRecepta(Recepta value) {
		this.recepta = value;
	}
	
	public Recepta getRecepta() {
		return recepta;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
