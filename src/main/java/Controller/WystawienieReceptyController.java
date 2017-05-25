package Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import View.Komunikaty;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.Lekarz;
import model.Leki;
import model.Leki_Recepta;
import model.Pacjent;
import model.Recepta;
import model.Wizyta;
import model.repository.LekiRepository;
import model.repository.WizytaRepository;
import model.repository.impl.LekiRepositoryImpl;
import model.repository.impl.WizytaRepositoryImpl;

public class WystawienieReceptyController {

	// wybrana wizyta - WSTAWIÆ WIZYTE!!!
	public Wizyta wizyta = new WizytaRepositoryImpl().getListaWizyt().get(0);

	// zalogowany lekarz - NIE MODYFOKOWAÆ
	private Lekarz lekarz;
	// wybrany pacjent - NIE MODYFIKOWAÆ
	private Pacjent pacjentOsoba;

	private ObservableList<LekiReceptaRekord> rekordyWystawionychLekow;

	private Recepta recepta;

	@FXML
	void initialize() {
		lekarz = wizyta.getID_lekarza();
		pacjentOsoba = wizyta.getPESEL_pacjenta();

		recepta = new Recepta();
		recepta.setDawkowanie("-");
		recepta.setWizyta(wizyta);

		rekordyWystawionychLekow = FXCollections.observableArrayList();

		LekiRepository lekiRepository = new LekiRepositoryImpl();
		List<Leki> listaLekowZBazy = lekiRepository.pobierzListeLekow();

		listaLekow.getItems().addAll(listaLekowZBazy);

		// inicjalizacja tabeli
		nazwaLekuColumn.setCellValueFactory(new PropertyValueFactory<LekiReceptaRekord, String>("nazwa"));
		producentColumn.setCellValueFactory(new PropertyValueFactory<LekiReceptaRekord, String>("producent"));
		refundacjaColumn.setCellValueFactory(new PropertyValueFactory<LekiReceptaRekord, Integer>("Procent_refundacji"));
		dawkowanieColumn.setCellValueFactory(new PropertyValueFactory<LekiReceptaRekord, String>("Dawkowanie"));
		usunColumn.setCellValueFactory(new PropertyValueFactory<LekiReceptaRekord, JFXButton>("usun"));

		tabela.setItems(rekordyWystawionychLekow);

	}

	@FXML
	void dodajLekDoListy(ActionEvent event) {

		Leki lek = listaLekow.getValue();
		int refundacja = 0;
		try {
			if (refundacjaTextField.getText().length() == 0) {
				refundacja = 0;
			} else {
				refundacja = Integer.parseInt(refundacjaTextField.getText());
			}

		} catch (Exception e) {
			Komunikaty.wyswietlOstrzezenie("Uwaga!", "W polu refundacja wporwadzono nieodpowiedni¹ wartoœæ!");
		} finally {
			refundacja = 0;
		}

		// czyszczenie formularza leków
		refundacjaTextField.setText("");
		dawkowanieTextField.setText("");
		String dawkowanie = dawkowanieTextField.getText();

		LekiReceptaRekord lekiReceptaRekord = new LekiReceptaRekord(lek);
		lekiReceptaRekord.setProcent_refundacji(refundacja);
		lekiReceptaRekord.setDawkowanie(dawkowanie);

		rekordyWystawionychLekow.add(lekiReceptaRekord);

	}

	@FXML
	void drukujRecepte(ActionEvent event) throws IOException {

		wczytajRecepteZFormularza();
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.loadContent(generujRecepteHTML());

		PrinterJob job = PrinterJob.createPrinterJob();

		if (job != null && job.showPrintDialog(listaLekow.getScene().getWindow())) {
			boolean success = job.printPage(browser);
			if (success) {
				job.endJob();
			}
		}
	}

	@FXML
	void zapiszRecepte(ActionEvent event) {

		wczytajRecepteZFormularza();

		WizytaRepository wizytaRepository = new WizytaRepositoryImpl();
		wizytaRepository.dodajRecepte(recepta);
	}

