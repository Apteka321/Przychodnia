package model;

public class Wizyta {
	public Wizyta() {
	}
	
	private int ID;
	
	private java.util.Date data;
	
	private java.sql.Time godzina;
	
	private String objawy;
	
	private String diagnoza;
	
	private int czy_ubezpieczony;
	
	private String status_wizyty;
	
	private Specjalizacja ID_Specjalizacji;
	
	private Pacjent PESEL_pacjenta;
	
	private Lekarz ID_lekarza;
	
	private Recepcjonistka recepcjonistka;
	
	private java.util.Set skierowanie = new java.util.HashSet();
	
	private Platnosc platnosc;
	
	private Recepta recepta1;
	
	private java.util.Set zwolnienie = new java.util.HashSet();
	
	public String getKto_zarejestrowal() {
		return kto_zarejestrowal;
	}

	public void setKto_zarejestrowal(String kto_zarejestrowal) {
		this.kto_zarejestrowal = kto_zarejestrowal;
	}

	public java.math.BigDecimal getCena() {
		return cena;
	}

	public void setCena(java.math.BigDecimal cena) {
		this.cena = cena;
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
	
	public void setData(java.util.Date value) {
		this.data = value;
	}
	
	public java.util.Date getData() {
		return data;
	}
	
	public void setGodzina(java.sql.Time value) {
		this.godzina = value;
	}
	
	public java.sql.Time getGodzina() {
		return godzina;
	}
	
	public void setObjawy(String value) {
		this.objawy = value;
	}
	
	public String getObjawy() {
		return objawy;
	}
	
	public void setDiagnoza(String value) {
		this.diagnoza = value;
	}
	
	public String getDiagnoza() {
		return diagnoza;
	}
	
	public void setCzy_ubezpieczony(int value) {
		this.czy_ubezpieczony = value;
	}
	
	public int getCzy_ubezpieczony() {
		return czy_ubezpieczony;
	}
	
	public void setStatus_wizyty(String value) {
		this.status_wizyty = value;
	}
	
	public String getStatus_wizyty() {
		return status_wizyty;
	}
	
	public void setID_lekarza(Lekarz value) {
		this.ID_lekarza = value;
	}
	
	public Lekarz getID_lekarza() {
		return ID_lekarza;
	}
	
	public void setPESEL_pacjenta(Pacjent value) {
		this.PESEL_pacjenta = value;
	}
	
	public Pacjent getPESEL_pacjenta() {
		return PESEL_pacjenta;
	}
	
	public void setID_Specjalizacji(Specjalizacja value) {
		this.ID_Specjalizacji = value;
	}
	
	public Specjalizacja getID_Specjalizacji() {
		return ID_Specjalizacji;
	}
	
	public void setRecepcjonistka(Recepcjonistka value) {
		this.recepcjonistka = value;
	}
	
	public Recepcjonistka getRecepcjonistka() {
		return recepcjonistka;
	}
	
	public void setSkierowanie(java.util.Set value) {
		this.skierowanie = value;
	}
	
	public java.util.Set getSkierowanie() {
		return skierowanie;
	}
	
	
	public void setPlatnosc(Platnosc value) {
		this.platnosc = value;
	}
	
	public Platnosc getPlatnosc() {
		return platnosc;
	}
	
	public void setRecepta1(Recepta value) {
		this.recepta1 = value;
	}
	
	public Recepta getRecepta1() {
		return recepta1;
	}
	
	public void setZwolnienie(java.util.Set value) {
		this.zwolnienie = value;
	}
	
	public java.util.Set getZwolnienie() {
		return zwolnienie;
	}
	
	
	private String kto_zarejestrowal;
	
	private java.math.BigDecimal cena;
	
	public String toString() {
		return String.valueOf(getID());
	}
	
}
