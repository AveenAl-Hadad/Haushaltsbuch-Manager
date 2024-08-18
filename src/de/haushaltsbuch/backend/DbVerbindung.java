package de.haushaltsbuch.backend;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Hier werden eine Singleton-Implementierung für die Verbindung zu einer MySQL-Datenbank darstellt.  
 */
public class DbVerbindung {
	// Definieren der Verbindungs-URL, Benutzername und Passwort
	 private static final String url = "jdbc:mysql://localhost:3306/haushaltsbuch_db?createDatabaseIfNotExist=true";
	 private static final String user = "root";
     private static final String passwort = "";
    // Verbindungsobjekt
     private static Connection verbindung;

     // Privater Konstruktor, um die direkte Instanziierung zu verhindern
     private DbVerbindung() {     }

     // Methode, um eine Verbindungsinstanz zurückzugeben
     public static Connection getInstance() {
         // Überprüfen, ob die Verbindung bereits besteht
         if (verbindung == null) {
             try {
                 // Verbindung zur Datenbank herstellen
                 verbindung = DriverManager.getConnection(url, user, passwort);
             } catch (SQLException ausnahme) {
                 ausnahme.printStackTrace();
             }
         }
         // Rückgabe der Verbindungsinstanz
         return verbindung;
     }
 }