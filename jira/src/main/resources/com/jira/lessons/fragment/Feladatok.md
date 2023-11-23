## ScriptRunner Listener feladatok

### 1. feladat
Az Event Listener fejezetben elkészített 2. feladatra épülve ([Feladatok.md](..%2Fevents%2FFeladatok.md)) hozzunk létre egy HTML web panel fragmentet.

A panel megjelenésének feltétele:
- A `totalWorklog` customfield értéke nagyobb, mint 0
- Vagy a `deletedWorklog` customfield értéke nagyobb, mint 0 az adott feladaton

Amennyiben valamelyik mező még nincs töltve (null értéke van), akkor a cella értékben a `Nincs töltve` felirat jelenjen meg.

**Fragment megjelenése (HTML táblázat)**

| Összes munkanapló száma | Törölt munkanaplók száma |
|-------------------------|--------------------------|
| ${totalWorklog}         | ${deletedWorklog}        |

**Hasznos komponens osztályok**
- com.atlassian.jira.issue.IssueManager
- com.atlassian.jira.issue.CustomFieldManager

**Az egyéb paraméterek megadása a készítőre van bízva!**