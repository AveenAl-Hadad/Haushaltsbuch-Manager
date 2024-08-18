package de.haushaltsbuch.frontend;
import de.haushaltsbuch.backend.HaushaltsbuchDao;
import de.haushaltsbuch.backend.HaushaltsbuchDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Die Klasse: ist eine Ansichtsklasse, die eine Statistik der Ausgaben in verschiedenen Kategorien eines Haushaltsbuchs darstellt.
 * Sie erweitert die Klasse "VBox" und enthält verschiedene Instanzvariablen, Elemente wie Buttons, ein PieChart-Diagramm und Labels und Methoden.
 *  
 * Als Quelle zu verwenden:
 * 	 "PieChart"-Object in JavaFX benutzen habe ich von diesem Link: "https://docs.oracle.com/javafx/2/charts/pie-chart.htm" nach geschaut.
 * 	  Error zu beheben habe ich mit "stackoverflow.com" am meisten nach geschaut manchmal mit "ChatGpt"
 *     
 * @author Aveen Al-Hadad
 * @version 26.01.2024
 */
public class HaushaltsbuchKategorienAusgabenStatistikAnsicht extends VBox{

	private HaushaltsbuchDao dao;
	private PieChart pieChart;
	private Button ausgaben;
	private Button einnahmen;
	private HBox behaelterFuerKnoepfe;
		
	/**
	 * Die Konstruktor: initialisiert die Instanzvariablen und ruft die Methode "BenutzeroberflaecheBauen()" auf,
	 *  um die Benutzeroberfläche zu erstellen.
	 * Diese DAO-Interface wird verwendet, um auf die Datenbank zuzugreifen und Informationen abzurufen. 
	 */
	public HaushaltsbuchKategorienAusgabenStatistikAnsicht() {
		 dao = new HaushaltsbuchDaoImpl();
		 BenutzeroberflaecheBauen();
	}
	
	/**
	 * Die Methode: ruft die Methoden "schaltflächenInitialisieren(), PieChartInitialisieren()" und "ansichtInitialisieren()" auf,
	 *  um die Benutzeroberfläche zu erstellen.
	 */
	private void BenutzeroberflaecheBauen() {
		schaltflächenInitialisieren();
		PieChartInitialisieren();
		ansichtInitialisieren();		
	}
	
	/**
	 * Die Methode: erstellt die Schaltflächen für Ausgaben und Einnahmen und fügt sie der "behaelterFuerKnoepfe"-HBox hinzu.
	 */
	private void schaltflächenInitialisieren() {
	ausgaben = new Button("Ausgaben " + dao.gesamtBetragAusgabe() + " €");
	einnahmen = new Button("Einnahmen " + dao.gesamtBetragEinnahme() + " €");
	behaelterFuerKnoepfe = new HBox(ausgaben, einnahmen);
	behaelterFuerKnoepfe.setSpacing(200);
	}
	
	/**
	 * Die Methode: erstellt ein Tortendiagramm "PieChart" für die Kategorienausgabenstatistik.
	 * Es verwendet die Methode "chartDataErstellen()" und "inProzentRechnen()", um die Daten für
	 *  das Diagramm zu erstellen.
	 */
	private void PieChartInitialisieren() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				chartDataErstellen("Lebensmittel", dao.gesamtLebensmittelInKategorie()),
				chartDataErstellen("Geschenk", dao.gesamtGeschenkInKategorie()),
				chartDataErstellen("Gesundheit", dao.gesamtGesundheitInKategorie()),
				chartDataErstellen("Sonstige", dao.gesamtSonstigeInKategorie()),
				chartDataErstellen("Kulturleben", dao.gesamtKulturlebenInKategorie()),
				chartDataErstellen("Haushaltswaren", dao.gesamtHaushaltswarenInKategorie()),
				chartDataErstellen("Mode/Schönheitspflege", dao.gesamtModeSchönheitspflegeInKategorie()),
				chartDataErstellen("Auto-/Fahrkosten", dao.gesamtAutoFahrkostenInKategorie())
				);

		pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Ausgaben Kategorien-Statistik");
		}
	
	/**
	 * Die Methode: erstellt ein "PieChart.Data"-Objekt für eine bestimmte Kategorie und den zugehörigen Wert.
	 * Sie verwendet die Methode "inProzentRechnen()", um den prozentualen Anteil des Werts zu berechnen.
	 * 
	 * @param kategorie: ein Zeichenkette Typ
	 * @param value: ist ein double Typ
	 * @return PieChart.Data: gibt ein bestimmte Kategorie mit zughörigen Wert zurück.
	 */
	private PieChart.Data chartDataErstellen(String kategorie, double value) {
		String prozentsatz = inProzentRechnen(value);
		return new PieChart.Data(kategorie + " " + prozentsatz + "%", value);
	}
	
	/**
	 * Die Methode: berechnet den prozentualen Anteil eines bestimmten Werts im Verhältnis zur Gesamtausgabe.
	 *  
	 * @param gesamtEinKategorie: ist ein decimal Type Wert.
	 * @return ProzenToString: gibt den prozentualen Anteil als formatierte Zeichenkette zurück.
	 */
	public String inProzentRechnen(double gesamtEinKategorie) {
		double gesamtAusgabe = dao.gesamtBetragAusgabe();
		double prozent = (gesamtEinKategorie / gesamtAusgabe) * 100;
		String ProzenToString = String.format("%.2f", prozent);
		return ProzenToString;
	}
	
	/**
	 * Die Methode: erstellt eine "VBox", die das Tortendiagramm und den Menüknopf enthält.
	 * Sie fügt auch die "behaelterFuerKnoepfe"-HBox und die "VBox" zur Ansicht hinzu.
	 */
	private void ansichtInitialisieren() {
		VBox behaelterFuerPieChartUndMenueKnoepfe = new VBox(pieChart);
		this.getChildren().addAll(behaelterFuerKnoepfe, behaelterFuerPieChartUndMenueKnoepfe);
	}

	/**
    * @return the pieChart
    */ 
	public PieChart getPieChart() {
		return pieChart;
	}

	/**
	 * @return the ausgaben
	 */
	public Button getAusgaben() {
		return ausgaben;
	}

	/**
	 * @return the einnahmen
	 */
	public Button getEinnahmen() {
		return einnahmen;
	}

}
