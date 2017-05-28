/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import java.util.List;
import model.Osoba;
import model.Wizyta;

/**
 *
 * @author Daniel
 */
public interface Gabinet {

    public Osoba pobirzPacjentaPoNumerzePesel(String PESEL);
    public List<Wizyta> pobierzHistorieWizytPacjenta(String PESEL);
    public void zapiszWizyte(Wizyta wizytaDoZapisania);
}
