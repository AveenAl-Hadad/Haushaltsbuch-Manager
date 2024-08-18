package de.haushaltsbuch.dtos;

import java.time.LocalDate;

/**
 * <pre>
 * In diesem Projekt sollen Haushaltsbuch-Obiekt erstellt und gespeichert 
 * 
 * Diese KLasse ist Template für Haushaltsbuch-Objekte.
 * Haushaltsbuch-Objekte übernehmen die Aufgabe eines Data Transfer Objektes: DTO 
 * 
 * Haushaltsbuch  Klasse erstellt Plain Old Java Objects: POJOs
 * 
 * </pre>
 * 
 * @author Aveen Al-Hadad
 * @version 22.01.2024
 * 
 */
public class Haushaltsbuch {

	/**
	 * Datums Einnahme oder Ausgabe des Haushaltsbuch eintrag
	 */
	private LocalDate datum;
	
	/**
	 * Kategories einnahme oder ausgabe des Haushaltsbuch eintrag
	 */
	private String kategorie;
	
	/**
	 * Konto Einnahme oder Ausgabe des Haushaltsbuch eintrag
	 */
	private String konto;
	
	/**
	 * Beschreibung Einnahme oder Ausgabe des Haushaltsbuch eintrag
	 */
	private String beschreibung;
	
	/**
	 * Betrag Einnahme oder Ausgabe des Haushaltsbuch eintrag
	 */
	private double betrag;
	
	/**
	 * Betrag Einnahme oder Ausgabe des Haushaltsbuch in String format
	 */
	private String betragToString;

	/**
	 * Diese Methode nimmt einen numerischen Wert "betrag" entgegen und konvertiert
	 * ihn in einen formatierten String mit zwei Dezimalstellen. Der formatierte
	 * String wird dann der Variablen "betragString" zugewiesen. Schließlich wird
	 * der Wert von "betragString" der Instanzvariable "string" zugewiesen.
	 *
	 */
	public void setBetragString() {
		String betragString = String.format("%.2f", betrag);
		this.betragToString = betragString;

	} 

	/**
	 * @return the datum
	 */
	public LocalDate getDatum() {
		return datum;
	}

	/**
	 * @return the kategorie
	 */
	public String getKategorie() {
		return kategorie;
	}

	/**
	 * @return the konto
	 */
	public String getKonto() {
		return konto;
	}

	/**
	 * @return the beschreibung
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * @return the betrag
	 */
	public double getBetrag() {
		return betrag;
	}

	/**
	 * @param datum the datum to set
	 */
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	/**
	 * @param kategorie the kategorie to set
	 */
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	/**
	 * @param konto the konto to set
	 */
	public void setKonto(String konto) {
		this.konto = konto;
	}
	/**
	 * @param beschreibung the beschreibung to set
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * @param betrag the betrag to set
	 */
	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

	@Override
	public String toString() {
		return datum + "\t" + kategorie + "\t" + konto + "\t" + beschreibung + "\t" + betragToString;
	}

	/**
	 * @return the betragToString
	 */
	public String getBetragToString() {
		return betragToString;
	}

	/**
	 * @param betragToString the betragToString to set
	 */
	public void setBetragToString(String betragToString) {
		this.betragToString = betragToString;
	}

}
