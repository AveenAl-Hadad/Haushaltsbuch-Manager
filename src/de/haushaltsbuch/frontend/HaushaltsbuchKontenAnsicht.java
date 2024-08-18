package de.haushaltsbuch.frontend;
import de.haushaltsbuch.backend.HaushaltsbuchDao;
import de.haushaltsbuch.backend.HaushaltsbuchDaoImpl;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Die Klasse: erstellt eine Benutzeroberfläche für die Kontenansicht eines Haushaltsbuchs.
 * Sie erbt von der Klasse "BorderPane" und enthält verschiedene Methoden zur Erstellung der UI-Elemente.
 * 
 * @author Aveen Al-Hadad
 * @version 29.01.2024
 */
public class HaushaltsbuchKontenAnsicht extends BorderPane {
	private HaushaltsbuchDao dao;
	private Label betragBargeldLabel;
	private Label betragKarteLabel;
	private Label betragKontenLabel;
	private Label saldoBetragLabel;
	private Label vermoegenBetragLabel;
	private Label kontenBetragLabel;
		
	/**
	 * /**
	 * Die KOnstruktor: wird ein neues "HaushaltsbuchDaoImpl"-Objekt erstellt der Methode "benutzeroberflaecheErstellen" aufgerufen.
	 * Diese DAO-Interface wird verwendet, um auf die Datenbank zuzugreifen und Informationen über die Konten abzurufen. 
	 */
	public HaushaltsbuchKontenAnsicht() {
		dao = new HaushaltsbuchDaoImpl();
		benutzeroberflaecheErstellen();   
	}

	/**
	 * Die Methode: erstellt die gesamte Benutzeroberfläche, indem sie die verschiedenen UI-Elemente in einem "BorderPane" anordnet.
	 * Sie besteht aus drei Teilen: 
	 * 		dem oberen Bereich wird durch den Aufruf der Methode "ueberschriftUndKontenErstellen" erstellt, 
	 * 		dem mittleren Bereich wird durch den Aufruf der Methode `createEintraege()` erstellt, 
	 * 		und dem unteren Bereich wird durch den Aufruf der Methode `createMenuKnopf()` erstellt.
	 */
	private void benutzeroberflaecheErstellen() {
		setTop(ueberschriftUndKontenErstellen());
		setCenter(eintraegeErstellen());
	}

	/**
	 * Diese Methode: ermöglicht es, die Überschrift und die Konten in einer gemeinsamen Box zu gruppieren und sie vertikal anzuordnen.
	 * 
	 * @return behaelterFuerUeberschriftUndKonten: ein VBox Type zurückgibt, die zwei Elemente enthält.
	 */
	private VBox ueberschriftUndKontenErstellen() {
		VBox behaelterFuerUeberschriftUndKonten = new VBox();
		behaelterFuerUeberschriftUndKonten.getChildren().addAll(ueberschriftErstellen(),kontenErstellen());
		return behaelterFuerUeberschriftUndKonten;
	}
	
	/**
	 * Die Methode: erstellt eine horizontale Box (HBox), die eine Überschrift für die Konten enthält.
	 * 
	 * @return behaelterFuerUeberschrift: ein HBox Type zurückgibt, die ein Label für Überschrift enthält.
	 */
	private HBox ueberschriftErstellen() {
		Label ueberschrift = new Label("Konten");
		ueberschrift.setId("ueberschrift");
		HBox behaelterFuerUeberschrift = new HBox(ueberschrift);
		behaelterFuerUeberschrift.setId("behaelterFuerUeberschrift");
		return behaelterFuerUeberschrift;
	}
	
