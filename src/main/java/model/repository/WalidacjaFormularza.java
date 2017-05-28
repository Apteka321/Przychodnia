/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Daniel
 */
public interface WalidacjaFormularza {

    public boolean sprawdzCzyPoleFomrularzaPuste(TextField poleFormularza);
    public boolean sprawdzCzyPoleFomrularzaPuste(TextField poleFormularza, Label etykieta, String komunikat);

}
