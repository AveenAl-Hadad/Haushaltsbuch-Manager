package de.haushaltsbuch.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import de.haushaltsbuch.backend.HaushaltsbuchDao;
import de.haushaltsbuch.backend.HaushaltsbuchDaoImpl;
import de.haushaltsbuch.frontend.HaushaltsbuchEingangAnsicht;
import de.haushaltsbuch.frontend.HaushaltsbuchNeuerEintragAnsicht;
import de.haushaltsbuch.frontend.HaushaltsbuchKategorienAusgabenStatistikAnsicht;
import de.haushaltsbuch.frontend.HaushaltsbuchKategorienEinnahmenStatistikAnsicht;
import de.haushaltsbuch.frontend.HaushaltsbuchKontenAnsicht;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Diese KLasse: ist ein Teil einer Anwendung zur Verwaltung eines Haushaltsbuchs. Sie enthält verschiedene
 *  Methoden und Funktionen, um die Benutzeroberfläche zu steuern und mit der Datenbank zu interagieren.
 *  
 * Als Quelle zu verwenden:
 * 	 - "BorderPane"-Object und Icon für Stage Titel in JavaFX benutzen habe ich von diesem Link:
 * 		 "https://docs.oracle.com/javaFX" nach geschaut.
 * 	 - Error zu beheben habe ich mit "stackoverflow.com" am meisten nach geschaut manchmal mit "ChatGpt"
 * 
 * @authorAveen Al-Hadad
 * @version 24.01.2024
 */
public class HaushaltsbuchController {
	private HaushaltsbuchDao dao;
	private HaushaltsbuchEingangAnsicht eingangAnsicht;
	private HaushaltsbuchNeuerEintragAnsicht neuerEintrag;
	private HaushaltsbuchKategorienAusgabenStatistikAnsicht statistikAusgaben;
	private HaushaltsbuchKategorienEinnahmenStatistikAnsicht statistikEinnahmen;
	private HaushaltsbuchKontenAnsicht kontenAnsicht;
	private Button statistikKnopf;
	private Button kontenKnopf;
	private Button buchungenKnopf;
	private BorderPane hauptSeite;
	private Scene scene;
	
	/**
	 * Der Konstruktor: initialisiert verschiedene Objekte und Ansichten, erstellt
	 * die erforderliche Tabelle in der Datenbank und konfiguriert die
	 * Benutzeroberfläche.
	 * 
	 * @param stage
	 * @throws SQLException
	 */
	public HaushaltsbuchController(Stage stage) throws SQLException {

		dao = new HaushaltsbuchDaoImpl();
		dao.erstellenInDatenbankHaushaltsbuchTabelle();
		
      // Erstellen die Objekte
		eingangAnsicht = new HaushaltsbuchEingangAnsicht(dao);
		neuerEintrag = new HaushaltsbuchNeuerEintragAnsicht();
		statistikAusgaben = new HaushaltsbuchKategorienAusgabenStatistikAnsicht();
		statistikEinnahmen = new HaushaltsbuchKategorienEinnahmenStatistikAnsicht();
		kontenAnsicht = new HaushaltsbuchKontenAnsicht();
    
	 // Erstelle das Layout für das Menü mit die drei Knöpfe
        HBox behaelterFuerBuchungUndKontenUndStatistik = new HBox();
        behaelterFuerBuchungUndKontenUndStatistik.setSpacing(130);
        behaelterFuerBuchungUndKontenUndStatistik.getChildren().addAll(buchungKnopfErstellen(),kontenKnopfErsellen(), statistikKnopfErstellen());
	    
     // Erstelle das Layout für die Seiten
        hauptSeite = new BorderPane(eingangAnsicht);
        hauptSeite.setBottom(behaelterFuerBuchungUndKontenUndStatistik);
	    
        scene = new Scene(hauptSeite,550,700); 
		scene.getStylesheets().add("file:styles/Style.css");

		eingangAnsicht.getNeuerEintrag().setOnAction(klick -> scene.setRoot(neuerEintrag));
		neuerEintrag.getEintragSpeichern().setOnAction(klick -> speichern());
		neuerEintrag.getAbbrechen().setOnAction(klick -> scene.setRoot(hauptSeite));
		statistikAusgaben.getEinnahmen().setOnAction(klick -> scene.setRoot(statistikEinnahmen));
		statistikEinnahmen.getZurueckKnopf().setOnAction(klick -> scene.setRoot(hauptSeite));
					
		stage.setScene(scene);
		stage.setTitle("Haushaltsbuch-App");
		// Setze das Icon
        Image icon = new Image("file:bilder/statik-bg.png");
        stage.getIcons().add(icon);
        
		stage.show();
	}

