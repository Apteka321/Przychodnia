/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository.impl;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.repository.WalidacjaFormularza;

/**
 *
 * @author Daniel
 */
public class WalidacjaFormularzaImpl implements WalidacjaFormularza {

    public boolean sprawdzCzyPoleFomrularzaPuste(TextField poleFormularza) {
        if (poleFormularza.getText() != null && !poleFormularza.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean sprawdzCzyPoleFomrularzaPuste(TextField poleFormularza, Label etykieta, String komunikat) {
        boolean czyPolePuste = true;
        String komunikatBledu = null;

        if (!sprawdzCzyPoleFomrularzaPuste(poleFormularza)) {
            czyPolePuste = false;
            komunikatBledu = komunikat;
        }
        etykieta.setText(komunikatBledu);
        return czyPolePuste;
    }

}
