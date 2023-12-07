package com.jira.lessons.fragment.solutions

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue

import java.text.SimpleDateFormat

Issue issueObj = context.issue as Issue

def customFieldManager = ComponentAccessor.customFieldManager
def worklogManager = ComponentAccessor.worklogManager
def groupManager = ComponentAccessor.groupManager
def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser

def totalWorklogCf = customFieldManager.getCustomFieldObjectsByName("totalWorklog").first()
def deletedWorklogCf = customFieldManager.getCustomFieldObjectsByName("deletedWorklog").first()

def totalWorklogCfv = issueObj.getCustomFieldValue(totalWorklogCf).with {
    it == null ? "Nincs töltve" : (it as Double).toInteger()
}
def deletedWorklogCfv = issueObj.getCustomFieldValue(deletedWorklogCf).with {
    it == null ? "Nincs töltve" : (it as Double).toInteger()
}

def issueWorklogs = worklogManager.getByIssue(issueObj)
def userWorklogData = issueWorklogs.findAll {
    it.authorObject.key == loggedInUser.key
}.with {
    if(it.isEmpty()) {
        ""
    } else {
        """
            <tr>
                <td><b>Bejelentkezett felhasználó munkanaplói</b></td>
                <td>${it.size()}</td>
            </tr>
        """
    }
}

def panelExtra = ""
if(groupManager.isUserInGroup(loggedInUser, groupManager.getGroup("worklog-stat-maintainers"))) {
    Long totalHour = 0
    Long totalMinutes = 0
    String firstCreateDate = "Nincs munkanapló a feladatra könyvelve"
    String lastCreateDate = "Nincs munkanapló a feladatra könyvelve"

    if(!issueWorklogs.isEmpty()) {
        Long totalTimeSpentInSec = (Long) issueWorklogs.timeSpent.sum()
        totalHour = issueWorklogs.isEmpty() ? 0L : (Long)(totalTimeSpentInSec / 3600.0)
        totalMinutes = issueWorklogs.isEmpty() ? 0L : (Long)((totalTimeSpentInSec - (totalHour * 3600)) / 60.0)

        def formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        firstCreateDate = formatter.format(issueWorklogs.first().created)
        lastCreateDate = formatter.format(issueWorklogs.last().created)

    }

    panelExtra += """
    <div class="margin-top">
        <h4>További munkanapló statisztikai adatok</h4>
        <ul id="my-custom-worklog-stat-extra">
            <li>Összes ráfordítás: ${totalHour} óra ${totalMinutes} perc</li>
            <li>Első munkanapló létrehozásának ideje: ${firstCreateDate}</li>
            <li>Utolsó munkanapló létrehozásának ideje: ${lastCreateDate}</li>
        </ul>
        <button class="aui-button aui-button-primary margin-top" id="my-custom-worklog-stat-extra-btn">Panel összecsukás / megjelenítés</button>
    </div>
    """
}

writer.write("""
<div>    
<table class="aui">
        <thead>
            <tr>
                <th>Adat</th>
                <th>Érték</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><b>Összes munkanapló száma (customfield érték)</b></td>
                <td>${totalWorklogCfv}</td>
            </tr>
            <tr>
                <td><b>Törölt munkanaplók száma (customfield érték)</b></td>
                <td>${deletedWorklogCfv}</td>
            </tr>
            ${userWorklogData}
        </tbody>
    </table>
</div>
${panelExtra}
""")