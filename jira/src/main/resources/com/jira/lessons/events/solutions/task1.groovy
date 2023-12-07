package com.jira.lessons.events.solutions

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.issue.IssueEvent
import com.atlassian.jira.event.type.EventType

/**
 * Listener name: Important Lead Comment
 * Projects: Global
 * Events: [Issue Commented]
 */

def issueEvent = event as IssueEvent

if(issueEvent.eventTypeId == EventType.ISSUE_COMMENTED_ID) {
    if(issueEvent.comment.authorApplicationUser.key == issueEvent.issue.projectObject.projectLead.key) {
        def commentManager = ComponentAccessor.commentManager

        def mutableComment = commentManager.getMutableComment(issueEvent.comment.id)
        mutableComment.setBody("*FONTOS!* Projekt vezetői megjegyzés!\n" + mutableComment.body)
        commentManager.update(mutableComment, false)
    }
}