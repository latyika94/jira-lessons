## ScriptRunner Listener feladatok

### 1. feladat
Feladatunk, hogy az issue-n megjelenő komment szövegét módosítsuk a következő esetben:

- Ha a komment tulajdonosa (author) a feladat projektjének vezetője (project lead), akkor a komment szövegét módosítani kell a lenti minta szerint.

**Minta**
```
FONTOS! Projekt vezetői megjegyzés!
${eredeti komment a projekt vezetőtől}
```
A `FONTOS!` szöveg legyen félkövér. Ennek formátumát a megjegyzés mező "szöveg" nézetében megtekinthető.

**Hasznos komponens osztályok**
- com.atlassian.jira.issue.comments.CommentManager

### 2. feladat
Hozzunk létre két új customfield-et, de csak a C1SP projekt feladataira, mely a munkanapló változásokat számolja a feladaton. 
1. Mutatja a feladaton lévő összes munkanapló számát (totalWorklog)
2. Mutatja az eddig törölt munkanaplók számát (deletedWorklog)

Hozzuk létre a ScriptRunner Listenert, mely(ek) töltik a fenti két mezőt a munkanaplók módosítása esetén.

**Segítség**: Ne "IssueEvent"-re készítsük a listener-t, amit a példa szkriptben láthattunk.

**Hasznos komponens osztályok**
- com.atlassian.jira.issue.IssueManager
- com.atlassian.jira.issue.CustomFieldManager
- com.atlassian.jira.issue.index.IssueIndexingService

**Ne feledjük, hogy issue customfield értékének módosítása során érdemes újraindexelni az érintett feladatot, amennyiben nem a HAPI-t használjuk!**