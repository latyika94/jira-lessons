# lesson_1 - Script Runner segédlet

### Console

Groovy szkriptek futtatása alkalmazáson belül a szkript kontextusban elérhető bármely manager-t, service-t, JVM-ben elérhető objektumot felhasználva.

Naplózás
- log
- org.apache.log4j.Logger 
- groovy.util.logging.Log4j annotáció
- log szintek beállítása
- log fájl vizsgálat: atlassian\Application Data\Jira\log\atlassian-jira.log

Komponensek
- ComponentAccessor (Legjobb barátunk)

### Jobs

Groovy szkriptek futtatása alkalmazáson belül ütemezve

### Listener

Alkalmazás eseményekre feliratkozás, egyedi Groovy Script futtatás

### Fragments

Web UI felület kiegészítés:

- Web Item
- Web Panel

### Resources

Egyedi kapcsolatok definiálása szkriptben való felhasználásra.

### JQL Functions + Script Editor

Egyedi JQL függvények készítése

- JqlFunction
- JqlQueryFunction
- AbstractScriptedJqlFunction

### Eszközök

- Naplózás (log)
- Adatbázis lekérdezés futtatás
- ComponentAccessor
  - Beépített
  - getComponent(SearchService)
- @PluginModule

### Hasznos manager osztályok

- com.atlassian.sal.api.user.UserManager

- com.atlassian.activeobjects.spi.DataSourceProvider

- com.atlassian.plugins.osgi.javaconfig.OsgiServices

- com.atlassian.jira.util.I18nHelper
- com.atlassian.jira.security.JiraAuthenticationContext
- com.atlassian.jira.security.groups.GroupManager
- com.atlassian.jira.project.ProjectManager
- com.atlassian.jira.issue.CustomFieldManager
- com.atlassian.jira.issue.IssueManager
- com.atlassian.jira.plugin.webresource.JiraWebResourceManager
- com.atlassian.jira.security.PermissionManager
