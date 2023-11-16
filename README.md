# Jira plugin fejlesztési leckék

## Atlassian Jira plugin fejlesztési eszközök

### Telepítendő eszközök

- Atlassian Plugin SDK: [Set up the Atlassian Plugin SDK and build a project](https://developer.atlassian.com/server/framework/atlassian-sdk/set-up-the-atlassian-plugin-sdk-and-build-a-project/)
  - Java JDK 8+ telepítés
  - Atlassian Plugin SDK telepítés
- IDE, text editor telepítés
  - Backend (preferált): [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    - [Jetbrains Toolbox](https://www.jetbrains.com/lp/toolbox/)
    - **Pluginek**
        - Resource Bundle Editor
  - Frontend (preferált): [Visual Studio Code](https://code.visualstudio.com/)
    - **Javasolt Pluginek**
        - Auto Close Tag
        - Auto Rename Tag
        - JavaScript (ES6) code snippets
        - json
        - npm Intellisense
        - Prettier
        - Sass
        - SCSS
        - XML
- Git

### Hasznos oldalak - Atlassian

- [Atlas CLI](https://developer.atlassian.com/server/framework/atlassian-sdk/automatic-plugin-reinstallation-with-quickreload/): Atlassian Plugin SDK konzol parancsok

  - Főként használt parancsok
    - `atlas-create-jira-plugin` Jira plugin skeleton Maven projekt inicializálás
    - `atlas-clean` Build folyamat során készült állományok törlése
    - `atlas-package` Plugin buildelés és csomagolás .jar fájlba
    - `atlas-install-plugin` Plugin telepítés a cél Jira alkalmazásra
    - `atlas-run` Plugin pom.xml alapján localhost development Jira inicializálás
    - `atlas-run-standalone` Plugin függetlenül localhost dev környezet inicializálás

- [Atlassian Developer Server Guides](https://developer.atlassian.com/server/jira/platform/getting-started/)
- Jira Server

  - [REST API](https://docs.atlassian.com/software/jira/docs/api/REST/9.11.0/)
  - [Java API](https://docs.atlassian.com/software/jira/docs/api/9.11.0/)

- Jira Software Server

  - [REST API](https://docs.atlassian.com/jira-software/REST/9.11.0/)

- [Atlassian Developer Community](https://community.developer.atlassian.com/)
- [Atlassian Community](https://community.atlassian.com/)
- [ScriptRunner for Jira](https://docs.adaptavist.com/sr4js/latest)

#### Backend

- [Active Objects](https://developer.atlassian.com/server/framework/atlassian-sdk/active-objects/) ORM réteg Atlassian alkalmazásokhoz
- [Többnyelvűsítés](https://developer.atlassian.com/server/framework/atlassian-sdk/internationalising-your-plugin/) i18n support

#### Frontend

### Hasznos oldalak - ScriptRunner

- [Groovy Language Specification](https://groovy-lang.org/documentation.html#languagespecification)

### Hasznos oldalak - Egyéb

- [Getting started with Kotlin/JVM](https://kotlinlang.org/docs/jvm-get-started.html#what-s-next)

# Segédletek

## [lesson_1 - Script Runner](lesson_1/README.md)

## [lesson_2, lesson_n - Jira plugin fejlesztési alapok](lesson_2/README.md)

## [lesson_n+1 - Saját Jira plugin service-ek használata ScriptRunner-ben](lesson_n1/README.md)
