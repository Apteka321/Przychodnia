/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import java.util.List;

/**
 *
 * @author Daniel
 */
public interface Raport {
    
   public List pobierzDaneDoRaportu(String rok);  
   public List pobierzLiczbeWizytWedlugMiesiaca(String rok);
   public List poberzLiczbeZamowienWedlugMiesiaca(String rok);
   public double[] pobierzMiesiaczneKoszty();
   public double[] pobierzKosztyZamowien();
   public int[] listaWizytPacjentow() ;
   public int[] listaZamowien();

}