package Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import View.Komunikaty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Lekarz;
import model.Pacjent;
import model.Skierowanie;
import model.Wizyta;
import model.repository.WizytaRepository;
import model.repository.impl.WizytaRepositoryImpl;

public class WystawienieSkierowaniaController {
	// wybrana wizyta - WSTAWIÆ WIZYTE!!!
	public Wizyta wizyta = new WizytaRepositoryImpl().getListaWizyt().get(0);

	// zalogowany lekarz - NIE MODYFOKOWAÆ
	private Lekarz lekarz;
	// wybrany pacjent - NIE MODYFIKOWAÆ
	private Pacjent pacjentOsoba;

	@FXML
	void initialize() {
		lekarz = wizyta.getID_lekarza();
		pacjentOsoba = wizyta.getPESEL_pacjenta();
	}

	@FXML
	void drukujSkierowanie(ActionEvent event) {

		if (rozpoznanieTextArea.getText().length() == 0) {
			rozpoznanieTextArea.setText(" ");
		}
		Skierowanie skierowanie = new Skierowanie();
		skierowanie.setID_wizyty(wizyta);
		// wczytanie z formularza
		skierowanie.setRozpoznanie(rozpoznanieTextArea.getText());
		skierowanie.setSkierowanie_do(skierowanieDoTextField.getText());
		skierowanie.setSkierowanie_na(skierowanieNaTextField.getText());

		WizytaRepository wizytaRepository = new WizytaRepositoryImpl();
		wizytaRepository.dodajSkierowanie(skierowanie);

		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.loadContent(generujSkierowanieHTML());

		PrinterJob job = PrinterJob.createPrinterJob();

		if (job != null && job.showPrintDialog(skierowanieDoTextField.getScene().getWindow())) {
			boolean success = job.printPage(browser);
			if (success) {
				job.endJob();

				Stage stage = (Stage) skierowanieDoTextField.getScene().getWindow();
				stage.close();
			} else {
				Komunikaty.wyswietlOstrzezenie("B³¹d!", "B³¹d podczas drukowania!");
			}
		}

	}

	private String generujSkierowanieHTML() {

		String skierowanieDo = skierowanieDoTextField.getText();
		String imieINazwiskoPacjenta = pacjentOsoba.getOsoba().getImie() + " " + pacjentOsoba.getOsoba().getNazwisko();
		String pesel = wizyta.getPESEL_pacjenta().getPESEL();
		String na = skierowanieNaTextField.getText();
		String rozpoznanie = rozpoznanieTextArea.getText();
		String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String imieINazwiskoLekarza = lekarz.getOsoba().getImie() + " " + lekarz.getOsoba().getNazwisko();

		String kodHTML = "<!DOCTYPE html><html><head> <meta charset=\"utf-8\"> <style type=\"text/css\">"
				+ "h1{text-align: center;	margin-bottom: 10px;}"
				+ "div{border: 1px solid;	width: 397px; padding: 20px;}"
				+ "p{padding: 0px; margin: 0px; text-align: center;} " + ".lewy{text-align: center;	width: 50%;	}"
				+ ".prawy{text-align: center;	}" + "</style></head><body><div>" + "<h1>Skierowanie</h1>" + "<p>do "
				+ skierowanieDo + "</p><br>" + "Kieruje siê Pana/i¹: " + imieINazwiskoPacjenta + "<br>" + "PESEL: "
				+ pesel + "<br>" + "celem: " + na + "<br>" + "Rozpoznanie: " + rozpoznanie + "<br>"
				+ "<br><table width=\"100%\">" + "<tr><td class=\"lewy\">" + "Data:<br>" + data
				+ "</td><td class=\"prawy\">" + "Podpis lekarza:<br>" + "________________<br>" + "dr "
				+ imieINazwiskoLekarza + "</td></tr></table>" + "</div></body></html>";

		return kodHTML;
	}

	@FXML
	private JFXTextField skierowanieDoTextField;

	@FXML
	private JFXTextField skierowanieNaTextField;

	@FXML
	private JFXTextArea rozpoznanieTextArea;

}
