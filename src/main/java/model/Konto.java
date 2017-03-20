package model;
public class Konto {
	public Konto() {
	}
	
	private int ID;
	
	private String login;
	
	private String haslo;
	
	private int typ_konta;
	
	private Lekarz lekarz;
	
	private Recepcjonistka recepcjonistka;
	
	private Pacjent pacjent;
	
	private Pielegniarka pielegniarka;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setLogin(String value) {
		this.login = value;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setHaslo(String value) {
		this.haslo = value;
	}
	
	public String getHaslo() {
		return haslo;
	}
	
	public void setTyp_konta(int value) {
		this.typ_konta = value;
	}
	
	public int getTyp_konta() {
		return typ_konta;
	}
	
	public void setLekarz(Lekarz value) {
		this.lekarz = value;
	}
	
	public Lekarz getLekarz() {
		return lekarz;
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
	
	public void setPielegniarka(Pielegniarka value) {
		this.pielegniarka = value;
	}
	
	public Pielegniarka getPielegniarka() {
		return pielegniarka;
	}
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
