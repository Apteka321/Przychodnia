package model;

public class Plan_pracy {
	public Plan_pracy() {
	}
	
	private int ID;
	
	private Plan_dzienny plan_dzienny;
	
	private java.util.Date umowa_od;
	
	private java.math.BigDecimal wyplata;
	
	private java.util.Set wolne_od_pracy = new java.util.HashSet();
	
	private Lekarz lekarz;
	
	private Pielegniarka pielegniarka;
	
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
	
	public void setUmowa_od(java.util.Date value) {
		this.umowa_od = value;
	}
	
	public java.util.Date getUmowa_od() {
		return umowa_od;
	}
	
	public void setWyplata(java.math.BigDecimal value) {
		this.wyplata = value;
	}
	
	public java.math.BigDecimal getWyplata() {
		return wyplata;
	}
	
	public void setPlan_dzienny(Plan_dzienny value) {
		this.plan_dzienny = value;
	}
	
	public Plan_dzienny getPlan_dzienny() {
		return plan_dzienny;
	}
	
	public void setWolne_od_pracy(java.util.Set value) {
		this.wolne_od_pracy = value;
	}
	
	public java.util.Set getWolne_od_pracy() {
		return wolne_od_pracy;
	}
	
	
	public void setLekarz(Lekarz value) {
		this.lekarz = value;
	}
	
	public Lekarz getLekarz() {
		return lekarz;
	}
	
	public void setPielegniarka(Pielegniarka value) {
		this.pielegniarka = value;
	}
	
	public Pielegniarka getPielegniarka() {
		return pielegniarka;
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
