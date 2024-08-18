package de.haushaltsbuch.backend;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import de.haushaltsbuch.dtos.Haushaltsbuch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Die Klasse: implementiert das `HaushaltsbuchDao`-Interface und stellt Methoden zur Verfügung,
 *  um mit einer Datenbank zu interagieren.
 *  
 * @author Aveen Al-Hadad
 * @version 23.01.2024
 */
public class HaushaltsbuchDaoImpl implements HaushaltsbuchDao{
	/**
	 * Eine Instanzvariable "verbindung" vom Typ "Connection,
	 * 	die für die Verbindung zur Datenbank verwendet wird.
	 */
	Connection verbindung;
	
	/**
	 * Im Konstruktor wird die Instanzvariable "verbindung" mit einer Verbindung zur Datenbank initialisiert,
	 * 	 die über die Klasse "DbVerbindung" hergestellt wird.
	 * @param verbindung
	 */
	public HaushaltsbuchDaoImpl() {
		verbindung = DbVerbindung.getInstance();
	}

	/**
	 * Die Methode: wird verwendet,um vorbereitete Anweisungen für das Einfügen von Daten in die Datenbank auszuführen.
	 * 
	 * @param Sie nimmt SQL-Anweisungen,
	 * @param ein Datum,
	 * @param eine Kategorie,
	 * @param eine Konto,
	 * @param eine Beschreibung und
	 * @param einen Betrag entgegen und führt die Anweisung mit den entsprechenden Parametern aus.
	 */
	private void preparedStatment(String sql, LocalDate datum, String kategorie, String konto, String beschreibung, double betrag) {
		try {
			PreparedStatement datenSatz1 = verbindung.prepareStatement(sql);

            datenSatz1.setDate(1,Date.valueOf(datum));
            datenSatz1.setString(2, kategorie);
            datenSatz1.setString(3, konto);
            datenSatz1.setString(4, beschreibung);
            datenSatz1.setDouble(5, betrag);
            datenSatz1.executeUpdate();
			
		}catch(SQLException ausnahme) {
			ausnahme.printStackTrace();
		}
	}

	/**
	 * Die Methode: erstellt eine Tabelle namens "Datensatz" in der Datenbank,
	 *  falls sie nicht bereits existiert.
	 */
	@Override
	public void erstellenInDatenbankHaushaltsbuchTabelle() {
		try 
       	{
            String sql;
        	// Tabelle "Datensatz" anlegen, falls sie nicht existiert
        	sql = "CREATE TABLE IF NOT EXISTS Datensatz ("
        				+ "id INT PRIMARY KEY AUTO_INCREMENT,"
        				+ " datum DATE,"
        				+ " kategorie VARCHAR(255),"
        				+ " konto VARCHAR(255),"
        				+ " beschreibung VARCHAR(255),"
        				+ " einnahme DOUBLE,"
        				+ " ausgabe DOUBLE)";
        	Statement uebersetzer = verbindung.createStatement();
        	uebersetzer.execute(sql);
    	        	
       	}catch (SQLException ausnahme) {
       		ausnahme.printStackTrace();
       	}
		
	}
	
	/**
	 * Die Methode: fügt eine Auszahlung in die Datenbank ein, indem sie die Methode
	 *  "preparedStatment" mit der entsprechenden SQL-Anweisung aufruft.
	 */
	@Override
	public void auszahlungEinfuegen(LocalDate datum, String kategorie, String konto, String beschreibung, double betrag) {
		String sql = "INSERT INTO datensatz (datum, kategorie, konto, beschreibung, ausgabe) VALUES (?,?,?,?,?)";
        preparedStatment(sql, datum, kategorie, konto, beschreibung, betrag);
	}
	
	/**
	 * Die Methode: fügt eine Einzahlung in die Datenbank ein, indem sie ebenfalls die Methode
	 *  "preparedStatment" mit der entsprechenden SQL-Anweisung aufruft.
	 */
	@Override
	public void einzahlungEinfuegen(LocalDate datum, String kategorie, String konto, String beschreibung, double betrag) {
		String sql = "INSERT INTO datensatz (datum, kategorie, konto, beschreibung, einnahme) VALUES (?,?,?,?,?)";
        preparedStatment(sql, datum, kategorie, konto, beschreibung, betrag);
	}
		
