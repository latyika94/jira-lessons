package com.onresolve.jira.groovy.jql

import com.atlassian.jira.JiraDataType
import com.atlassian.jira.JiraDataTypes
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.jql.operand.QueryLiteral
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.permission.ProjectPermissions
import com.atlassian.jira.project.version.VersionManager
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand
import com.onresolve.jira.groovy.jql.AbstractScriptedJqlFunction
import com.onresolve.jira.groovy.jql.JqlFunction

/**
 * Az alábbi JQL függvény azokra az feladatokra szűr, melyen van olyan projekt verzió beállítva, ami tartalmazza a megadott értéket.
 * A Custom JQL a [com.onresolve.jira.groovy.jql.JqlFunction]-t implementálja a [JqlQueryFunction] helyett, mivel nem Issue-ra, hanem Version mezőre szűrünk.
 * Ebben az esetben a [QueryLiteral] listát kell visszaadni, lásd [getValues] függvény
 *
 * Példák:
 * - fixVersion in fixVersionContains("v1.0")
 * - fixVersion in fixVersionContains("V1.0", "true")
 */
class ProjectVersionContainsJql extends AbstractScriptedJqlFunction implements JqlFunction {

    def versionManager = ComponentAccessor.getComponent(VersionManager)
    def permissionManager = ComponentAccessor.getPermissionManager()

    //Kötelező
    //Saját JQL függvényünk leírása
    @Override
    String getDescription() {
        "A verzió tartalmazza a megadott értéket"
    }

    //Kötelező
    //Saját JQL függvényünk paraméterei
    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Szűrendő érték (tartalmazás)",
                        optional   : false
                ] as Map,
                [
                        description: "Szűrendő értéket kis-nagy betű érzékenyen tartalmazza? (true/false)",
                        optional   : true
                ] as Map
        ]
    }

    //Kötelező
    //Saját JQL függvényünk neve
    @Override
    String getFunctionName() {
        return "fixVersionContains"
    }

    //A QueryLiteral visszaadott értékei (id - azonosítói) Version típusú objektumhoz tartozik
    @Override
    JiraDataType getDataType() {
        JiraDataTypes.VERSION
    }

    @Override
    List<QueryLiteral> getValues(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {
        /* JQL paraméterek (argumentumok) lekérése, beállítása */
        def containsValue = operand.args.first()
        def containsCaseSensitive = false
        if(operand.args.size() >= 2) {
            containsCaseSensitive =  operand.args.get(1).toLowerCase() == "true"
        }

        /* Leszűrjük az argumentumok alapján az összes olyan verziót, melynek neve tartlamazza a keresett kifejezést*/
        versionManager.allVersions.findAll {
            if(containsCaseSensitive) {
                it.name.contains(containsValue)
            } else {
                it.name.containsIgnoreCase(containsValue)
            }
        }.findAll {
            //Leszürjük, hogy a végrehajtó felhasználónak van-e hozzáférése a projekthez
            queryCreationContext.securityOverriden || permissionManager.hasPermission(ProjectPermissions.BROWSE_PROJECTS, it.project, queryCreationContext.applicationUser) // <4>
        }.collect {
            //Elkészítjük a QueryLiteral-okat a Version objektum "id" mezőjéből.
            new QueryLiteral(operand, it.id)
        }
    }
}
