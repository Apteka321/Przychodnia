package model;

import java.io.Serializable;

public class Produkt_Zamowienia implements Serializable {
	public Produkt_Zamowienia() {
	}

	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Produkt_Zamowienia))
			return false;
		Produkt_Zamowienia produkt_zamowienia = (Produkt_Zamowienia) aObj;
		if (getProdukt() == null) {
			if (produkt_zamowienia.getProdukt() != null)
				return false;
		} else if (!getProdukt().equals(produkt_zamowienia.getProdukt()))
			return false;
		if (getZamowienia() == null) {
			if (produkt_zamowienia.getZamowienia() != null)
				return false;
		} else if (!getZamowienia().equals(produkt_zamowienia.getZamowienia()))
			return false;
		return true;
	}

	public int hashCode() {
		int hashcode = 0;
		if (getProdukt() != null) {
			hashcode = hashcode + (int) getProdukt().getORMID();
		}
		if (getZamowienia() != null) {
			hashcode = hashcode + (int) getZamowienia().getORMID();
		}
		return hashcode;
	}

	private Produkt produkt;

	private int produktId;

	public void setProduktId(int value) {
		this.produktId = value;
	}

	public int getProduktId() {
		return produktId;
	}

	private Zamowienia zamowienia;

	private int zamowieniaId;

	public void setZamowieniaId(int value) {
		this.zamowieniaId = value;
	}

	public int getZamowieniaId() {
		return zamowieniaId;
	}

	private Integer ilosc;

	public void setIlosc(int value) {
		setIlosc(new Integer(value));
	}

	public void setIlosc(Integer value) {
		this.ilosc = value;
	}

	public Integer getIlosc() {
		return ilosc;
	}

	public void setProdukt(Produkt value) {
		this.produkt = value;
	}

	public Produkt getProdukt() {
		return produkt;
	}

	public void setZamowienia(Zamowienia value) {
		this.zamowienia = value;
	}

	public Zamowienia getZamowienia() {
		return zamowienia;
	}

	public String toString() {
		return String.valueOf(((getProdukt() == null) ? "" : String.valueOf(getProdukt().getORMID())) + " "
				+ ((getZamowienia() == null) ? "" : String.valueOf(getZamowienia().getORMID())));
	}

	private boolean _saved = false;

	public void onSave() {
		_saved = true;
	}

	public void onLoad() {
		_saved = true;
	}

	public boolean isSaved() {
		return _saved;
	}

}
