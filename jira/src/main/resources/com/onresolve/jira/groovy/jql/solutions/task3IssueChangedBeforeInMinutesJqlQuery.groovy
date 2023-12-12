package com.onresolve.jira.groovy.jql.solutions

import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.jql.query.LuceneQueryBuilder
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.jql.validator.NumberOfArgumentsValidator
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.util.MessageSet
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand
import com.onresolve.jira.groovy.jql.AbstractScriptedJqlFunction
import com.onresolve.jira.groovy.jql.JqlQueryFunction
import org.apache.lucene.search.Query

import java.text.MessageFormat
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Példák:
 * - issueFunction in issueChangedBeforeInMinutes(60)
 */
class IssueChangedBeforeInMinutesJqlQuery extends AbstractScriptedJqlFunction implements JqlQueryFunction {

    public static final String TEMPLATE_QUERY = "issue in ({0})"

    JqlQueryParser queryParser = ComponentAccessor.getComponent(JqlQueryParser)
    LuceneQueryBuilder luceneQueryBuilder = ComponentAccessor.getComponent(LuceneQueryBuilder)
    SearchService searchService = ComponentAccessor.getComponent(SearchService)

    ProjectService projectService = ComponentAccessor.getComponent(ProjectService)
    IssueManager issueManager = ComponentAccessor.getIssueManager()

    @Override
    String getDescription() {
        return "Szűrés feladat módosítás idejére"
    }

    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Módosítás ideje percben",
                        optional   : false
                ] as Map,
        ]
    }

    @Override
    String getFunctionName() {
        return "issueChangedBeforeInMinutes"
    }

    @Override
    MessageSet validate(ApplicationUser user, FunctionOperand operand, TerminalClause terminalClause) {
        def messageSet = new NumberOfArgumentsValidator(1, getArguments().size(), getI18n()).validate(operand) as MessageSet
        messageSet.addMessageSet(super.validate(user, operand, terminalClause))

        if (messageSet.hasAnyErrors()) {
            return messageSet
        }

        searchService.validateQuery(user, mergeQuery(operand, user))
    }

    private com.atlassian.query.Query mergeQuery(FunctionOperand operand, ApplicationUser applicationUser) {
        def firstOperand = operand.args.first().toInteger()
        def currentTime = LocalDateTime.now().minusMinutes(firstOperand).atZone(ZoneId.systemDefault()).toInstant()
        List<Issue> issues = projectService.getAllProjects(applicationUser).get().collect {
            issueManager.getIssueIdsForProject(it.id).collect {
                issueManager.getIssueObject(it)
            }
        }.flatten() as List<Issue>

        def filteredIssues = issues.findAll {
            it.updated.toInstant() <= currentTime
        }.collect {
            it.id
        }

        if(filteredIssues.isEmpty()) {
            return  queryParser.parseQuery("")
        }

        return queryParser.parseQuery(MessageFormat.format(TEMPLATE_QUERY, filteredIssues.join(",")))

    }

    @Override
    Query getQuery(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {
        luceneQueryBuilder.createLuceneQuery(queryCreationContext, mergeQuery(operand, queryCreationContext.applicationUser).whereClause)
    }
}
