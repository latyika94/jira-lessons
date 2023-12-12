package com.onresolve.jira.groovy.jql.solutions

import com.atlassian.crowd.exception.UserNotFoundException
import com.atlassian.jira.JiraDataType
import com.atlassian.jira.JiraDataTypes
import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.jql.operand.QueryLiteral
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.user.util.UserManager
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand
import com.onresolve.jira.groovy.jql.AbstractScriptedJqlFunction
import com.onresolve.jira.groovy.jql.JqlFunction

/**
 * Példák:
 * - project in projectLeadIs()
 * - project in projectLeadIs("laszlo.majnar")
 */
class ProjectLeadIsJql extends AbstractScriptedJqlFunction implements JqlFunction {
    ProjectService projectService = ComponentAccessor.getComponent(ProjectService)
    UserManager userManager = ComponentAccessor.getUserManager()

    //Kötelező
    //Saját JQL függvényünk leírása
    @Override
    String getDescription() {
        "Szűrés projekt vezetőjére"
    }

    //Kötelező
    //Saját JQL függvényünk paraméterei
    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Projekt vezető felhasználóneve",
                        optional   : true
                ] as Map,
        ]
    }

    //Kötelező
    //Saját JQL függvényünk neve
    @Override
    String getFunctionName() {
        return "projectLeadIs"
    }

    //A QueryLiteral visszaadott értékei (id - azonosítói) Project típusú objektumhoz tartozik
    @Override
    JiraDataType getDataType() {
        JiraDataTypes.PROJECT
    }

    @Override
    List<QueryLiteral> getValues(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {

        List<String> appUsers =  operand.args.size() >= 1 ? operand.args.first().split(',').collect {username ->
            def appUser = userManager.getUserByName(username.trim())
            if(appUser == null) {
                throw new UserNotFoundException(username)
            }
            appUser.key
        } : [queryCreationContext.applicationUser.key]


        return projectService.getAllProjects(queryCreationContext.applicationUser).get().findAll {
            appUsers.contains(it.projectLead.key)
        }.collect {
            new QueryLiteral(operand, it.id)
        }
    }
}