package model;

public class Plan_dzienny {
	public Plan_dzienny() {
	}
	
	private int ID;
	
	private int dzien;
	
	private java.sql.Time poczatek;
	
	private java.sql.Time koniec;
	
	private java.sql.Time przerwa_od;
	
	private java.sql.Time przerwa_do;
	
	private Sala salaNr_sali;
	
	private Plan_pracy plan_pracy;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setDzien(int value) {
		this.dzien = value;
	}
	
	public int getDzien() {
		return dzien;
	}
	
	public void setPoczatek(java.sql.Time value) {
		this.poczatek = value;
	}
	
	public java.sql.Time getPoczatek() {
		return poczatek;
	}
	
	public void setKoniec(java.sql.Time value) {
		this.koniec = value;
	}
	
	public java.sql.Time getKoniec() {
		return koniec;
	}
	
	public void setPrzerwa_od(java.sql.Time value) {
		this.przerwa_od = value;
	}
	
	public java.sql.Time getPrzerwa_od() {
		return przerwa_od;
	}
	
	public void setPrzerwa_do(java.sql.Time value) {
		this.przerwa_do = value;
	}
	
	public java.sql.Time getPrzerwa_do() {
		return przerwa_do;
	}
	
	public void setSalaNr_sali(Sala value) {
		this.salaNr_sali = value;
	}
	
	public Sala getSalaNr_sali() {
		return salaNr_sali;
	}
	
	public void setPlan_pracy(Plan_pracy value) {
		this.plan_pracy = value;
	}
	
	public Plan_pracy getPlan_pracy() {
		return plan_pracy;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
