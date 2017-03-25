package model.repository;

import java.util.List;

import model.Leki;

public interface LekiRepository {

	public List<Leki> pobierzListeLekow();

	public boolean dodajLek(Leki lek);

	public boolean modyfikujLek(Leki lek);

	public void usunLeki(List<Leki> listaLekow);

}
