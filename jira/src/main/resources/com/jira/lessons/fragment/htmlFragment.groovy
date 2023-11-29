package com.jira.lessons.fragment

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue

Issue issue = context.issue as Issue

def worklogManager = ComponentAccessor.worklogManager
def commentManager = ComponentAccessor.commentManager
def groupManager = ComponentAccessor.groupManager

def lastWorkloger = worklogManager.getByIssue(issue).sort {
    it.created
}.with {
    it.isEmpty() ? "A feladaton nem található munkanapló rekord" : it.last().authorObject.displayName
}

def lastCommenter = commentManager.getComments(issue).sort {
    it.created
}.with {
    it.isEmpty() ? "A feladaton nem található komment" : it.last().authorApplicationUser.displayName
}

def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def isAdminUser = groupManager.isUserInGroup(loggedInUser, groupManager.getGroup("jira-administrators"))

def adminUserFunction = ""

if(isAdminUser) {
    adminUserFunction += """<button class="aui-button aui-button-primary margin-top margin-left" id="my-custom-issue-stat-admin-button">Life as Sysadmin</button>"""
}

writer.write("""
    <p>
        <ul>
            <li class="red-color-item"><b>Utolsó kommentelő:</b> ${lastCommenter}</li>
            <li><b>Utolsó könyvelő: </b>${lastWorkloger}</li>
        </ul>
        <div style="display: flex">
            <button class="aui-button aui-button-primary margin-top" id="my-custom-issue-stat-close-button">Panel összecsukás</button>
            <button class="aui-button aui-button-primary margin-top margin-left" id="my-custom-issue-stat-reload-button">Oldal újratöltés</button>
            
            ${adminUserFunction}
        </div> 
    </p>
""")