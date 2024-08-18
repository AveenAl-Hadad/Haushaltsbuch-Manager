package de.haushaltsbuch.backend;
import java.time.LocalDate;
import java.util.Set;

import de.haushaltsbuch.dtos.Haushaltsbuch;
import javafx.collections.ObservableList;

/**
 * Interface das das DAO-Design Pattern mit verschiedenen Methoden, die für den Zugriff auf
 *  ein Haushaltsbuch in einer Datenbank verwendet werden können.
 * Soll dieses Interface implemntieren(die hier vorgegebnen Methode implemntieren)
 *  
 *  @author Aveen Al-Hadad
 *  @version 24.01.2024
 */
public interface HaushaltsbuchDao {
	
	/**
     * diese Methode: wird eine Verbindung zur Datenbank hergestellt und anschließend ein SQL-Befehl ausgeführt,
     * 	um die Tabelle "Datensatz" anzulegen, falls sie noch nicht existiert. 
     *  Der SQL-Befehl definiert die Spalten der Tabelle, darunter
     * 	"id", "datum", "kategorie", "beschreibung", "einnahme" und "ausgabe". 
     * (zB. CREATE Table)
     */
	 void erstellenInDatenbankHaushaltsbuchTabelle();
	 
	/**
	 * Diese Methode fügt eine Ausgabe in das Haushaltsbuch Datenbank ein.
	 * (zB. INSERT INTO )
	 *  Sie erwartet Parameter wie:  
	 * @param das Datum der Ausgabe,
	 * @param die Kategorie (z.B. Lebensmittel, Schönheit usw.),
	 * @param eine Beschreibung
	 * @param Betrag der Ausgabe.
	 */
	void auszahlungEinfuegen(LocalDate datum , String kategorie, String konto, String beschreibung, double betrag);
	
	/**
	 * Diese Methode fügt eine Einzahlung in das Haushaltsbuch ein.
	 * (zB. INSERT INTO )
	 * Sie erwartet Parameter wie:
	 * @param das Datum der Einzahlung,
	 * @param die Kategorie,
	 * @param eine Beschreibung und
	 * @param den Betrag der Einzahlung.
	 */
    void einzahlungEinfuegen(LocalDate datum , String kategorie, String konto, String beschreibung, double betrag);

    /**
     * Diese Liste enthält alle Einträge im Haushaltsbuch, die aus der Datenbank abgerufen wurden.
     * Eine ObservableList ermöglicht es anderen Teilen des Codes, automatisch über Änderungen an der Liste informiert zu werden.
     * (zB. SELECT *)
     * 
     * @return die Liste alle Einträge im Haushaltsbuch
     */
    ObservableList<Haushaltsbuch> datenLiefern();

    /**
     * Diese Methode berechnet den Gesamtbetrag aller Ein- und Auszahlungen im Haushaltsbuch.
     * @return gesamBetrag: gibt ein double-Wert zurück.
     */
    double gesamtBetragBerechnen();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Einnahmen.
     * @return gibt einen Wert vom Typ "double" zurück.
     */
    double gesamtBetragEinnahme();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Ausgabe.
     * @return gibt einen Wert vom Typ "double" zurück.
     */
    double gesamtBetragAusgabe();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Lebensmittel in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für Lebensmittel vom Typ "double" zurück.
     */
    double gesamtLebensmittelInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Geschenk in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für Geschenke vom Typ "double" zurück.
     */
    double gesamtGeschenkInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Gesundheit in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für Gesundheit vom Typ "double" zurück.
     */
    double gesamtGesundheitInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Sonstige in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für Sonstige vom Typ "double" zurück.
     */
    double gesamtSonstigeInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Kulturleben in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für Kulturleben vom Typ "double" zurück.
     */
    double gesamtKulturlebenInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der Haushaltswaren in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für Haushaltswaren vom Typ "double" zurück.
     */
    double gesamtHaushaltswarenInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der ModeSchönheitspflege in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für ModeSchönheitspflege vom Typ "double" zurück.
     */
    double gesamtModeSchönheitspflegeInKategorie();
    
    /**
     * Die Methode: berechnet den Gesamtbetrag der AutoFahrkosten in einer bestimmten Kategorie.
     * @return gibt eine gesamt Ausgabe für AutoFahrkosten vom Typ "double" zurück.
     */
    double gesamtAutoFahrkostenInKategorie();
    
    /**
     * Die Methode: berechnet die Gesamtsumme der Einkommen in der Kategorie.     * 
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Einkommen" repräsentiert.
     */
    double gesamtEinkommenKategorie();
    
    /**
     * Die Methode: berechnet die Gesamtsumme des Kindergeldes in der Kategorie.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Kindergeld" repräsentiert.
     */
    double gesamtKindergeldKategorie();
    
    /**
     * Die Methode: berechnet die Gesamtsumme des Kinderzuschlags.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Kinderzuschlag" repräsentiert.
     */
    double gesamtKinderzuschlag();
    
    /**
     * Die Methode: berechnet die Gesamtsumme der sonstigen Einnahmen in der Kategorie.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Sonstige" repräsentiert.
     */
    double gesamtSonstigeEinnahmesKategorie();
    
    /**
     * Die Methode: berechnet die Gesamtsumme der Bargeld-Einnahmen in den Konten.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Bargeld" repräsentiert.
     */
    double gesamtBargeldEinnahmenKonten();
    
    /**
     * Die Methode: berechnet die Gesamtsumme der Bargeld-Ausgaben in den Konten.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für "Bargeld" repräsentiert.
     */
    double gesamtBargeldAusgabenKonten();
    
    /**
     * Die Methode: berechnet die Gesamtsumme der Karten-Einnahmen in den Konten
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Karte" repräsentiert.
     */
    double gesamtKarteEinnahmenKonten();
    
    /**
     * Die Methode:  berechnet die Gesamtsumme der Karten-Ausgaben in den Konten.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für "Karte" repräsentiert.
     */
    double gesamtKarteAusgabenKonten();

    /**
     * Die Methode: berechnet die Gesamtsumme der Ausgaben in den Konten.
     * @return ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für "Konten" repräsentiert.
     */
    double gesamtKontenAusgabenKonten();
    
    /**
     * Die Methode: berechnet die Gesamtsumme der Einnahmen in den Konten.
     * @return  ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Konten" repräsentiert.
     */
    double gesamtKontenEinnahmenKonten();

}