	/**
	 * Diese Methode ermöglicht es, die Einträge für Vermögen, Schulden und Saldo in einer gemeinsamen horizontalen Box zu
	 *  gruppieren und sie nebeneinander anzuordnen. Und diese drei Einträge werden durch die Methoden "vermoegenEintragErstellen()",
	 *  "schuldenEintragErstellen()" und "saldoEintragErstellen()" erstellt.
	 *  
	 * @return behaelterFuerVermoegenUndSchuldenUndSaldo: ein HBox Type zurückgibt, die die drei Einträge enthält.
	 */
	private HBox kontenErstellen() {
		HBox behaelterFuerVermoegenUndSchuldenUndSaldo = new HBox();
		behaelterFuerVermoegenUndSchuldenUndSaldo.setSpacing(135);
		behaelterFuerVermoegenUndSchuldenUndSaldo.getChildren().addAll(
				vermoegenEintragErstellen(),
				schuldenEintragErstellen(),
				saldoEintragErstellen()	
				);
		behaelterFuerVermoegenUndSchuldenUndSaldo.setId("behaelterFuerUeberschrift");
		return behaelterFuerVermoegenUndSchuldenUndSaldo;
	}

	/**
	 * Die Methode: stellt mehrere Einträge in einer vertikalen Anordnung zu gruppieren und sie in der Benutzeroberfläche dar.
	 * Diese Einträge werden durch die Aufrufe der Methoden "bargeldEintragErstellen()", "karteEintragErstellen()" und
	 *  "kontenEintragErstellen()" erstellt. 
	 * 
	 * @return eintraege: ein VBox Type zurückgibt, die die drei Einträge enthält.
	 */
	private VBox eintraegeErstellen() {
		VBox eintraege = new VBox();
		eintraege.getChildren().addAll(
				bargeldEintragErstellen(),
				karteEintragErstellen(),
				kontenEintragErstellen()				
				);
		return eintraege;
	}

	/**
	 * Die Methode: erstellt eine horizontale Box (HBox), die Informationen zum Bargeld enthält. 
	 * Und stellt zwei Label eine für Überschrift und die zweite für den Betrag des Bargelds.
	 * 
	 * @return behaelterFuerBargeld: ein HBox Type zurückgibt, die die zwei Elemente "bargeld, betragBargeldLabel" enthält.
	 */
	private HBox bargeldEintragErstellen() {
		Label bargeld = new Label("Bargeld");
		double betragBargeld = dao.gesamtBargeldAusgabenKonten() + dao.gesamtBargeldEinnahmenKonten();
		betragBargeldLabel = new Label(betraegeToString(betragBargeld));
		betragBargeldLabel.setId("bargeldBetrag");
		HBox behaelterFuerBargeld = new HBox(bargeld, betragBargeldLabel);
		behaelterFuerBargeld.setSpacing(330);
		behaelterFuerBargeld.setId("behaelterFuerKonten");
		return behaelterFuerBargeld;
	}
	
	/**
	 * Die Methode: erstellt eine horizontale Box (HBox), die Informationen zum Karte enthält. 
	 * Und stellt zwei Label eine für Überschrift und die zweite für den Betrag des Karts.
	 *  
	 * @return behaelterFuerKarte: ein HBox Type zurückgibt, die die zwei Elemente "karte, betragKarteLabel" enthält.
	 */
	private HBox karteEintragErstellen() {
		Label karte = new Label("Karte");
		double betragKarte = dao.gesamtKarteAusgabenKonten() + dao.gesamtKarteEinnahmenKonten();
		betragKarteLabel = new Label(betraegeToString(betragKarte));
		betragKarteLabel.setId("karteBetrag");
		HBox behaelterFuerKarte = new HBox(karte, betragKarteLabel);
		behaelterFuerKarte.setSpacing(330);
		behaelterFuerKarte.setId("behaelterFuerKarte");
		return behaelterFuerKarte;
	}
	
	/**
	 * Die Methode: erstellt eine HBox für Informationen zu den Konten. Sie enthält ein Label mit dem Text Konten und
	 *  ein Label mit dem Betrag der Kontenausgaben und Konteneinnahmen. 
	 * Der Betrag wird ebenfalls ähnlich wie beim Bargeld berechnet und formatiert.
	 *  
	 * @return behaelterFuerKonten: ein HBox Type zurückgibt, die die zwei Elemente "konten,betragKontenLabel" enthält.
	 */
	private HBox kontenEintragErstellen() {
		Label konten = new Label("Konten");
		double betragKonten = dao.gesamtKontenAusgabenKonten() + dao.gesamtKontenEinnahmenKonten();
		betragKontenLabel =new Label(betraegeToString(betragKonten)); 
		betragKontenLabel.setId("kontenBetrag");
		HBox behaelterFuerKonten = new HBox(konten,betragKontenLabel);
		behaelterFuerKonten.setSpacing(320);
		behaelterFuerKonten.setId("behaelterFuerKonten");
		return behaelterFuerKonten;
	}
	
