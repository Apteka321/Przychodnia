package model;
public class Zabieg {
	public Zabieg() {
	}
	
	private int ID;
	
	private Lista_zabiegow lista_zabiegow;
	
	private Pielegniarka pielegniarka;
	
	private Skierowanie skierowanie;
	
	private java.util.Date data_wykonania;
	
	private String uwagi;
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setData_wykonania(java.util.Date value) {
		this.data_wykonania = value;
	}
	
	public java.util.Date getData_wykonania() {
		return data_wykonania;
	}
	
	public void setUwagi(String value) {
		this.uwagi = value;
	}
	
	public String getUwagi() {
		return uwagi;
	}
	
	public void setLista_zabiegow(Lista_zabiegow value) {
		this.lista_zabiegow = value;
	}
	
	public Lista_zabiegow getLista_zabiegow() {
		return lista_zabiegow;
	}
	
	public void setSkierowanie(Skierowanie value) {
		this.skierowanie = value;
	}
	
	public Skierowanie getSkierowanie() {
		return skierowanie;
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
