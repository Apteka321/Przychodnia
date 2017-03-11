package model;

public class Wolne_od_pracy {
	public Wolne_od_pracy() {
	}
	
	private int ID_wolnego;
	
	private Plan_pracy plan_pracy;
	
	private String powod;
	
	private java.util.Date od_dnia;
	
	private java.sql.Time od_godziny;
	
	private java.util.Date do_dnia;
	
	private java.sql.Time do_godziny;
	
	public void setPowod(String value) {
		this.powod = value;
	}
	
	public String getPowod() {
		return powod;
	}
	
	public void setOd_dnia(java.util.Date value) {
		this.od_dnia = value;
	}
	
	public java.util.Date getOd_dnia() {
		return od_dnia;
	}
	
	public void setOd_godziny(java.sql.Time value) {
		this.od_godziny = value;
	}
	
	public java.sql.Time getOd_godziny() {
		return od_godziny;
	}
	
	public void setDo_dnia(java.util.Date value) {
		this.do_dnia = value;
	}
	
	public java.util.Date getDo_dnia() {
		return do_dnia;
	}
	
	public void setDo_godziny(java.sql.Time value) {
		this.do_godziny = value;
	}
	
	public java.sql.Time getDo_godziny() {
		return do_godziny;
	}
	
	public void setID_wolnego(int value) {
		this.ID_wolnego = value;
	}
	
	public int getID_wolnego() {
		return ID_wolnego;
	}
	
	public int getORMID() {
		return getID_wolnego();
	}
	
	public void setPlan_pracy(Plan_pracy value) {
		this.plan_pracy = value;
	}
	
	public Plan_pracy getPlan_pracy() {
		return plan_pracy;
	}
	
	public String toString() {
		return String.valueOf(getID_wolnego());
	}
	
}