	/**
	 * Die Methode: ruft alle Datensätze aus der Datenbank ab und gibt sie als ObservableList
	 *   von "Haushaltsbuch"-Objekten zurück.
	 *   
	 * Sie verwendet eine SQL-Anweisung, um alle Datensätze auszuwählen, und iteriert dann über das ResultSet,
	 *  um jedes Ergebnis in ein "Haushaltsbuch"-Objekt zu konvertieren.
	 * Je nachdem, ob der Betrag positiv oder negativ ist, wird der Betrag entsprechend gesetzt und ein formatierter
	 *   String wird erstellt. Die Methode gibt schließlich die ObservableList mit den konvertierten Datensätzen zurück.
	 */
	@Override
	public ObservableList<Haushaltsbuch> datenLiefern() {
		ObservableList<Haushaltsbuch> datensaetze = FXCollections.observableArrayList();
        String sql = "SELECT * FROM datensatz";

        try (Statement statement = verbindung.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
            	Haushaltsbuch datensatz = new Haushaltsbuch();
                datensatz.setDatum(resultSet.getDate(2).toLocalDate());
                datensatz.setKategorie(resultSet.getString(3));
                datensatz.setKonto(resultSet.getString(4));
                datensatz.setBeschreibung(resultSet.getString(5));
                if (resultSet.getDouble(6) == 0.0) {
                    datensatz.setBetrag(resultSet.getDouble(7) * -1);
                } else {
                    datensatz.setBetrag(resultSet.getDouble(6));
                }
                datensatz.setBetragString();
                datensaetze.add(datensatz);
            }
        } catch (SQLException ausnahme) {
            ausnahme.printStackTrace();
        }

