package model;

public class Zwolnienie {
	public Zwolnienie() {
	}

	private int ID;

	private Wizyta wizyta;

	private java.util.Date zwolnienie_do;

	private int powod_zwolnienia;

	public void setID(int value) {
		this.ID = value;
	}

	public int getID() {
		return ID;
	}

	public int getORMID() {
		return getID();
	}

	public void setZwolnienie_do(java.util.Date value) {
		this.zwolnienie_do = value;
	}

	public java.util.Date getZwolnienie_do() {
		return zwolnienie_do;
	}

	public void setPowod_zwolnienia(int value) {
		this.powod_zwolnienia = value;
	}

	public int getPowod_zwolnienia() {
		return powod_zwolnienia;
	}

	public void setWizyta(Wizyta value) {
		this.wizyta = value;
	}

	public Wizyta getWizyta() {
		return wizyta;
	}

	public String toString() {
		return String.valueOf(getID());
	}

}
