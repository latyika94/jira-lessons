## ScriptRunner Fragment feladatok

Az Event Listener fejezetben elkészített 2. feladatra épülve ([Feladatok.md](..%2Fevents%2FFeladatok.md)) hozzunk létre egy HTML web panel fragmentet.

A panel megjelenésének feltétele:
- A `totalWorklog` customfield értéke nagyobb, mint 0
- Vagy a `deletedWorklog` customfield értéke nagyobb, mint 0 az adott feladaton

Amennyiben valamelyik mező még nincs töltve (null értéke van), akkor a cella értékben a `Nincs töltve` felirat jelenjen meg.

**Fragment megjelenése (AUI stílusú HTML táblázat)**

| Adat                                         | Érték             |
|----------------------------------------------|-------------------|
| Összes munkanapló száma (customfield érték)  | ${totalWorklog}   |
| Törölt munkanaplók száma (customfield érték) | ${deletedWorklog} |

Amennyiben a panelt megjelenítő felhasználónak vannak munkanapló rögzítései az adott feladaton, akkor jelenítsük meg annak darabszámát is a fenti táblázatban (3. sorban). Egyébként ne jelenjen meg a sor.
- Adat: `Bejelentkezett felhasználó munkanaplói`
- Érték: Például. `2`

A web panel tartalmazzon extra információkat is abban az esetben, ha a felhasználó tagja a "worklog-stat-maintainers" csoportnak (létre kell hoznil).

**Extra tartalom:**
- A táblázat alatt (nem annak részeként) jelenítsük meg "X óra Y perc" alakban az eddig összes ráfordítást (munkanapló órák) a feladatról.
A másodpercekkel nem kell foglalkozni: 1 óra 1 perc 20 másodperc is "1 óra 1 perc"-ként jelenjen meg.
- Jelenítsük meg az első és az utolsó munkanapló létrehozásának idejét "yyyy-MM-dd HH:mm:ss" alakban.

A statisztika alatt jelenítsünk meg egy gombot, ami elrejti/megjeleníti az extra információkat a képernyőn.

**Az extra adatok megjelenítési módja a fejlesztőre van bízva!**

**Az ScriptRunner objektumok egyéb paramétereinek megadása a készítőre van bízva.**
**A panel adatainak nem fontos frissülnie, ha módosulnak a munkanaplók.**

### Feladat megoldása

- [resource-ok](solutions/resources)
- [Fragment Condition](solutions/task1Condition.groovy)
- [Fragment Provider](solutions/task1Provider.groovy)