	private void wczytajRecepteZFormularza() {

		if (rekordyWystawionychLekow.size() == 0) {
			Komunikaty.wyswietlOstrzezenie("B³¹d", "Brak leków na recepcie!");
		} else {

			if (dataRezalizacji.getValue() == null) {
				recepta.setData_waznosci(new Date());
			} else {
				recepta.setData_waznosci(new Date(dataRezalizacji.getValue().getYear() - 1900,
						dataRezalizacji.getValue().getMonthValue() - 1, dataRezalizacji.getValue().getDayOfMonth()));
			}
			Set<Leki_Recepta> setWystawionychLekow = new HashSet<Leki_Recepta>();
			for (LekiReceptaRekord lekReceptaRekord : rekordyWystawionychLekow) {
				setWystawionychLekow.add(lekReceptaRekord);
			}
			recepta.setLeki_Recepta(setWystawionychLekow);
		}

	}

	String generujRecepteHTML() {

		List<String> kodHTML = new ArrayList<String>();

		kodHTML.add("<!DOCTYPE html>");
		kodHTML.add("<html>");
		kodHTML.add("<head>");
		kodHTML.add("<meta charset=\"utf-8\">");
		kodHTML.add("<style type=\"text/css\">table tr{line-height: 20px;}p{	margin: 0;	padding: 0}"
				+ "body{ font-size: 12px;}	</style>");
		kodHTML.add("</head>");
		kodHTML.add("<body style=\"size=8\" width=\"400px\">");
		kodHTML.add("<table border=\"1px\" cellspacing=\"0\">");
		kodHTML.add("<tr>");
		kodHTML.add("<td colspan=\"2\">");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Recepta: " + recepta.getID() + "</p>");

		kodHTML.add("<center>" + lekarz.getOsoba().getImie() + " " + lekarz.getOsoba().getNazwisko() + "<br>");
		kodHTML.add(lekarz.getOsoba().getMiejscowosc() + " " + lekarz.getOsoba().getNumer_domu() + " " + "<br>");
		kodHTML.add(lekarz.getOsoba().getKod_pocztowy() + " " + lekarz.getOsoba().getPoczta());
		kodHTML.add("<br>TEL. " + lekarz.getOsoba().getNumer_telefonu() + "<br>");
		kodHTML.add("</center><p style=\"text-align: left; font-size: 10\">Œwiadczeniodawca</p>");

		kodHTML.add("</td>");
		kodHTML.add("</tr>");
		kodHTML.add("<tr>");
		kodHTML.add("<td rowspan=\"2\" width=\"60%\">");

		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Pacjent</p>");
		kodHTML.add(
				"<center>" + pacjentOsoba.getOsoba().getImie() + " " + pacjentOsoba.getOsoba().getNazwisko() + "<br>");
		kodHTML.add(pacjentOsoba.getOsoba().getMiejscowosc() + " " + pacjentOsoba.getOsoba().getNumer_domu() + "<br>");
		if (pacjentOsoba.getOsoba().getUlica() != null)
			kodHTML.add(pacjentOsoba.getOsoba().getUlica() + "<br>");
		kodHTML.add(pacjentOsoba.getPESEL() + "</center><br>");

		kodHTML.add("</td>");
		kodHTML.add("<td>");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Oddzia³ NFZ</p>");
		kodHTML.add("<center>7</center>");
		kodHTML.add("</td>");
		kodHTML.add("</tr>");
		kodHTML.add("<tr>");
		kodHTML.add("<td>");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Uprawnienia dodatkowe:</p>");
		kodHTML.add("<center>brak</center>");
		kodHTML.add("</td>");
		kodHTML.add("</tr>");

		kodHTML.add("<tr>");
		kodHTML.add("<th>");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Leki:<br>");
		kodHTML.add("</th>");
		kodHTML.add("<th>");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Odp³atnoœæ:</p>");
		kodHTML.add("</th>");
		kodHTML.add("</tr>");

		// leki
		for (LekiReceptaRekord lekiReceptaRekord : rekordyWystawionychLekow) {
			kodHTML.add("<tr>");
			kodHTML.add("<td>");
			kodHTML.add("<p style=\"text-align: left;\">" + lekiReceptaRekord.getNazwa() + " "
					+ lekiReceptaRekord.getProducent() + "</p>");
			kodHTML.add("</td>");
			kodHTML.add("<td><center>");
			kodHTML.add(lekiReceptaRekord.getProcent_refundacji() + "%");
			kodHTML.add("</center></td>");
			kodHTML.add("</tr>");
		}

		kodHTML.add("<tr>");
		kodHTML.add("<td>");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Data wystawienia:</p>");
		kodHTML.add("<center>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "<br></center>");
		kodHTML.add("</td>");
		kodHTML.add("<td rowspan=\"2\">");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Dane i podpis lekarza</p>");
		kodHTML.add("<br><center>______________<br>");
		kodHTML.add("dr." + lekarz.getOsoba().getImie() + " " + lekarz.getOsoba().getNazwisko() + "</center><br>");
		kodHTML.add("</td>");
		kodHTML.add("</tr>");
		kodHTML.add("<tr>");
		kodHTML.add("<td>");
		kodHTML.add("<p style=\"text-align: left; font-size: 10\">Data realizacji \"od dnia\":</p>");
		kodHTML.add("center>");
		if (recepta.getData_waznosci() == null) {
			kodHTML.add("-");
		} else {
			kodHTML.add(new SimpleDateFormat("yyyy-MM-dd").format(recepta.getData_waznosci()));
		}
		kodHTML.add("<br></center>");
		kodHTML.add("</td>");
		kodHTML.add("</tr>");
		kodHTML.add("</table>");
		kodHTML.add("</body>");
		kodHTML.add("</html>");

		String returnString = "";
		for (String string : kodHTML) {
			returnString += string + "\n";
		}

		return returnString;
	}

	@FXML
	private ChoiceBox<Leki> listaLekow;

	@FXML
	private TextField refundacjaTextField;

	@FXML
	private JFXTextArea dawkowanieTextField;

	@FXML
	private TableView<LekiReceptaRekord> tabela;

	@FXML
	private TableColumn<LekiReceptaRekord, String> nazwaLekuColumn;

	@FXML
	private TableColumn<LekiReceptaRekord, String> producentColumn;

	@FXML
	private TableColumn<LekiReceptaRekord, Integer> refundacjaColumn;

	@FXML
	private TableColumn<LekiReceptaRekord, String> dawkowanieColumn;

	@FXML
	private TableColumn<LekiReceptaRekord, JFXButton> usunColumn;

	@FXML
	private JFXDatePicker dataRezalizacji;

	protected class LekiReceptaRekord extends Leki_Recepta {

		private JFXButton usun;
		private String nazwa;
		private String producent;

		public LekiReceptaRekord(Leki lek) {
			this.setLeki(lek);
			this.setRecepta(recepta);
			this.nazwa = lek.getNazwa();
			this.producent = lek.getProducent();

			JFXButton buttonUsun = new JFXButton();
			FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
			buttonUsun.setGraphic(icon);
			buttonUsun.setStyle("-fx-background-color: #2598F3;");

			buttonUsun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					rekordyWystawionychLekow.remove(LekiReceptaRekord.this);
				}
			});
			this.setUsun(buttonUsun);

		}

		public String getNazwa() {
			return nazwa;
		}

		public void setNazwa(String nazwa) {
			this.nazwa = nazwa;
		}

		public String getProducent() {
			return producent;
		}

		public void setProducent(String producent) {
			this.producent = producent;
		}

		public JFXButton getUsun() {
			return usun;
		}

		public void setUsun(JFXButton usun) {
			this.usun = usun;
		}

		public Leki_Recepta konwertujNaLeki_Recepta() {
			return (Leki_Recepta) this;
		}

	}
}