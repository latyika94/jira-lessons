package com.onresolve.jira.groovy.jql.solutions

import com.atlassian.jira.JiraDataType
import com.atlassian.jira.JiraDataTypes
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.jql.operand.QueryLiteral
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.permission.ProjectPermissions
import com.atlassian.jira.project.version.VersionManager
import com.atlassian.jira.security.PermissionManager
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand
import com.onresolve.jira.groovy.jql.AbstractScriptedJqlFunction
import com.onresolve.jira.groovy.jql.JqlFunction

import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Példák:
 * - fixVersion in versionReleaseDateIsBefore()
 * - fixVersion in versionReleaseDateIsBefore("2023-12-01")
 */
class VersionReleaseDateIsBeforeJql extends AbstractScriptedJqlFunction implements JqlFunction {

    VersionManager versionManager = ComponentAccessor.getComponent(VersionManager)
    PermissionManager permissionManager = ComponentAccessor.getPermissionManager()

    //Kötelező
    //Saját JQL függvényünk leírása
    @Override
    String getDescription() {
        "A verzió kiadásának dátuma korábbi, mint a megadott"
    }

    //Kötelező
    //Saját JQL függvényünk paraméterei
    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Kiadás dátum maximum érték ('yyyy-MM-dd' formátumban)",
                        optional   : true
                ] as Map,
        ]
    }

    //Kötelező
    //Saját JQL függvényünk neve
    @Override
    String getFunctionName() {
        return "versionReleaseDateIsBefore"
    }

    //A QueryLiteral visszaadott értékei (id - azonosítói) Version típusú objektumhoz tartozik
    @Override
    JiraDataType getDataType() {
        JiraDataTypes.VERSION
    }

    @Override
    List<QueryLiteral> getValues(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {
        LocalDate queryDate = operand.args.size() >= 1 ? LocalDate.parse(operand.args.first(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) : LocalDate.now()

        /* Leszűrjük az argumentumok alapján az összes olyan verziót, melynek releaseDate értéke kisebb egyenlő, mint a megadott, vagy az aktuális nap*/
        versionManager.allVersions.findAll {
            (it.releaseDate?.toLocalDate() ?:LocalDate.now())  <= queryDate
        }.findAll {
            //Leszürjük, hogy a végrehajtó felhasználónak van-e hozzáférése a projekthez
            queryCreationContext.securityOverriden || permissionManager.hasPermission(ProjectPermissions.BROWSE_PROJECTS, it.project, queryCreationContext.applicationUser)
            // <4>
        }.collect {
            //Elkészítjük a QueryLiteral-okat a Version objektum "id" mezőjéből.
            new QueryLiteral(operand, it.id)
        }
    }
}