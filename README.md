# 💰 Haushaltsbuch Manager

Willkommen zum **Haushaltsbuch Manager** – einer JavaFX-Desktop-Anwendung zur Verwaltung deiner persönlichen Finanzen.  
Die Anwendung nutzt **JavaFX** für die Benutzeroberfläche, **MySQL** (XAMPP) für die Datenbank und **Maven** als Build-Tool.

---

## 🚀 Funktionen

- ✅ Einnahmen und Ausgaben erfassen
- ✅ Kategorien verwalten
- ✅ Budgetübersicht & Verwaltung
- ✅ Verbindung zu lokaler MySQL-Datenbank (XAMPP)
- ✅ JavaFX-GUI mit SceneBuilder-Unterstützung
- ✅ Maven-Projektstruktur

---

## 🛠 Verwendete Technologien

| Technologie     | Beschreibung                         |
|----------------|--------------------------------------|
| Java           | Programmiersprache                   |
| JavaFX         | GUI-Framework                        |
| MySQL (XAMPP)  | Datenbank für Speicherung            |
| JDBC           | Verbindung zu MySQL                  |
| Maven          | Projekt- und Abhängigkeitsmanagement |
| SceneBuilder   | Visuelles UI-Tool (optional)         |

---

## 🧑‍💻 Voraussetzungen

- XAMPP mit laufendem MySQL-Server
- Datenbank `haushaltsbuch` (oder dein Name)
- Tabelle(n) wie z. B. `ausgaben`, `einnahmen`, `kategorien`
- IntelliJ IDEA oder Eclipse
- Java 17+
- Maven installiert oder IDE mit Maven-Unterstützung

---

## ⚙️ Datenbankverbindung

Stelle sicher, dass du eine Datenbank in XAMPP wie folgt eingerichtet hast:

### Beispiel:

```sql
CREATE DATABASE haushaltsbuch;

CREATE TABLE ausgaben (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kategorie VARCHAR(50),
    betrag DOUBLE,
    datum DATE,
    bemerkung TEXT
);
