package de.haushaltsbuch.frontend;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Diese Klasse erweitert die Klasse "BorderPane" und stellt eine
 * Benutzeroberfläche für die Eingabe eines neuen Eintrags dar.
 * 
 * Sie enthält verschiedene Textfelder (datumEingabe, kategorieEingabe,
 * beschreibungEingabe, betragEingabe) für die Eingabe von Daten wie Datum,
 * Kategorie, Beschreibung und Betrag.
 * 
 * Es gibt auch Buttons (eintragSpeichern, abbrechen), um den Eintrag zu
 * speichern oder abzubrechen.
 * 
 * Zusätzlich gibt es RadioButtons (einzahlung, ausgabe), um den Eintrag als
 * Einzahlung oder Ausgabe zu kennzeichnen.
 * 
 * Als Quelle zu verwenden:
 * 	 - ComboBox, für benutzen habe ich von diesem Link:
 * 		 "https://docs.oracle.com/javafx" nach geschaut.
 * 	 - Error zu beheben habe ich mit "stackoverflow.com" am meisten nach geschaut manchmal mit "ChatGpt"
 * 
 * @author Aveen Al-Hadad
 * @version 23.01.2024
 */
public class HaushaltsbuchNeuerEintragAnsicht extends BorderPane {
	private DatePicker  datumEingabe;
	private ComboBox<String> kategorieEingabe;
	private ComboBox<String> kontoEingabe;
	private TextField beschreibungEingabe;
	private TextField betragEingabe;
	private Button eintragSpeichern;
	private Button abbrechen;
	private RadioButton einzahlung;
	private RadioButton ausgabe;
	private final ToggleGroup group = new ToggleGroup();

	/**
	 * In Konstruktore initialisiert die verschiedenen UI-Elemente wie Labels,
	 * Textfelder, Buttons und RadioButton-Gruppen. Sie ordnet diese Elemente in
	 * HBox- und VBox-Containern an und setzt sie in die entsprechenden Bereiche des
	 * BorderPane-Layouts.
	 * 
	 * @param datumEingabe
	 * @param kategorieEingabe
	 * @param kontoEingabe
	 * @param beschreibungEingabe
	 * @param betragEingabe
	 * @param eintragSpeichern
	 * @param abbrechen
	 * @param einzahlung
	 * @param ausgabe
	 */
	public HaushaltsbuchNeuerEintragAnsicht() {
//      Datum
		HBox behaelterFuerDatum = new HBox(); 
		behaelterFuerDatum.setId("behaelterFuerDatum");
		VBox eintraege = new VBox();
		Label datum = new Label("Datum: ");
		datum.setId("datum");
		datumEingabe = new DatePicker();
		datumEingabe.setPromptText("-Bitte eine Datum auswählen-");
		behaelterFuerDatum.getChildren().addAll(datum, datumEingabe);
		behaelterFuerDatum.setMargin(datumEingabe, new Insets(0, 0, 0, 50));
		
//      Kategorie
		HBox behaelterFuerKategorie = new HBox();
		Label kategorie = new Label("Kategorie: ");
		kategorie.setId("kategorie");
		kategorieEingabe = new ComboBox<>(FXCollections.observableArrayList(
                "Lebensmittel", "Kulturleben", "Geschenk", "Haushaltswaren","Auto-/Fahrkosten","Mode/Schönheitspflege","Gesundheit","Sonstige",
                "Kinderzuschlag","Einkommen","Kindergeld"));
		kategorieEingabe.setPromptText("-Bitte eine Kategorie auswählen-");
		
//		kategorieEingabe.setStyle("-fx-prompt-text-fill: lightgray;");
		behaelterFuerKategorie.getChildren().addAll(kategorie, kategorieEingabe);
		behaelterFuerKategorie.setMargin(kategorieEingabe, new Insets(0, 0, 0, 32));

//      Konto
		HBox behaelterFuerKonto = new HBox();
		Label konto = new Label("Konto: ");
		kategorie.setId("konto");
		kontoEingabe = new ComboBox<>(FXCollections.observableArrayList(
                "Karte", "Bargeld", "Konten"));
		kontoEingabe.setPromptText("-Bitte eine Konto auswählen-");
		
//		kategorieEingabe.setStyle("-fx-prompt-text-fill: lightgray;");
		behaelterFuerKonto.getChildren().addAll(konto, kontoEingabe);
		behaelterFuerKonto.setMargin(kontoEingabe, new Insets(0, 0, 0, 50));
		
//      Beschreibung
		HBox behaelterFuerBeschreibung = new HBox();
		Label beschreibung = new Label("Beschreibung: ");
		beschreibung.setId("beschreibung");
		beschreibungEingabe = new TextField();
		behaelterFuerBeschreibung.getChildren().addAll(beschreibung, beschreibungEingabe);
		behaelterFuerBeschreibung.setMargin(beschreibungEingabe, new Insets(0, 0, 0, 14));
		
//		Betrag
		HBox behaelterFuerBetrag = new HBox();
		Label betrag = new Label("Betrag: ");
		betragEingabe = new TextField();
		behaelterFuerBetrag.getChildren().addAll(betrag, betragEingabe);
		behaelterFuerBetrag.setMargin(betragEingabe, new Insets(0, 0, 0, 50));

//		Radio button with an empty string for its label
		einzahlung = new RadioButton("Einzahlung");
		ausgabe = new RadioButton("Ausgabe");
		einzahlung.setToggleGroup(group);
		ausgabe.setToggleGroup(group);
		ausgabe.setSelected(true);
		HBox radioKnoepfe = new HBox();
		radioKnoepfe.getChildren().addAll(ausgabe, einzahlung);

		eintraege.getChildren().addAll(behaelterFuerDatum, behaelterFuerKategorie, behaelterFuerKonto, behaelterFuerBeschreibung,
				behaelterFuerBetrag, radioKnoepfe);
		this.setCenter(eintraege);
		
//		Speichern und Abbrechen Knöpfe
		eintragSpeichern = new Button("Speichern");
		eintragSpeichern.setId("speichernKnopf");
		abbrechen = new Button("Abbrechen");
		abbrechen.setId("abbrechenKnopf");
		HBox behaelterFuerKnoepfe = new HBox();
		behaelterFuerKnoepfe.getChildren().addAll(eintragSpeichern, abbrechen);
		this.setBottom(behaelterFuerKnoepfe);
		behaelterFuerKnoepfe.setMargin(eintragSpeichern, new Insets(10, 10, 10, 10));
		behaelterFuerKnoepfe.setMargin(abbrechen, new Insets(10, 10, 10, 20));
	}

