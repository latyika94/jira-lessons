## 1. feladat
Készítsünk egy olyan JQL  függvényt, ami legyűjti azokat a feladatokat, ahol a projekt vezetője a megadott felhasználó.

**Paraméterek**
1. Felhasználó nevek (opcionális): Azokat a feladatok szűri le, ahol a projekt vezetője benne van a megadott felhasználók listájában

A felhasználó lista megadás szintaktikája a fejlesztőre van bízva, viszont amennyiben nem kerül megadásra felhasználó, akkor az JQL-t futtató felhasználót használjuk.

**A feladat megoldáshoz a `JqlFunction` implementációt használjuk.**

#### JQL futtatásra példa
```sql
project in projectLeadIs()
project in projectLeadIs("laszlo.majnar")
```

## 2. feladat
Készítsünk egy olyan JQL  függvényt, ami verzió (Version) típusra szűr és a verzió kiadási dátumára szűr a megadott paraméter alapján

**Paraméterek**
1. Dátum (Opcionális): A verzió kiadása a megadott dátum előtt van/volt. A dátumot "yyyy-MM-dd" formátumban kezeljük, pl.: "2023-10-10".

Amennyiben nem adunk meg dátumot, akkor a mai dátumot használja a JQL szűrő.
Figyeljünk arra, hogy a kiadás dátuma nem kötelezően kitöltendő a verzió objektumon, ezért lehet az értéke `null` is. Ha `null` az értéke a kiadás dátumának, akkor vegyük úgy, hogy a jövőben kerül kiadásra a feladat.
Azaz, csak akkor hozza a JQL az eredményhalmazban a kapcsolódó feladatokat, ha jövőbeli dátumot adunk meg.

**A feladat megoldáshoz a `JqlFunction` implementációt használjuk.**

#### JQL futtatásra példa
```sql
fixVersion in versionReleaseDateIsBefore()
fixVersion in versionReleaseDateIsBefore("2023-10-10")
```

## 3. feladat
Készítsünk egy olyan JQL függvényt, mely az issue-nak a kommentjeire szűr.

**Paraméterek**
1. Kommentek száma (Opcionális): Azokat a feladatokat kapjuk vissza, melyen legalább annyi komment van, amekkorát megadunk.
2. Felhasználó név (Opcionális): Azokat a feladatokat szűri le, ahol a feladaton van az adott felhasználónak kommentje (ApplicationUser.name mezőre szűrjünk)

Mind a két paraméter legyen opcionálisan megadandó, azaz lehessen szűrni a következő módszerek szerint:
1. Szűrés csak komment számra
2. Szűrés csak felhasználóra
3. Mind a két paraméter megadása esetén arra szűrjön, hogy az adott felhasználónak a megadott számú kommentje van legalább a feladaton
4. Ha egy paraméter sincs megadva, akkor dobjunk Exception-t

**A feladat megoldáshoz a `JqlFunction` implementációt használjuk.**

#### JQL futtatásra példa
```sql
issue in issueCommentCount(10)
issue in issueCommentCount(10, "laszlo.majnar")
issue in issueCommentCount(null, "laszlo.majnar")
issue in issueCommentCount() //Hiba
```

## 4. feladat (JQLQueryFunction)
Készítsünk egy olyan JQL függvényt, ami visszaadja azokat a feladatokat, amik a paraméterben megadott percnél korábban lettek módosítva és a `JQLQueryFunction` implementációt használja.

**Paraméterek**
1. Perc küszöbérték (kötelező paraméter), pl. 60 esetében azokat a feladatok hozza a szűrő, melyet már 1 órája nem módosítottak.

#### JQL futtatásra példa
```sql
issueFunctions in issueChangedBeforeInMinutes(60)
```

## 5. feladat (JQLQueryFunction - SQL)
Készítsünk egy olyan JQL függvényt, ami visszaadja azokat a feladatokat, melyeknek a projektje a megadott projekt kategóriában szerepel (SQL-lel szűrjünk).

**Paraméterek**
1. Projekt kategória (kötelező paraméter)

A működést egészítsük ki azzal, hogy hibaüzenetet kapjunk, ha egy olyan projekt kategóriát ad meg a felhasználó, ami nem létezik.
**Tipp**: A messageSet-hez hozzá tudunk adni saját hibaüzenetet.

SQL Segítség: [How to list all projects in a specific Project Category](https://confluence.atlassian.com/jirakb/how-to-list-all-projects-in-a-specific-project-category-781194603.html)

#### JQL futtatásra példa
```sql
issueFunctions in projectCategoryIs("Software Development")
```