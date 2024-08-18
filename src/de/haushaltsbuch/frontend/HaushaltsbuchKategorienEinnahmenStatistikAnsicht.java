package de.haushaltsbuch.frontend;
import de.haushaltsbuch.backend.HaushaltsbuchDao;
import de.haushaltsbuch.backend.HaushaltsbuchDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * Die Klasse: ist eine Ansichtsklasse, die eine Statistik der Einnahmen in verschiedenen Kategorien eines Haushaltsbuchs darstellt.
 * Sie erweitert die Klasse "VBox" und enthält verschiedene Instanzvariablen, Elemente wie Buttons, ein PieChart-Diagramm und Labels und Methoden.
 *  
 * @author Aveen Al-Hadad
 * @version 26.01.2024
 */
public class HaushaltsbuchKategorienEinnahmenStatistikAnsicht extends VBox{

//	"dao": Ein Objekt vom Typ "HaushaltsbuchDao", das für den Zugriff auf die Datenbank verwendet wird.
	private HaushaltsbuchDao dao;
	
//	"pieChart": Ein Objekt vom Typ "PieChart", das das Diagramm zur Darstellung der Statistik enthält.	
	private PieChart pieChart;
	private Button zurueckKnopf;

	/**
	 * Die Konstruktor: wird ein neues "HaushaltsbuchDaoImpl"-Objekt erstellt und der Methode "PieChartInitialisieren()"
	 *  und "ansichtInitialisieren()" aufgerufen. 
	 *  Diese DAO-Interface wird verwendet, um auf die Datenbank zuzugreifen und Informationen abzurufen. 
	 */
	public HaushaltsbuchKategorienEinnahmenStatistikAnsicht(){
		dao = new HaushaltsbuchDaoImpl();
		PieChartInitialisieren();
		ansichtInitialisieren();
		 
	}
	
	/**
	 * Die Methode: initialisiert das "PieChart"-Objekt mit den Daten der Einnahmenkategorien. 
	 * Dazu werden die Methoden "chartDataErstellen()" und "inProzentRechnen()" verwendet, um die Daten für das
	 *  Diagramm zu erstellen.
	 */
	private void PieChartInitialisieren() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				chartDataErstellen("Einkommen",  dao.gesamtEinkommenKategorie()),
				chartDataErstellen("Kindergeld", dao.gesamtKindergeldKategorie()),
				chartDataErstellen("Sonstige", dao.gesamtKinderzuschlag()),
				chartDataErstellen("Kinderzuschlag", dao.gesamtSonstigeEinnahmesKategorie())
				);
		pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Einnahmen Kategorien-Statistik");
	}

	/**
	 * Die Methode: erstellt ein "PieChart.Data"-Objekt für eine bestimmte Kategorie und den zugehörigen Wert.
	 * Sie verwendet die Methode "inProzentRechnen()", um den prozentualen Anteil des Werts zu berechnen.
	 * 
	 * @param kategorie: ein Zeichenkette Typ
	 * @param value: ist ein double Typ
	 * @return PieChart.Data: gibt ein bestimmte Kategorie mit zughörigen Wert zurück.
	 */
	private PieChart.Data chartDataErstellen(String category, double value) {
		String prozentsatz = inProzentRechnen(value);
		return new PieChart.Data(category + " " + prozentsatz + "%", value);
	}

	/**
	 * Die Methode: berechnet den prozentualen Anteil eines bestimmten Werts im Verhältnis zur Gesamtausgabe.
	 *  
	 * @param gesamtEinKategorie: ist ein decimal Type Wert.
	 * @return ProzenToString: gibt den prozentualen Anteil als formatierte Zeichenkette zurück.
	 */
	public String inProzentRechnen(double gesamtEinKategorie) {
		double gesamtEinnahmen = dao.gesamtBetragAusgabe();
		double prozent = (gesamtEinKategorie / gesamtEinnahmen) * 100;
		String ProzenToString = String.format("%.2f", prozent);
		return ProzenToString;
	}
		
	/**
	 * Die Methode: erstellt eine "VBox", die das Tortendiagramm und den Menüknopf enthält.
	 * Sie fügt auch die "behaelterFuerKnoepfe"-HBox und die "VBox" zur Ansicht hinzu.
	 */
	private void ansichtInitialisieren() {
		VBox behaelterFuerPieChartzurueckKnoepfe = new VBox(pieChart,zurueckKnopfErstellen());
		behaelterFuerPieChartzurueckKnoepfe.setSpacing(200);
		this.getChildren().addAll(behaelterFuerPieChartzurueckKnoepfe);
	}
	
	/**
	 * Die Methode:` erstellt eine VBox (vertikales Layout) und fügt einen Button hinzu.
	 * Der Button wird in die VBox platziert. Am Ende wird die `VBox` zurückgegeben.
	 * 
	 * @return behaelterFuerNeuerEintrag: gibt den Typ der VBox zurück, die die Schaltfläche Zurück Knopf enthält.
	 */
	private VBox zurueckKnopfErstellen() {
		zurueckKnopf = new Button();
		zurueckKnopf.setId("zurueckKnopf");
		VBox behaelterFuerNeuerEintrag = new VBox(zurueckKnopf);
		behaelterFuerNeuerEintrag.setId("behaelterFuerNeuerEintrag");
		behaelterFuerNeuerEintrag.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(behaelterFuerNeuerEintrag);
		return behaelterFuerNeuerEintrag;
	}

	/**
	 * @return the pieChart
	 */
	public PieChart getPieChart() {
		return pieChart;
	}

	/**
	 * @return the zurueckKnopf
	 */
	public Button getZurueckKnopf() {
		return zurueckKnopf;
	}

}
