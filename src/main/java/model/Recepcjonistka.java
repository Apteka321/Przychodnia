package model;

public class Recepcjonistka implements Pracownik {
	public Recepcjonistka() {
	}

	private int id;

	private Konto konto;

	private Osoba osoba;

	private Plan_pracy plan_pracy;

	private java.util.Set wizyta = new java.util.HashSet();

	private java.util.Set platnosc = new java.util.HashSet();

	public void setId(int value) {
		this.id = value;
	}

	public int getId() {
		return id;
	}

	public int getORMID() {
		return getId();
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

	public void setOsoba(Osoba value) {
		this.osoba = value;
	}

	public Osoba getOsoba() {
		return osoba;
	}

	public void setWizyta(java.util.Set value) {
		this.wizyta = value;
	}

	public java.util.Set getWizyta() {
		return wizyta;
	}

	public void setPlatnosc(java.util.Set value) {
		this.platnosc = value;
	}

	public java.util.Set getPlatnosc() {
		return platnosc;
	}

	public String toString() {
		return String.valueOf(getId());
	}

	@Override
	public void setID(int ID) {
		this.setId(ID);

	}

	@Override
	public int getID() {
		return this.getId();
	}

}