	/**
	 * Die Methode: validiert die eingegebenen Daten für einen neuen Eintrag und
	 * speichert sie in der Datenbank, falls sie gültig sind.
	 */
	private void speichern() {
		if (neuerEintrag.datenValidieren(neuerEintrag.getDatumEingabe(), neuerEintrag.getKategorieEingabe(),
				neuerEintrag.getKontoEingabe(), neuerEintrag.getBeschreibungEingabe(),
				neuerEintrag.getBetragEingabe())) {
			String datumString = neuerEintrag.getDatumEingabe().getValue().toString();
			LocalDate datum = LocalDate.parse(datumString);
			String kategorie = neuerEintrag.getKategorieEingabe().getValue();
			String konto = neuerEintrag.getKontoEingabe().getValue();
			String beschreibung = neuerEintrag.getBeschreibungEingabe().getText();
			String betragString = neuerEintrag.getBetragEingabe().getText().replace(",", ".");
			double betrag = Double.parseDouble(betragString);
			auswahlEinAuszahlung(datum, kategorie, konto, beschreibung, betrag);
			neuerEintrag.setAllesNull();
			HaushaltsbuchEingangAnsicht aktuelleEinganAnsicht = new HaushaltsbuchEingangAnsicht(dao);
			scene.setRoot(aktuelleEinganAnsicht);
			aktuelleEinganAnsicht.getNeuerEintrag().setOnAction(event -> scene.setRoot(neuerEintrag));
		}
	}

	/**
	 * Die Methode: unterscheidet zwischen Einzahlungen und Ausgaben und fügt die
	 * entsprechenden Daten in die Datenbank ein.
	 * 
	 * @param datum
	 * @param kategorie
	 * @param konto
	 * @param beschreibung
	 * @param betrag
	 */
	private void auswahlEinAuszahlung(LocalDate datum, String kategorie, String konto, String beschreibung,
			double betrag) {
		String string = neuerEintrag.getGroup().getSelectedToggle().toString();
		if (string.contains("Ausgabe")) {
			dao.auszahlungEinfuegen(datum, kategorie, konto, beschreibung, betrag);
		} else
			dao.einzahlungEinfuegen(datum, kategorie, konto, beschreibung, betrag);
	}
	
	/**
	 * Die Methode: erstellt einen Knopf und noch ein Label für die Buchungen.
	 * Beide Elemente werden in einer vertikalen Box "VBox" platziert
	 * @return behaelterFuerBuchungen: ein VBox Type zurückgibt, die zwei Elemente enthält.
	 */
    private VBox buchungKnopfErstellen() {
        buchungenKnopf = new Button();
        buchungenKnopf.setId("buchungenKnopf");
        Label buchungen = new Label("Buchungen");
        VBox behaelterFuerBuchungen = new VBox(buchungenKnopf, buchungen);
        buchungenKnopf.setOnAction(event-> {
        	hauptSeite.setCenter(eingangAnsicht);
        });
        return behaelterFuerBuchungen;
    }

    /**
	 * Die Methode: erstellt einen Knopf und noch ein Label für die Konten.
	 * Beide Elemente werden in einer vertikalen Box "VBox" platziert
	 * @return behaelterFuerBuchungen: ein VBox Type zurückgibt, die zwei Elemente enthält.
	 */
    private VBox kontenKnopfErsellen() {
        kontenKnopf = new Button();
        kontenKnopf.setId("kontenKnopf");
        Label konten = new Label("Konten");
        VBox behaelterFuerKonten = new VBox(kontenKnopf, konten);
        kontenKnopf.setOnAction(event-> {
        	hauptSeite.setCenter(kontenAnsicht);
        });
        return behaelterFuerKonten;
    }

    /**
	 * Die Methode: erstellt einen Knopf und noch ein Label für die Statistik.
	 * Beide Elemente werden in einer vertikalen Box "VBox" platziert
	 * @return behaelterFuerBuchungen: ein VBox Type zurückgibt, die zwei Elemente enthält.
	 */
    private VBox statistikKnopfErstellen() {
        statistikKnopf = new Button();
        statistikKnopf.setId("statistikKnopf");
        Label statistik = new Label("Statistik");
        VBox behaelterFuerStatistik = new VBox(statistikKnopf, statistik);
        statistikKnopf.setOnAction(event-> {
        	hauptSeite.setCenter(statistikAusgaben);
        });
        return behaelterFuerStatistik;
    }

}
