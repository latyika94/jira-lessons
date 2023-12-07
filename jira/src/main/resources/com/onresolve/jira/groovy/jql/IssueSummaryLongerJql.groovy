package com.onresolve.jira.groovy.jql

import com.atlassian.jira.JiraDataType
import com.atlassian.jira.JiraDataTypes
import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.jql.operand.QueryLiteral
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.permission.ProjectPermissions
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.project.version.VersionManager
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Az alábbi JQL függvény azokra az feladatokra szűr, melyen van olyan projekt verzió beállítva, ami tartalmazza a megadott értéket.
 * A Custom JQL a [com.onresolve.jira.groovy.jql.JqlFunction]-t implementálja a [JqlQueryFunction] helyett, mivel nem Issue-ra, hanem Version mezőre szűrünk.
 * Ebben az esetben a [QueryLiteral] listát kell visszaadni, lásd [getValues] függvény
 *
 * Példák:
 * - fixVersion in issueSummaryLongerThan(10)
 * - fixVersion in issueSummaryLongerThan(20)
 */
class IssueSummaryLongerThanJql extends AbstractScriptedJqlFunction implements JqlFunction {

    ProjectService projectService = ComponentAccessor.getComponent(ProjectService)
    IssueManager issueManager = ComponentAccessor.getIssueManager()

    //Kötelező
    //Saját JQL függvényünk leírása
    @Override
    String getDescription() {
        "Feladat összefoglaló hosszabb, mint a megadott érték"
    }

    //Kötelező
    //Saját JQL függvényünk paraméterei
    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Összefoglaló hosszabb, mint",
                        optional   : false
                ] as Map,
        ]
    }

    //Kötelező
    //Saját JQL függvényünk neve
    @Override
    String getFunctionName() {
        return "issueSummaryLongerThan"
    }

    //A QueryLiteral visszaadott értékei (id - azonosítói) Issue típusú objektumhoz tartozik
    @Override
    JiraDataType getDataType() {
        JiraDataTypes.ISSUE
    }

    @Override
    List<QueryLiteral> getValues(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {
        //Lekérdezzük a JQL 1. paraméterben megadott értéket és int-et csinálunk belőle
        Integer length = operand.args.first()?.toInteger()

        //Lekérdezzük az összes projektet, melyhez hozzáfér a JQL-t futtató felhasználó
        List<Issue> issues = projectService.getAllProjects(queryCreationContext.applicationUser).get().collect {
            //Lekérdezzük az összes issue id-t a projektekből és Issue objektumot csinálunk belőlük
            issueManager.getIssueIdsForProject(it.id).collect {
                issueManager.getIssueObject(it)
            }
        }.flatten() as List<Issue> //A List<List<Issue>>-ból List<Issue>-t csinálunk

        //Megkeressük az összes olyan issue-t, melynek összefoglalója, legalább {length} méretű
        issues.findAll {
            it.summary.length() >= length
        }.collect {
            //Elkészítjük a QueryLiteral-okat az Issue objektum "id" mezőjéből.
            new QueryLiteral(operand, it.id)
        }
    }
}
