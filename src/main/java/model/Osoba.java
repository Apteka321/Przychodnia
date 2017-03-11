package model;

public class Osoba {
	public Osoba() {
	}
	
	private int ID;
	
	private String imie;
	
	private int nazwisko;
	
	private String miejscowosc;
	
	private int numer_domu;
	
	private String ulica;
	
	private String kod_pocztowy;
	
	private String poczta;
	
	private String numer_telefonu;
	
	private Lekarz lekarz;
	
	private Pielegniarka pielegniarka;
	
	private Recepcjonistka recepcjonistka;
	
	private Pacjent pacjent;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setImie(String value) {
		this.imie = value;
	}
	
	public String getImie() {
		return imie;
	}
	
	public void setNazwisko(int value) {
		this.nazwisko = value;
	}
	
	public int getNazwisko() {
		return nazwisko;
	}
	
	public void setMiejscowosc(String value) {
		this.miejscowosc = value;
	}
	
	public String getMiejscowosc() {
		return miejscowosc;
	}
	
	public void setNumer_domu(int value) {
		this.numer_domu = value;
	}
	
	public int getNumer_domu() {
		return numer_domu;
	}
	
	public void setUlica(String value) {
		this.ulica = value;
	}
	
	public String getUlica() {
		return ulica;
	}
	
	public void setKod_pocztowy(String value) {
		this.kod_pocztowy = value;
	}
	
	public String getKod_pocztowy() {
		return kod_pocztowy;
	}
	
	public void setPoczta(String value) {
		this.poczta = value;
	}
	
	public String getPoczta() {
		return poczta;
	}
	
	public void setNumer_telefonu(String value) {
		this.numer_telefonu = value;
	}
	
	public String getNumer_telefonu() {
		return numer_telefonu;
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
	
	public void setPacjent(Pacjent value) {
		this.pacjent = value;
	}
	
	public Pacjent getPacjent() {
		return pacjent;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
