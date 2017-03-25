package model;

public interface Pracownik {
	public void setKonto(Konto value);

	public Konto getKonto();

	public void setOsoba(Osoba value);

	public Osoba getOsoba();

	public void setPlan_pracy(Plan_pracy value);

	public Plan_pracy getPlan_pracy();

	public void setID(int ID);

	public int getID();

}
