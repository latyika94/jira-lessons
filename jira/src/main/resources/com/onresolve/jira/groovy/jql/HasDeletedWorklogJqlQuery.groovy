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

/**
 * Az alábbi JQL függvény azokra az feladatokra szűr, melyen már töröltek munkanaplót a deletedWorklog customfield alapján.
 * "empty" megadás esetén még nincs töltve az érték vagy 0.
 *
 * Példák:
 * - issueFunction in hasDeletedWorklog(1)
 * - issueFunction in hasDeletedWorklog("empty")
 */
class HasDeletedWorklogJqlQuery extends AbstractScriptedJqlFunction implements JqlQueryFunction{

    public static final String TEMPLATE_QUERY = "(deletedWorklog >= {0} AND deletedWorklog is not EMPTY)"
    public static final String TEMPLATE_QUERY_EMPTY = "(deletedWorklog is EMPTY or deletedWorklog = 0)"

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
        //Validáljuk az argumentumok számát
        def messageSet = new NumberOfArgumentsValidator(1, getArguments().size(), getI18n()).validate(operand) as MessageSet

        //Elvégezzük a szülő osztály validációit is
        messageSet.addMessageSet(super.validate(user, operand, terminalClause))

        //Ha van hiba, akkor visszadjuk az üzeneteket
        if (messageSet.hasAnyErrors()) {
            return messageSet
        }

        //Elvégezzük a JQL validációt
        searchService.validateQuery(user, mergeQuery(operand))
    }

    //Saját belső függvény, amit használ a JQL validáció és LuceneQueryBuilder is.
    //Ez a függvény állítja össze a JQL függvényünk JQL kifejezését.
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
