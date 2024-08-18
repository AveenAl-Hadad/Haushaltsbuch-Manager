package de.haushaltsbuch.Main;
import java.sql.SQLException;
import de.haushaltsbuch.controller.HaushaltsbuchController;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Die Klasse: erweitert die Klasse "Application" und dient als Einstiegspunkt für die Anwendung. 
 * In der Methode "start" wird eine neue Instanz des "HaushaltsbuchController" erstellt und dem 
 *  Konstruktor der Klasse der "primaryStage" übergeben.
 *  
 *  @author Aveen Al-Hadad
 *  @version 25.01.2024
 */
public class HaushaltsbuchApp extends Application {

	@Override
	public void start(Stage primaryStage) throws SQLException {
		
     	new HaushaltsbuchController(primaryStage);		
	}

}
