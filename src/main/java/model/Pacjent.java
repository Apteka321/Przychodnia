package model;

public class Pacjent {
	public Pacjent() {
	}
	
	private String PESEL;
	
	private Konto konto;
	
	private Osoba osoba;
	
	private java.util.Set wizyta = new java.util.HashSet();
	
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

	public boolean is_saved() {
		return _saved;
	}

	public void set_saved(boolean _saved) {
		this._saved = _saved;
	}

	public void setPESEL(String value) {
		this.PESEL = value;
	}
	
	public String getPESEL() {
		return PESEL;
	}
	
	public String getORMID() {
		return getPESEL();
	}
	
	public void setKonto(Konto value) {
		this.konto = value;
	}
	
	public Konto getKonto() {
		return konto;
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
	
	
	private String imie;
	
	private String nazwisko;
	
	private String numer_telefonu;
	
	private String login;
	
	private String haslo;
	
	public String toString() {
		return String.valueOf(getPESEL());
	}
	
	private boolean _saved = false;
	
	public void onSave() {
		_saved=true;
	}
	
	
	public void onLoad() {
		_saved=true;
	}
	
	
	public boolean isSaved() {
		return _saved;
	}
	
	
}
