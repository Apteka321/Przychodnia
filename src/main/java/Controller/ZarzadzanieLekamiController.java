package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import View.Komunikaty;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Leki;
import model.repository.LekiRepository;
import model.repository.impl.LekiRepositoryImpl;

public class ZarzadzanieLekamiController {
	LekiRepository lekiRepository;
	ObservableList<RekordLeku> listaRekordowLekow;
	ObservableList<RekordLeku> listaWszystkichRekordowLekow;

	@FXML
	public void initialize() {
		lekiRepository = new LekiRepositoryImpl();
		listaRekordowLekow = FXCollections.observableArrayList();
		listaWszystkichRekordowLekow = FXCollections.observableArrayList();
		List<Leki> listaLekow = lekiRepository.pobierzListeLekow();

		for (Leki leki : listaLekow) {
			RekordLeku rekordLeku = new RekordLeku(leki);
			listaRekordowLekow.add(rekordLeku);
			listaWszystkichRekordowLekow.add(rekordLeku);
		}

		zaznacz.setCellValueFactory(new PropertyValueFactory<>("zaznaczony"));
		zaznacz.setStyle("-fx-alignment: CENTER;");
		nazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
		producent.setCellValueFactory(new PropertyValueFactory<>("producent"));
		zawartosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
		modyfikuj.setCellValueFactory(new PropertyValueFactory<>("modyfikuj"));
		modyfikuj.setStyle("-fx-alignment: CENTER;");

		tabelaLekow.setItems(listaRekordowLekow);
	}