        return datensaetze;
   }

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahmen und Ausgabe aus einer Datenbanktabelle. 
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen und Ausgabe aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag repräsentiert.
	 */
	@Override
	public double gesamtBetragBerechnen() {
		double gesamtBetrag = 0;
        String sql = "SELECT SUM(einnahme) - SUM(ausgabe)FROM datensatz";
        try (Statement statement = verbindung.createStatement()) {
        	 
        	ResultSet resultSet = statement.executeQuery(sql);
        	while (resultSet.next()) {
            gesamtBetrag = resultSet.getDouble(1);}
        } catch (SQLException ausnahme) {
            ausnahme.printStackTrace();
        }
        return gesamtBetrag;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahmen aus einer Datenbanktabelle. 
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen repräsentiert.
	 */
	@Override
	public double gesamtBetragEinnahme() {
		double gesamtBetragEinnahme = 0;
		String sql = "SELECT SUM(einnahme)FROM datensatz";
        try (Statement statement = verbindung.createStatement()){
        	
        	ResultSet resultSet = statement.executeQuery(sql);
        	while (resultSet.next()) {
        		gesamtBetragEinnahme = resultSet.getDouble(1);}
        } catch (SQLException ausnahme) {
            ausnahme.printStackTrace();
        }
        return gesamtBetragEinnahme;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgabe aus einer Datenbanktabelle. 
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgabe aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgabe repräsentiert.
	 */
	@Override
	public double gesamtBetragAusgabe() {
		double gesamtBetragAusgabe = 0;
		String sql = "SELECT SUM(ausgabe)FROM datensatz";
        try (Statement statement = verbindung.createStatement()) {
        	 
        	ResultSet resultSet = statement.executeQuery(sql);
        	while (resultSet.next()) {
        		gesamtBetragAusgabe = resultSet.getDouble(1);}
        } catch (SQLException ausnahme) {
            ausnahme.printStackTrace();
        }
        return gesamtBetragAusgabe;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für Lebensmittel in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "Lebensmittel" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für Lebensmittel repräsentiert.
	 */
	@Override
	public double gesamtLebensmittelInKategorie() {
		double gesamtLebensmittel = 0.0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Lebensmittel'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtLebensmittel = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtLebensmittel;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für Geschenk in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "Geschenk" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für Geschenk repräsentiert.
	 */
	@Override
	public double gesamtGeschenkInKategorie() {
		double gesamtGeschenk = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Geschenk'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtGeschenk = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtGeschenk;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für Gesundheit in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "Gesundheit" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für Gesundheit repräsentiert.
	 */
	@Override
	public double gesamtGesundheitInKategorie() {
		double gesamtGesundheit = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Gesundheit'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtGesundheit = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtGesundheit;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für Sonstige in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "Sonstige" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für Sonstige repräsentiert.
	 */
	@Override
	public double gesamtSonstigeInKategorie() {
		double gesamtSonstige = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Sonstige'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtSonstige = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtSonstige;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für Kulturleben in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "Kulturleben" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für Kulturleben repräsentiert.
	 */
	@Override
	public double gesamtKulturlebenInKategorie() {
		double gesamtKulturleben = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Kulturleben'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKulturleben = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtKulturleben;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für Haushaltswaren in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "Haushaltswaren" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für Haushaltswaren repräsentiert.
	 */
	@Override
	public double gesamtHaushaltswarenInKategorie() {
		double gesamtHaushaltswaren = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Haushaltswaren'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtHaushaltswaren = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtHaushaltswaren;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für ModeSchönheitspflege in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "ModeSchönheitspflege" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für ModeSchönheitspflege repräsentiert.
	 */
	@Override
	public double gesamtModeSchönheitspflegeInKategorie() {
		double gesamtModeSchönheitspflege = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Mode/Schönheitspflege'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtModeSchönheitspflege = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtModeSchönheitspflege;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgaben für AutoFahrkosten in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Kategorie "AutoFahrkosten" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für AutoFahrkosten repräsentiert.
	 */
	@Override
	public double gesamtAutoFahrkostenInKategorie() {
		double gesamtAutoFahrkosten = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE kategorie = 'Auto-/Fahrkosten'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtAutoFahrkosten = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtAutoFahrkosten;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Einkommen" in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen für die Kategorie "Einkommen" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Einkommen" repräsentiert.
	 */
	@Override
	public double gesamtEinkommenKategorie() {
		double gesamtEinkommen = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE kategorie = 'Einkommen'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtEinkommen = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtEinkommen;
	}
	
	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Kindergeld" in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen für die Kategorie "Kindergeld" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Kindergeld" repräsentiert.
	 */
	@Override
	public double gesamtKindergeldKategorie() {
		double gesamtKindergelg = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE kategorie = 'Kindergeld'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKindergelg = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtKindergelg;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Kinderzuschlag" in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen für die Kategorie "Kinderzuschlag" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Kinderzuschlag" repräsentiert.
	 */
	@Override
	public double gesamtKinderzuschlag() {
		double gesamtKinderzuschlag = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE kategorie = 'Kinderzuschlag'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKinderzuschlag = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtKinderzuschlag;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Sonstige" in einer bestimmten Kategorie aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen für die Kategorie "Sonstige" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Sonstige" repräsentiert.
	 */
	@Override
	public double gesamtSonstigeEinnahmesKategorie() {
		double gesamtSonstige = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE kategorie = 'Sonstige'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtSonstige = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtSonstige;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Bargeld" in einer bestimmten Konto aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahmen für die KOnto "Bargeld" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Bargeld" repräsentiert.
	 */
	@Override
	public double gesamtBargeldEinnahmenKonten() {
		double gesamtBargeldEinnahmen = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE konto = 'Bargeld'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtBargeldEinnahmen = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtBargeldEinnahmen;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgabe für "Bargeld" in einer bestimmten Konto aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die KOnto "Bargeld" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für "Bargeld" repräsentiert.
	 */
	@Override
	public double gesamtBargeldAusgabenKonten() {
		double gesamtBargeldAusgaben = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE konto = 'Bargeld'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtBargeldAusgaben = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtBargeldAusgaben;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Karte" in einer bestimmten Konto aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahme für die Konto "Karte" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Karte" repräsentiert.
	 */
	@Override
	public double gesamtKarteEinnahmenKonten() {
		double gesamtKarteEinnahmen = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE konto = 'Karte'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKarteEinnahmen = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtKarteEinnahmen;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgabe für "Karte" in einer bestimmten Konto aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Konto "Karte" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für "Karte" repräsentiert.
	 */
	@Override
	public double gesamtKarteAusgabenKonten() {
		double gesamtKarteAusgaben = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE konto = 'Karte'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKarteAusgaben = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtKarteAusgaben;
	}
	

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Ausgabe für "Konten" in einer bestimmten Konto aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Ausgaben für die Konto "Konten" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Ausgaben für "Konten" repräsentiert.
	 */
	@Override
	public double gesamtKontenAusgabenKonten() {
		double gesamtKontenAusgaben = 0;	   
		String sql = "SELECT SUM(ausgabe) FROM `datensatz` WHERE konto = 'Konten'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKontenAusgaben = resultSet.getDouble(1);}
       } catch (SQLException ausnahme) {
           ausnahme.printStackTrace();
       }
       return gesamtKontenAusgaben;
	}

	/**
	 * Die Methode: berechnet den Gesamtbetrag der Einnahme für "Konten" in einer bestimmten Konto aus einer Datenbanktabelle.
	 * Sie verwendet eine SQL-Abfrage, um die Summe der Einnahme für die Konto "Konten" aus der Tabelle "datensatz" zu ermitteln.
	 * Der Rückgabewert der Methode ist ein Wert vom Typ "double", der den Gesamtbetrag der Einnahmen für "Konten" repräsentiert.
	 */
	@Override
	public double gesamtKontenEinnahmenKonten() {
		double gesamtKontenEinnahmen = 0;	   
		String sql = "SELECT SUM(einnahme) FROM `datensatz` WHERE konto = 'Konten'";
		try (Statement statement = verbindung.createStatement()) {
      	 
       	ResultSet resultSet = statement.executeQuery(sql);
       	while (resultSet.next()) {
       		gesamtKontenEinnahmen = resultSet.getDouble(1);}
        } catch (SQLException ausnahme) {
            ausnahme.printStackTrace();
        }
        return gesamtKontenEinnahmen;
	}
			
}