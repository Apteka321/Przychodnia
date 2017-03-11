package model;

public class Lekarz {
	public Lekarz() {
	}
	
	private int ID;
	
	private Osoba osoba;
	
	private Konto konto;
	
	private Plan_pracy plan_pracy;
	
	private java.util.Set ID_Specjalizacji = new java.util.HashSet();
	
	private java.util.Set wizyta = new java.util.HashSet();
	
	private java.util.Set specjalizacja = new java.util.HashSet();
	
	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNumer_telefonu() {
		return numer_telefonu;
	}

	public void setNumer_telefonu(String numer_telefonu) {
		this.numer_telefonu = numer_telefonu;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public Integer getCena_za_godzienie() {
		return cena_za_godzienie;
	}

	public void setCena_za_godzienie(Integer cena_za_godzienie) {
		this.cena_za_godzienie = cena_za_godzienie;
	}

	public String getKontoLogin() {
		return kontoLogin;
	}

	public void setKontoLogin(String kontoLogin) {
		this.kontoLogin = kontoLogin;
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
	
	public void setOsoba(Osoba value) {
		this.osoba = value;
	}
	
	public Osoba getOsoba() {
		return osoba;
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
	
	public void setID_Specjalizacji(java.util.Set value) {
		this.ID_Specjalizacji = value;
	}
	
	public java.util.Set getID_Specjalizacji() {
		return ID_Specjalizacji;
	}
	
	
	public void setWizyta(java.util.Set value) {
		this.wizyta = value;
	}
	
	public java.util.Set getWizyta() {
		return wizyta;
	}
	
	
	public void setSpecjalizacja(java.util.Set value) {
		this.specjalizacja = value;
	}
	
	public java.util.Set getSpecjalizacja() {
		return specjalizacja;
	}
	
	
	private String imie;
	
	private String nazwisko;
	
	private String numer_telefonu;
	
	private String login;
	
	private String haslo;
	
	private Integer cena_za_godzienie;
	
	private String kontoLogin;
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
