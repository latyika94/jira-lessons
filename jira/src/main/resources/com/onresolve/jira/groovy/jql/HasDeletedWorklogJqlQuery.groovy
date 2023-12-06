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
import org.apache.lucene.search.Query

import java.text.MessageFormat

class HasDeletedWorklogJqlQuery extends AbstractScriptedJqlFunction implements JqlQueryFunction{

    public static final String TEMPLATE_QUERY = "(deletedWorklog >= {0} AND deletedWorklog is not EMPTY)"
    public static final String TEMPLATE_QUERY_EMPTY = "deletedWorklog is EMPTY"

    JqlQueryParser queryParser = ComponentAccessor.getComponent(JqlQueryParser)
    LuceneQueryBuilder luceneQueryBuilder = ComponentAccessor.getComponent(LuceneQueryBuilder)
    SearchService searchService = ComponentAccessor.getComponent(SearchService)

    @Override
    String getDescription() {
        return "Feladaton törölt munkanaplók számának szűrése"
    }

    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Törölt munkanaplók száma",
                        optional   : false
                ] as Map,
        ]
    }

    @Override
    String getFunctionName() {
        return "hasDeletedWorklog"
    }

    @Override
    MessageSet validate(ApplicationUser user, FunctionOperand operand, TerminalClause terminalClause) {
        def messageSet = new NumberOfArgumentsValidator(1, getArguments().size(), getI18n()).validate(operand) as MessageSet
        messageSet.addMessageSet(super.validate(user, operand, terminalClause))

        if (messageSet.hasAnyErrors()) {
            return messageSet
        }

        searchService.validateQuery(user, mergeQuery(operand))
    }

    private com.atlassian.query.Query mergeQuery(FunctionOperand operand) {
        def firstOperand = operand.args.first()
        if(firstOperand.toLowerCase() == "empty") {
            queryParser.parseQuery(TEMPLATE_QUERY_EMPTY)
        } else {
            queryParser.parseQuery(MessageFormat.format(TEMPLATE_QUERY, firstOperand))
        }
    }

    @Override
    Query getQuery(QueryCreationContext queryCreationContext, FunctionOperand operand, TerminalClause terminalClause) {
        luceneQueryBuilder.createLuceneQuery(queryCreationContext, mergeQuery(operand).whereClause)
    }
}
