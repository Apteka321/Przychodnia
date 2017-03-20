package model;

public class Pielegniarka implements Pracownik {
	public Pielegniarka() {
	}

	private int ID;

	private Osoba adres;

	private Konto konto;

	private Plan_pracy plan_pracy;

	private java.util.Set zabieg = new java.util.HashSet();

	private java.util.Set zamowienia = new java.util.HashSet();

	public void setID(int value) {
		this.ID = value;
	}

	public int getID() {
		return ID;
	}

	public int getORMID() {
		return getID();
	}

	public void setAdres(Osoba value) {
		this.adres = value;
	}

	public Osoba getAdres() {
		return adres;
	}

	public void setKonto(Konto value) {
		this.konto = value;
	}

	public Konto getKonto() {
		return konto;
	}

	public void setPlan_pracy(Plan_pracy value) {
		this.plan_pracy = value;
	}

	public Plan_pracy getPlan_pracy() {
		return plan_pracy;
	}

	public void setZabieg(java.util.Set value) {
		this.zabieg = value;
	}

	public java.util.Set getZabieg() {
		return zabieg;
	}

	public void setZamowienia(java.util.Set value) {
		this.zamowienia = value;
	}

	public java.util.Set getZamowienia() {
		return zamowienia;
	}

	public String toString() {
		return String.valueOf(getID());
	}

	@Override
	public void setOsoba(Osoba value) {
		this.setAdres(value);

	}

	@Override
	public Osoba getOsoba() {
		return this.getAdres();
	}

}
