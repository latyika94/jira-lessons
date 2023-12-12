package com.onresolve.jira.groovy.jql.solutions

import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.jql.query.LuceneQueryBuilder
import com.atlassian.jira.jql.query.QueryCreationContext
import com.atlassian.jira.jql.validator.NumberOfArgumentsValidator
import com.atlassian.jira.project.ProjectManager
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.util.MessageSet
import com.atlassian.query.clause.TerminalClause
import com.atlassian.query.operand.FunctionOperand
import com.onresolve.jira.groovy.jql.AbstractScriptedJqlFunction
import com.onresolve.jira.groovy.jql.JqlQueryFunction
import com.onresolve.scriptrunner.db.DatabaseUtil
import groovy.sql.GroovyRowResult
import org.apache.lucene.search.Query

import java.text.MessageFormat

/**
 * Példák:
 * - issueFunction in projectCategoryIs("Software Development")
 */
class ProjectCategoryIsSqlJqlQuery extends AbstractScriptedJqlFunction implements JqlQueryFunction {

    public static final String TEMPLATE_QUERY = "project in ({0})"

    JqlQueryParser queryParser = ComponentAccessor.getComponent(JqlQueryParser)
    LuceneQueryBuilder luceneQueryBuilder = ComponentAccessor.getComponent(LuceneQueryBuilder)
    SearchService searchService = ComponentAccessor.getComponent(SearchService)

    ProjectManager projectManager = ComponentAccessor.getProjectManager()

    @Override
    String getDescription() {
        return "Szűrés feladat módosítás idejére"
    }

    @Override
    List<Map> getArguments() {
        return [
                [
                        description: "Projekt kategória",
                        optional   : false
                ] as Map,
        ]
    }

    @Override
    String getFunctionName() {
        return "projectCategoryIs"
    }

    @Override
    MessageSet validate(ApplicationUser user, FunctionOperand operand, TerminalClause terminalClause) {
        def messageSet = new NumberOfArgumentsValidator(1, getArguments().size(), getI18n()).validate(operand) as MessageSet
        messageSet.addMessageSet(super.validate(user, operand, terminalClause))

        if (messageSet.hasAnyErrors()) {
            return messageSet
        }
        try {
            return searchService.validateQuery(user, mergeQuery(operand, messageSet))
        } catch(Exception e) {
            messageSet.addErrorMessage(e.message)
            return messageSet
        }
    }

    private com.atlassian.query.Query mergeQuery(FunctionOperand operand, MessageSet messageSet = null) {
        def projectCategory = projectManager.getProjectCategoryObjectByNameIgnoreCase(operand.args.first().toLowerCase())

        if(projectCategory == null) {
            if(messageSet != null) {
                messageSet.addErrorMessage("Projekt kategória nem létezeik: ${operand.args.first()}")
            } else {
                throw new IllegalArgumentException("Projekt kategória nem létezeik: ${operand.args.first()}")
            }
        }

        def sqlStr = """
        SELECT 
            DISTINCT p.id AS projectid
        FROM 
            project p 
        JOIN 
            nodeassociation n
        ON 
            p.id=n.source_node_id
        WHERE 
            n.sink_node_entity='ProjectCategory' 
        AND n.sink_node_id= 
            (   SELECT 
                    id 
                FROM 
                    projectcategory 
                WHERE 
                    cname='${projectCategory.name}') 
    """.toString()

        def result = DatabaseUtil.withSql('jira-app') { sql ->
            sql.rows(sqlStr)
        }.collect {
            String.valueOf((it as GroovyRowResult).getProperty("projectid"))
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
