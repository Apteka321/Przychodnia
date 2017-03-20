package model;

public class Sala {
	public Sala() {
	}
	
	private int numer_sali;
	
	private String sala_dla;
	
	private java.util.Set zamowienia = new java.util.HashSet();
	
	private java.util.Set plan_dzienny = new java.util.HashSet();
	
	public void setNumer_sali(int value) {
		this.numer_sali = value;
	}
	
	public int getNumer_sali() {
		return numer_sali;
	}
	
	public int getORMID() {
		return getNumer_sali();
	}
	
	public void setSala_dla(String value) {
		this.sala_dla = value;
	}
	
	public String getSala_dla() {
		return sala_dla;
	}
	
	public void setZamowienia(java.util.Set value) {
		this.zamowienia = value;
	}
	
	public java.util.Set getZamowienia() {
		return zamowienia;
	}
	
	
	public void setPlan_dzienny(java.util.Set value) {
		this.plan_dzienny = value;
	}
	
	public java.util.Set getPlan_dzienny() {
		return plan_dzienny;
	}
	
	
	public String toString() {
		return String.valueOf(getNumer_sali());
	}
	
}