	/**
	 * Die Methode: konvertiert einen gegebenen Betrag in Form einer Dezimalzahl (double) in einen formatierten String.
	 *            
	 * @param betragBargeld: ein  Dezimalzahl Wert.
	 * @return betragBargeldToString: gibt ein String zurück.
	 */
	private String betraegeToString(double betragBargeld) {
		String betragBargeldToString = String.format("%.2f €", betragBargeld);
		return betragBargeldToString;
	}
	
	/**
	 * Die Methode: erstellt eine vertikale Box (VBox), die einen Eintrag für das Vermögen enthält.
	 * 				Zuerst wird ein Label mit dem Text"Vermgen" erstellt.
	 * 				Dann wird der Betrag des Vermögens berechnet, indem die Gesamtausgaben und Gesamteinnahmen 
	 * 				der Bargeldkonten abgerufen und addiert werden.
	 * 
     * @return behaelterFuerVermoegen: ein VBox Type zurückgibt, die die zwei Elemente "vermoegen, vermoegenBetragLabel" enthält. 
	 */
	private VBox vermoegenEintragErstellen() {
		Label vermoegen = new Label("Vermögen");
		double betragBargeld = dao.gesamtBargeldAusgabenKonten() + dao.gesamtBargeldEinnahmenKonten();
		vermoegenBetragLabel = new Label(betraegeToString(betragBargeld));
		vermoegenBetragLabel.setId("bargeldBetrag");
		VBox behaelterFuerVermoegen = new VBox(vermoegen, vermoegenBetragLabel);
		return behaelterFuerVermoegen;
		
	}
	
	/**
	 * Die Methode: erstellt eine vertikale Box (VBox), die einen Eintrag für das Schulden enthält.
	 * 				Zuerst wird ein Label mit dem Text"Schulden" erstellt.
	 * 				Dann wird der Betrag des Schulden berechnet, indem die Gesamtausgaben und Gesamteinnahmen 
	 * 				der gesamt Konten abgerufen und addiert werden.
	 * 
     * @return behaelterFuerVermoegen: ein VBox Type zurückgibt, die die zwei Elemente "schulden, kontenBetragLabel" enthält. 
	 */
	private VBox schuldenEintragErstellen() {
		Label schulden = new Label("Schulden");
		double betragKonten = dao.gesamtKontenAusgabenKonten() + dao.gesamtKontenEinnahmenKonten();
		kontenBetragLabel =new Label(betraegeToString(betragKonten)); 
		kontenBetragLabel.setId("kontenBetrag");
		VBox behaelterFuerSchulden = new VBox(schulden, kontenBetragLabel);
		return behaelterFuerSchulden;
			
	}
	
	/**
	 * Die Methode: erstellt eine vertikale Box (VBox), die einen Eintrag für das Saldo enthält.
	 * 				Zuerst wird ein Label mit dem Text"Saldo" erstellt.
	 * 				Dann wird der Betrag des saldo berechnet, indem die Gesamtausgaben und Gesamteinnahmen 
	 * 				der gesamt Berträge abgerufen und addiert werden.
	 * 
     * @return behaelterFuerVermoegen: ein VBox Type zurückgibt, die die zwei Elemente "saldo, saldoBetragLabel" enthält. 
	 */
	private VBox saldoEintragErstellen() {
		Label saldo = new Label("Saldo");
		double saldoBetrags = dao.gesamtBetragBerechnen();
		saldoBetragLabel = new Label(betraegeToString(saldoBetrags));
		saldoBetragLabel.setId("saldoBetrag");
		VBox behaelterFuerSaldo = new VBox(saldo, saldoBetragLabel);
		return behaelterFuerSaldo;
		
	}
	
}