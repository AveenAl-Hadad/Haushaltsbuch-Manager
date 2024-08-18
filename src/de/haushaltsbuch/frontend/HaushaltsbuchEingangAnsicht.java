package de.haushaltsbuch.frontend;
import java.time.LocalDate;
import java.time.Month;
import de.haushaltsbuch.backend.HaushaltsbuchDao;
import de.haushaltsbuch.dtos.Haushaltsbuch;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Diese Klasse: stellt eine Ansicht für ein Haushaltsbuch-Eingabeformular dar, die von der VBox-Klasse erbt.
 * Und enthält verschiedene UI-Elemente und Logik, um die Daten anzuzeigen und zu verwalten. 
 * 
 * @author Aveen Al-Hadad
 * @version 23.01.2024
 */
public class HaushaltsbuchEingangAnsicht extends VBox{

	private Button neuerEintrag;
	private HaushaltsbuchDao dao;
		
	/**
	 * Der Konstruktor: nimmt ein HaushaltsbuchDao-Interface als Parameter und initialisiert die Instanzvariable "dao" damit.
	 * Die Methode "BenutzeroberflaecheErstellen" wird aufgerufen, um die Benutzeroberfläche zu erstellen.
	 * Diese DAO-Interface wird verwendet, um auf die Datenbank zuzugreifen und Informationen abzurufen. 

	 * @param dao: ein HaushaltsbuchDao-Interface
	 */
	public HaushaltsbuchEingangAnsicht(HaushaltsbuchDao dao) {
		this.dao = dao;
		benutzeroberflaecheErstellen();
	}
	
	/**
	 * Die Methode: fügt verschiedene Elemente zur Ansicht hinzu, darunter eine TableView, Buttons und Labels.
	 */
	private void benutzeroberflaecheErstellen() {
		getChildren().addAll(getTabelle());
		balkenFuerBetraegeAnzeigen();
		hinzufuegenKnopfErstellen();
	}
	
	/**
	 * Die Methode: erstellt also eine VBox(vertikaler Behälter), die eine Tabelle mit Daten
	 *  aus dem "dao" enthält und einen Filter für das Datum ermöglicht.  
	 * 
	 * @return behaelterFuerFilterUndTabelleBehaelter: gibt den Typ der VBox zurück, die die Filter und erstellte TableView enthält.
	 */
	private VBox getTabelle() {

		ObservableList<Haushaltsbuch> uebersicht = dao.datenLiefern();
		TableView<Haushaltsbuch> tabelle = new TableView<>(uebersicht);
		tabelle.setItems(uebersicht);

		// Spalten erzeugen und verknüpfen
		TableColumn<Haushaltsbuch, String> datumSpalte = new TableColumn<>("Datum");
		datumSpalte.setCellValueFactory(new PropertyValueFactory<>("datum"));
		tabelle.getColumns().add(datumSpalte);
		
		
		TableColumn<Haushaltsbuch, String> kategorieSpalte = new TableColumn<>("Kategorie");
		kategorieSpalte.setCellValueFactory(new PropertyValueFactory<>("kategorie"));
		tabelle.getColumns().add(kategorieSpalte);
		
		TableColumn<Haushaltsbuch, String> kontoSpalte = new TableColumn<>("Konto");
		kontoSpalte.setCellValueFactory(new PropertyValueFactory<>("konto"));
		tabelle.getColumns().add(kontoSpalte);
		
		TableColumn<Haushaltsbuch, String> beschreibungSpalte = new TableColumn<>("Beschreibung");
		beschreibungSpalte.setCellValueFactory(new PropertyValueFactory<>("beschreibung"));
		tabelle.getColumns().add(beschreibungSpalte);

		TableColumn<Haushaltsbuch, String> betragStringSpalte = new TableColumn<>("Betrag");
		betragStringSpalte.setCellValueFactory(new PropertyValueFactory<>("betragToString"));
		tabelle.getColumns().add(betragStringSpalte);
		 
		// Filter für Datum hinzufügen
		FilteredList<Haushaltsbuch> gefilterteDaten = new FilteredList<>(uebersicht, praedkat -> true);
	    SortedList<Haushaltsbuch> sortierteDaten = new SortedList<>(gefilterteDaten);
	    
	    //Die sortierteDaten-Liste wird an die comparatorProperty() der tabelle (vermutlich eine TableView) gebunden.
	    //Dadurch wird sichergestellt, dass die Daten in der Tabelle entsprechend sortiert werden.
	    sortierteDaten.comparatorProperty().bind(tabelle.comparatorProperty());
	    tabelle.setItems(sortierteDaten);

	    //Ein DatePicker: wird erstellt. Dieser ermöglicht es dem Benutzer, ein Datum auszuwählen.
	    DatePicker datumsAuswahl = new DatePicker();
	    //Wenn der Benutzer ein Datum auswählt, wird der Code im "EventHandler" ausgeführt.
	    datumsAuswahl.setOnAction(event -> {
	        LocalDate ausgewaehltesDatum = datumsAuswahl.getValue();
	        gefilterteDaten.setPredicate(haushaltsbuch -> {
	            if (ausgewaehltesDatum == null) {
	                return true;
	            } else {
	                // Hier können Sie den Filter nach Jahren oder Monaten anpassen
	                // Zum Beispiel: Filter nach Jahr und Monat
	            	Month selectedMonat = ausgewaehltesDatum.getMonth();
	            	Month haushaltsbuchMonat = haushaltsbuch.getDatum().getMonth();
	                
	                int selectedYear = ausgewaehltesDatum.getYear();
	                int haushaltsbuchYear = haushaltsbuch.getDatum().getYear();
	                return haushaltsbuchYear == selectedYear && haushaltsbuchMonat == selectedMonat;
	            }
	        });
	    });
	    Label filter = new Label("Filter mit Datum");
	    VBox behaelterFuerFilter = new VBox(filter,datumsAuswahl);
	    behaelterFuerFilter.setSpacing(5);
	    behaelterFuerFilter.setPadding(new Insets(5));

	    VBox behaelterFuerTabelle = new VBox(tabelle);
	    behaelterFuerTabelle.setSpacing(10);
	    behaelterFuerTabelle.setPadding(new Insets(10));

	    VBox behaelterFuerFilterUndTabelleBehaelter = new VBox(behaelterFuerFilter, behaelterFuerTabelle);
	   	    
	    return behaelterFuerFilterUndTabelleBehaelter;	
	}
	