	@FXML
	void dodajLek(MouseEvent event) {
		try {
			Parent root = FXMLLoader.load(this.getClass().getResource("/fxml/dodajLek.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Dodawanie leku do bazy");
			stage.setScene(new Scene(root, 610, 175));
			stage.resizableProperty().set(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void wyszukajLeki(KeyEvent event) {
		ObservableList<RekordLeku> znelezioneLeki = FXCollections.observableArrayList();
		znelezioneLeki.clear();
		String wpisanaFraza = szukaj.getText();

		String[] tablicaFraz = wpisanaFraza.split(" ");
		int ileSpacji = wpisanaFraza.length() - wpisanaFraza.replace(" ", "").length();

		if (wpisanaFraza.length() != 0) {

			for (Leki lek : listaWszystkichRekordowLekow) {

				Leki aktualnyLek = lek;
				boolean flaga = false;
				for (String string : tablicaFraz) {
					boolean zawieraJednaFraze = false;
					if (lek.getNazwa().toUpperCase().contains(string.toUpperCase())) {
						zawieraJednaFraze = true;
					}

					if (lek.getIlosc().toUpperCase().contains(string.toUpperCase())) {
						zawieraJednaFraze = true;
					}
					if (lek.getProducent().toUpperCase().contains(string.toUpperCase())) {
						zawieraJednaFraze = true;
					}

					if (zawieraJednaFraze) {
						flaga = true;
					}

				}
				if (flaga) {
					RekordLeku rekordLeku = new RekordLeku(lek);
					znelezioneLeki.add(rekordLeku);
				}
			}
			listaRekordowLekow.clear();
			listaRekordowLekow.addAll(znelezioneLeki);
		} else {
			listaRekordowLekow.clear();
			listaRekordowLekow.addAll(listaWszystkichRekordowLekow);
		}
	}

	@FXML
	void usunZaznaczoneLeki(MouseEvent event) {
		List<Leki> zaznaczoneLeki = new ArrayList<>();
		for (RekordLeku rekordLeku : listaRekordowLekow) {
			if (rekordLeku.getZaznaczony().isSelected()) {
				zaznaczoneLeki.add(rekordLeku.konwetrujNaLeki());
			}
		}

		if (zaznaczoneLeki.isEmpty()) {
			Komunikaty.wyswietlInformacje("B³¹d!", "Aby usun¹æ musisz zaznaczyæ con najmniej jeden lek!");
		} else {
			if (Komunikaty.wyswietlPotwierdzenie("Potwierdzenie", "Kasowanie leków z bazy",
					"Na pewno chcesz skasowaæ leki?")) {
				lekiRepository.usunLeki(zaznaczoneLeki);
				// odœwie¿enie listy w tabeli
				listaRekordowLekow.clear();
				listaWszystkichRekordowLekow.clear();
				List<Leki> listaLekow = lekiRepository.pobierzListeLekow();

				for (Leki leki : listaLekow) {
					RekordLeku rekordLeku = new RekordLeku(leki);
					listaRekordowLekow.add(rekordLeku);
					listaWszystkichRekordowLekow.add(rekordLeku);
				}
			}
		}
	}

	public void odswiezTabele() {
		button.setText("Odsiwzono");
		System.out.println("Odswiezanie tabeli");
		lekiRepository = new LekiRepositoryImpl();
		listaRekordowLekow.clear();
		listaWszystkichRekordowLekow.clear();
		List<Leki> listaLekow = lekiRepository.pobierzListeLekow();

		for (Leki leki : listaLekow) {

			RekordLeku rekordLeku = new RekordLeku(leki);
			listaRekordowLekow.add(rekordLeku);
			listaWszystkichRekordowLekow.add(rekordLeku);
		}
		tabelaLekow.refresh();
	}

	public class RekordLeku extends Leki {
		private JFXCheckBox zaznaczony;
		private JFXButton modyfikuj;

		public RekordLeku(Leki lek) {
			this.setID(lek.getID());
			this.setIlosc(lek.getIlosc());
			this.setNazwa(lek.getNazwa());
			this.setProducent(lek.getProducent());

			JFXButton buttonModyfikuj = new JFXButton();
			FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.COG);
			buttonModyfikuj.setGraphic(icon);
			buttonModyfikuj.setStyle("-fx-background-color: #2598F3;");
			buttonModyfikuj.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					Parent root;
					try {
						root = fxmlLoader.load(this.getClass().getResource("/fxml/modyfikujLek.fxml").openStream());
						ModyfikowanieLekuController modyfikowanieLekuController = (ModyfikowanieLekuController) fxmlLoader
								.getController();
						modyfikowanieLekuController.wczytajLekDoFormularza(RekordLeku.this.konwetrujNaLeki());
						Stage stage = new Stage();
						stage.setTitle("Dodawanie leku do bazy");
						stage.setScene(new Scene(root, 610, 175));
						stage.resizableProperty().set(false);
						stage.show();

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			});
			this.setModyfikuj(buttonModyfikuj);

			JFXCheckBox checkBox = new JFXCheckBox();
			checkBox.setCheckedColor(Color.web("0x2598f3"));
			this.setZaznaczony(checkBox);
		}

		public Leki konwetrujNaLeki() {
			Leki lek = new Leki();
			lek.setID(this.getID());
			lek.setIlosc(this.getIlosc());
			lek.setNazwa(this.getNazwa());
			lek.setProducent(this.getProducent());
			return lek;
		}

		public CheckBox getZaznaczony() {
			return zaznaczony;
		}

		public void setZaznaczony(JFXCheckBox zaznaczony) {
			this.zaznaczony = zaznaczony;
		}

		public JFXButton getModyfikuj() {
			return modyfikuj;
		}

		public void setModyfikuj(JFXButton modyfikuj) {
			this.modyfikuj = modyfikuj;
		}

	}

	@FXML
	private JFXButton button;
	@FXML
	private JFXTextField szukaj;

	@FXML
	private TableView<RekordLeku> tabelaLekow;

	@FXML
	private TableColumn<RekordLeku, CheckBox> zaznacz;

	@FXML
	private TableColumn<RekordLeku, String> nazwa;

	@FXML
	private TableColumn<RekordLeku, String> producent;

	@FXML
	private TableColumn<RekordLeku, String> zawartosc;

	@FXML
	private TableColumn<RekordLeku, JFXCheckBox> modyfikuj;

}