	/**
	 * Die Methode: überprüft, ob die eingegebenen Daten den erwarteten Formaten
	 * entsprechen. Wenn die Validierung fehlschlägt, wird eine Fehlermeldung
	 * angezeigt.
	 * 
	 * @param Sie nimmt ein TextField-Objekt als Parameter entgegen, validiert den
	 *            eingegebenen Wert in einem Textfeld.
	 * @return gibt den Wert von "passt" zurück, der angibt, ob die Eingabe gültig
	 *         war oder nicht.(true oder False)
	 */
	private boolean betragValidieren(TextField textField) {

		String betrag = textField.getText();

		// hier wird ein regulärer Ausdrucksmuster ("pattern") definiert
		String pattern = "\\d{1,5}(\\.\\d{5})*(,\\d{2})?";

//      hier wird überprüft dann, ob der eingegebene Betrag dem definierten Muster entspricht		
		boolean passt = betrag.matches(pattern);
		System.out.println(passt);

		if (!passt) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Eingabefehler Betrag");
			alert.setHeaderText("Ungültige Eingabe für Betrag");
			alert.setContentText("Es dürfen nur Zahlenwerte und zwei Nachkommastellen eingegeben werden.");
			alert.showAndWait();
		}

		return passt;
	}

	/**
	 * Die Methode: validiert ein ausgewähltes Datum in einem DatePicker-Objekt.
	 * 
	 * @param Sie nimmt ein DatePicker-Objekt als Parameter
	 * @return gibt einen booleschen Wert zurück
	 */
	private boolean datumValidieren(DatePicker  datePicker) {

//      Sie überprüft, ob das ausgewählte Datum null ist oder ob der String-Wert des ausgewählten Datums leer ist.
		boolean passt = datePicker.getValue() == null || datePicker.getValue().toString().isEmpty(); //true
		
//		Wenn "passt" true ist,
		if (passt) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Eingabefehler Datum");
			alert.setHeaderText(" Darf nicht leer sein");
			alert.setContentText("Bitte wählen Sie ein Datum aus.");
//          Das Alert-Fenster wird angezeigt und der Benutzer muss darauf warten, bis es geschlossen wird.			
			alert.showAndWait();
		}
		
		return passt;
	}

	/**
	 * Die Methode: validiert eine ComboBox mit Kategorien. Sie überprüft, ob der Wert der ComboBox null ist oder ob er leer ist.
	 * @param kategorieComboBox: Sie nimmt ein ComboBox-Objekt als Parameter
	 * @return passt: gibt einen booleschen Wert zurück
	 */
	private boolean kategorieValidieren(ComboBox<String> kategorieComboBox) {
//      Sie überprüft, ob der Wert der ComboBox null ist oder ob er leer ist.
		boolean passt = kategorieComboBox.getValue() == null || kategorieComboBox.getValue().isEmpty(); //true
		if (passt) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Eingabefehler Kategorie");
			alert.setHeaderText("Leere Kategorie!");
			alert.setContentText("Bitte wählen Sie ein Kategorie aus.");
			alert.showAndWait();
		}
		return passt;
	}
	
	/**
	 * Die Methode: validiert eine ComboBox mit Konto. Sie überprüft, ob der Wert der ComboBox null ist oder ob er leer ist.
	 * @param kontoComboBox: Sie nimmt ein ComboBox-Objekt als Parameter
	 * @return passt: gibt einen booleschen Wert zurück
	 */
	private boolean kontoValidieren(ComboBox<String> kontoComboBox) {
//      Sie überprüft, ob der Wert der ComboBox null ist oder ob er leer ist.
		boolean passt = kontoComboBox.getValue() == null || kontoComboBox.getValue().isEmpty(); //true
		if (passt) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Eingabefehler Konto");
			alert.setHeaderText("Leere Konto!");
			alert.setContentText("Bitte wählen Sie ein Konto aus.");
			alert.showAndWait();
		}
		return passt;
	}

	/**
	 * Die Methode: überprüft, ob die eingegebenen Daten den erwarteten Formaten
	 * entsprechen. Wenn die Validierung fehlschlägt, wird eine Fehlermeldung
	 * angezeigt.
	 *
	 * @param beschreibungField: Sie akzeptiert ein TextField-Objekt als Parameter
	 * @return passt: gibt einen booleschen Wert zurück.Wird auf "true" gesetzt,
	 *         wenn das Textfeld leer ist, andernfalls bleibt er "false".
	 */
	private boolean beschreibungValidieren(TextField beschreibungField) {
		boolean passt = beschreibungField.getText().isEmpty();
		if (passt) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Eingabefehler Beschreibung");
			alert.setHeaderText("Darf nicht leere sein!");
			alert.setContentText("Bitte Text eingeben");
			alert.showAndWait();
		}
		return passt;
	}

	/**
	 * Diese Methode: ruft diese Validierungsmethoden(betragValidieren,
	 * datumValidieren, kategorieValidieren, beschreibungValidieren) auf.
	 * 
	 * @param datum
	 * @param kategorie
	 * @param beschreibung
	 * @param betrag
	 * @return allesKlappt: gibt zurück, ob alle eingegebenen Daten gültig sind.
	 */
	public boolean datenValidieren(DatePicker  datum, ComboBox<String> kategorie, ComboBox<String> konto, TextField beschreibung, TextField betrag) {
		boolean istDatumUndkategorieOk = !datumValidieren(datum) && !kategorieValidieren(kategorie);
		boolean istKontoUndistDatumUndkategorieOk = istDatumUndkategorieOk	&& !kontoValidieren(konto);
		boolean istBeschreibungUndistKontoUndistDatumUndkategorieOk = istKontoUndistDatumUndkategorieOk && !beschreibungValidieren(beschreibung);
		boolean allesKlappt = istBeschreibungUndistKontoUndistDatumUndkategorieOk && betragValidieren(betrag);
		
		System.out.println(istDatumUndkategorieOk);
		System.out.println(istKontoUndistDatumUndkategorieOk);
		System.out.println(istBeschreibungUndistKontoUndistDatumUndkategorieOk);
		System.out.println(allesKlappt);
		return allesKlappt;
	}
		
	/**
	 * @return the datumEingabe
	 */
	public DatePicker getDatumEingabe() {
		return datumEingabe;
	}
	
	/**
	 * @return the kategorieEingabe
	 */
	public ComboBox<String> getKategorieEingabe() {
		return kategorieEingabe;
	}

	/**
	 * @return the kontoEingabe
	 */
	public ComboBox<String> getKontoEingabe() {
		return kontoEingabe;
	}

	/**
	 * @return the beschreibungEingabe
	 */
	public TextField getBeschreibungEingabe() {
		return beschreibungEingabe;
	}

	/**
	 * @return the betragEingabe
	 */
	public TextField getBetragEingabe() {
		return betragEingabe;
	}

	/**
	 * @return the eintragSpeichern
	 */
	public Button getEintragSpeichern() {
		return eintragSpeichern;
	}

	/**
	 * @return the abbrechen
	 */
	public Button getAbbrechen() {
		return abbrechen;
	}

	/**
	 * @return the einzahlung
	 */
	public RadioButton getEinzahlung() {
		return einzahlung;
	}

	/**
	 * @return the ausgabe
	 */
	public RadioButton getAusgabe() {
		return ausgabe;
	}

	/**
	 * @return the group
	 */
	public ToggleGroup getGroup() {
		return group;
	}

	/**
	 * Die Methode: setzt alle Textfelder auf den leeren String.
	 */
	public void setAllesNull() {
		datumEingabe.setValue(null);
		kategorieEingabe.setValue("");
		kontoEingabe.setValue("");
		beschreibungEingabe.setText("");
		betragEingabe.setText("");
	}

}