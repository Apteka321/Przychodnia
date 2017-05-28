/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.*;
import static Controller.GabinetController.idHistoriWizytyPacjenta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Wizyta;
import model.repository.impl.WizytyImpl;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class HistoriaWizytyController implements Initializable {

    WizytyImpl metodyWizyt = new WizytyImpl();

    @FXML
    private Label imieLabel;
    @FXML
    private Label nazwiskoLabel;
    @FXML
    private Label dataWizytyLabel;
    @FXML
    private Label peselLabel;
    @FXML
    private TextArea objawyArea;
    @FXML
    private TextArea wywiadArea;
    @FXML
    private Label imieLekarzaLabel;
    @FXML
    private Label specjalizacjaLekarzaLabel;
    @FXML
    private Label nazwiskoLekarzaLabel;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        Wizyta wizyta = metodyWizyt.pobierzWizytePoId(idHistoriWizytyPacjenta);

        imieLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getImie());
        nazwiskoLabel.setText(wizyta.getPESEL_pacjenta().getOsoba().getNazwisko());
        peselLabel.setText(wizyta.getPESEL_pacjenta().getPESEL());
        dataWizytyLabel.setText(wizyta.getData().toString());
        wywiadArea.setText(wizyta.getObjawy());
        objawyArea.setText(wizyta.getDiagnoza());
        imieLekarzaLabel.setText(wizyta.getID_lekarza().getOsoba().getImie());
        nazwiskoLekarzaLabel.setText(wizyta.getID_lekarza().getOsoba().getNazwisko());
        specjalizacjaLekarzaLabel.setText(wizyta.getID_Specjalizacji().getNazwa());

    }

}
