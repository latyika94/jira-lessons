package com.jira.lessons.events

import com.adaptavist.hapi.jira.issues.Issues
import com.atlassian.jira.event.issue.IssueEvent
import com.atlassian.jira.event.type.EventType
import org.apache.log4j.Logger


def logger = Logger.getLogger("com.jira.lessons.events.Logger")

def issueEvent = event as IssueEvent
def updatedIssue = issueEvent.issue
def updatedIssueProject = updatedIssue.projectObject


logger.info("Updated issue: ${updatedIssue}")
logger.info("Updated issue project: ${updatedIssueProject}")

switch (issueEvent.eventTypeId) {
    case EventType.ISSUE_COMMENTED_ID:
        logger.info("Issue is commented with body: ${issueEvent.comment.body}")
        break

    case EventType.ISSUE_WORKLOGGED_ID:
        logger.info("Issue is workloged with comment: ${issueEvent.worklog.comment}")
        //HAPI: https://docs.adaptavist.com/sr4js/latest/hapi/work-with-comments
        Issues.getByKey(issueEvent.issue.key).addComment("""
            Új munkanapló rögzítés történet a feladaton.
            Rögzítő felhasználó: ${issueEvent.worklog.authorObject.displayName}
            Rögzített munkaidő (óra): ${issueEvent.worklog.timeSpent / 3600.0}
        """.trim())
        break
    default:
        logger.info("Unhandled issue event occured, typeid: ${issueEvent.eventTypeId}")
}