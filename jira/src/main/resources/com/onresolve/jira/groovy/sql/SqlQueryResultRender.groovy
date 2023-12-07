package com.onresolve.jira.groovy.sql

import com.onresolve.scriptrunner.db.DatabaseUtil
import groovy.sql.GroovyRowResult

class SqlQueryResultRender {

    static String renderSql(String sqlQueryString, int offset = 0, int maxRows = 50) {
        List<GroovyRowResult> sqlResult = DatabaseUtil.withSql('jira-app') { sql ->
            return sql.rows(sqlQueryString, offset, maxRows)
        } as List<GroovyRowResult>

        if (sqlResult.isEmpty()) {
            return "A lekérdezésnek nincs eredménye"
        }

        def serialNumber = ["ROW_NUMBER"]
        serialNumber.addAll(sqlResult.first().keySet())

        def headers = serialNumber.collect {
            "<th>${it}</th>"
        }.join("\n")

        def rowSetSb = new StringBuilder()
        sqlResult.eachWithIndex { it, index ->
            def rowsSb = new StringBuilder()
            rowsSb.append("<tr>")
            rowsSb.append("<td>${index+1}</td>")
            it.values().each {
                rowsSb.append("<td>${it}</td>")
            }

            rowsSb.append("</tr>")
            rowSetSb.append(rowsSb.toString())
        }


        return new StringBuilder()
                .append("""<table class="aui"><thead><tr>""")
                .append(headers)
                .append("""</tr></thead><tbody>""")
                .append(rowSetSb.toString())
                .append("""</tbody></table>""")
                .toString()
    }
}
