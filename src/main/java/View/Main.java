package View;

import java.math.BigDecimal;

import org.hibernate.Session;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import model.HibernateUtil;
import model.Specjalizacja;

public class Main extends Application {

	public static void main(String[] args) {
		Specjalizacja specjalizacja = new Specjalizacja();
		specjalizacja.setCzas_wizyty(20);
		specjalizacja.setNazwa("Test");
		specjalizacja.setKoszt_wizyty(new BigDecimal(30));

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Save the employee in database
		session.save(specjalizacja);

		// Commit the transaction
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("/fxml/Register.fxml"));
		StackPane stackPane = loader.load();

		Scene scene = new Scene(stackPane);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Rejestracja pracownika");
		primaryStage.show();
	}

}