	/**
	 * Die Methode: initialisiert eine behälter von Typ HBox(horizontale Box), die verschiedene Label (Etikett) für Beträge enthält.
	 * Diese Etikett werden durch die Methoden "gesamtBetragEinnahmeBerechnen()",
	 *  "gesamtBetragAusgabeBerechnen()" und "gesamtBetrag()" erzeugt.
	 *  
	 * @return behaelterFuerBtraegeAnzeigen: gibt den Typ der HBox zurück, die die drei Etikett enthält.
	 */
	private HBox balkenFuerBetraegeAnzeigen() {
		HBox behaelterFuerBtraegeAnzeigen = new HBox( gesamtBetragEinnahmeBerechnen(), gesamtBetragAusgabeBerechnen(),gesamtBetrag());
		behaelterFuerBtraegeAnzeigen.setSpacing(50);
		behaelterFuerBtraegeAnzeigen.setId("behaelterFuerAlleArtsBetrag");
	    this.getChildren().addAll(behaelterFuerBtraegeAnzeigen);
		return behaelterFuerBtraegeAnzeigen;
	}
		    
	/**
	 * Die Methode: berechnet den Gesamtbetrag aller Einträge im Haushaltsbuch
	 * mithilfe des dao-Objekts und formatiert ihn als String mit zwei
	 * Dezimalstellen.
	 * 
	 * Sie erstellt ein Label mit dem Text "Saldo: " gefolgt vom berechneten Betrag
	 * und gibt es zurück.
	 * 
	 * @return gesamtBetrag: ein Label Typ zurückgibt, die die summe für berechneten Betrag enthählt.
	 */
	private Label gesamtBetrag() {
		Double betrag = dao.gesamtBetragBerechnen();
		String string = String.format("%.2f", betrag);
		Label gesamtBetrag = new Label("Saldo: " + string +" €" );
		gesamtBetrag.setId("gesambetragLabel");
		return gesamtBetrag;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag aller Einträge für Einnahmen im Haushaltsbuch
	 * mithilfe des dao-Interface und formatiert ihn als String mit zwei Dezimalstellen. 
	 * Sie erstellt ein Label mit dem Text "Einnahme: " gefolgt vom berechneten Einnahmen Betrag
	 * und gibt es zurück.
	 * 
	 * @return gesamtEinnahme: ein Label Typ zurückgibt, die die summe für berechneten Einnahmen Betrag enthählt.
	 */
	private Label gesamtBetragEinnahmeBerechnen() {
		Double betrag = dao.gesamtBetragEinnahme();
		String string = String.format("%.2f", betrag);
		Label gesamtEinnahme = new Label("Einnahme: " + string +" €" );
		gesamtEinnahme.setId("gesamEinnahmeLabel");
		return gesamtEinnahme;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag aller Einträge für Ausgaben im Haushaltsbuch
	 * mithilfe des dao-Objekts und formatiert ihn als String mit zwei Dezimalstellen.
	 * Sie erstellt ein Label mit dem Text "Ausgabe: " gefolgt vom berechneten Ausgaben Betrag
	 * und gibt es zurück.
	 * 
	 * @return gesamtAusgabe: ein Label Typ zurückgibt, die die summe für berechneten Ausgaben Betrag enthählt.
	 */
	private Label gesamtBetragAusgabeBerechnen() {
		Double betrag = dao.gesamtBetragAusgabe();
		String string = String.format("%.2f", betrag);
		Label gesamtAusgabe = new Label("Ausgabe: " + string +" €" );
		gesamtAusgabe.setId("gesamtAusgabeLabel");
		return gesamtAusgabe;
	}
	
	/**
	 * Diese Methode: erstellt einen Schaltfläche für einen neuen Eintrag und ihn in einer vertikalen Box anzuzeigen.
	 *  
	 * @return behaelterFuerNeuerEintrag: gibt den Typ der VBox zurück, die die Schaltfläche "neuerEintrag" enthält.
	 */
	private VBox hinzufuegenKnopfErstellen() {
		neuerEintrag = new Button();
		neuerEintrag.setId("neuerEintragKnopf");
		VBox behaelterFuerNeuerEintrag = new VBox(neuerEintrag);
		behaelterFuerNeuerEintrag.setId("behaelterFuerNeuerEintrag");
		behaelterFuerNeuerEintrag.setAlignment(Pos.TOP_RIGHT);
		this.getChildren().addAll(behaelterFuerNeuerEintrag);
		return behaelterFuerNeuerEintrag;
	}

	/**
	 * @return the neuerEintrag
	 */
	public Button getNeuerEintrag() {
		return neuerEintrag;
	}

}
