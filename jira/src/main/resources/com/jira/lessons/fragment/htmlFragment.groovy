package com.jira.lessons.fragment

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue

Issue issue = context.issue as Issue

def worklogManager = ComponentAccessor.worklogManager
def commentManager = ComponentAccessor.commentManager

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

writer.write("""
    <p>
        <ul>
            <li><b>Utolsó kommentelő:</b> ${lastCommenter}</li>
            <li><b>Utolsó könyvelő: </b>${lastWorkloger}</li>
        </ul>
    </p>
""")