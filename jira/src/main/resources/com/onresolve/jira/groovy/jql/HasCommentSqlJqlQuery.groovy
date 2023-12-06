package com.onresolve.jira.groovy.jql

import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.jql.query.LuceneQueryBuilder
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.jql.validator.NumberOfArgumentsValidator
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.util.MessageSet
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand
import com.onresolve.scriptrunner.db.DatabaseUtil
import groovy.sql.GroovyRowResult
import org.apache.lucene.search.Query

import java.text.MessageFormat

class HasCommentSqlJqlQuery extends AbstractScriptedJqlFunction implements JqlQueryFunction{

    public static final String TEMPLATE_QUERY = "issue in ({0})" //Ekvivalens: id in ({0})
    public static final String SQL_QUERY = """
        SELECT 
            DISTINCT issueid 
        FROM 
            jiraaction 
        WHERE 
            actiontype = 'comment'
    """

    JqlQueryParser queryParser = ComponentAccessor.getComponent(JqlQueryParser)
    LuceneQueryBuilder luceneQueryBuilder = ComponentAccessor.getComponent(LuceneQueryBuilder)
    SearchService searchService = ComponentAccessor.getComponent(SearchService)

    @Override
    String getDescription() {
        return "Szűrés azon feladatokra, melyeknek van kommentje (SQL-lel)"
    }

    @Override
    List<Map> getArguments() {
        return []
    }

    @Override
    String getFunctionName() {
        return "hasCommentSql"
    }

    @Override
    MessageSet validate(ApplicationUser user, FunctionOperand operand, TerminalClause terminalClause) {
        def messageSet = new NumberOfArgumentsValidator(0, getArguments().size(), getI18n()).validate(operand) as MessageSet
        messageSet.addMessageSet(super.validate(user, operand, terminalClause))

        if (messageSet.hasAnyErrors()) {
            return messageSet
        }

        searchService.validateQuery(user, mergeQuery(operand))
    }

    private com.atlassian.query.Query mergeQuery(FunctionOperand operand) {

        def result = DatabaseUtil.withSql('jira-app') { sql ->
            sql.rows(SQL_QUERY)
        }.collect {
            String.valueOf((it as GroovyRowResult).getProperty("issueid"))
        }

        if(result.isEmpty()) {
            return  queryParser.parseQuery("")
        }

        return queryParser.parseQuery(MessageFormat.format(TEMPLATE_QUERY, result.join(",")))
    }

    @Override
    Query getQuery(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {
        luceneQueryBuilder.createLuceneQuery(queryCreationContext, mergeQuery(operand).whereClause)
    }
}
