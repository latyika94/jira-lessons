package com.jira.lessons.events.solutions

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.event.worklog.WorklogDeletedEvent
import com.atlassian.jira.event.worklog.WorklogEvent
import com.atlassian.jira.issue.index.IssueIndexingParams
import com.atlassian.jira.issue.index.IssueIndexingService

/**
 * Listener name: Worklog counter
 * Projects: Global
 * Events: [WorklogCreatedEvent, WorklogDeletedEvent]
 */

def worklogEvent = event as WorklogEvent

def currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser

def worklogManager = ComponentAccessor.worklogManager
def customFieldManager = ComponentAccessor.customFieldManager
def issueManager = ComponentAccessor.issueManager
def issueIndexingService = ComponentAccessor.getComponent(IssueIndexingService)

def totalWorklogCf = customFieldManager.getCustomFieldObjectsByName("totalWorklog").first()
def deletedWorklogCf = customFieldManager.getCustomFieldObjectsByName("deletedWorklog").first()

def totalWorklogSize = worklogManager.getByIssue(worklogEvent.worklog.issue).size().toDouble()

def mutableIssue = issueManager.getIssueObject(worklogEvent.worklog.issue.id)

mutableIssue.setCustomFieldValue(totalWorklogCf, totalWorklogSize)

if(worklogEvent instanceof WorklogDeletedEvent) {
    Double cfValue = mutableIssue.getCustomFieldValue(deletedWorklogCf)

    mutableIssue.setCustomFieldValue(deletedWorklogCf,  (cfValue ?: 0.0) + 1.0d)
}

issueManager.updateIssue(
        currentUser,
        mutableIssue,
        EventDispatchOption.DO_NOT_DISPATCH,
        false
)
issueIndexingService.reIndex(mutableIssue, IssueIndexingParams.INDEX_ALL)