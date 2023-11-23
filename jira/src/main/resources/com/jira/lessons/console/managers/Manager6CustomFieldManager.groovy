package com.jira.lessons.console.managers

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption
import com.atlassian.jira.issue.index.IssueIndexingParams
import com.atlassian.jira.issue.index.IssueIndexingService
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.managers.Logger")
def currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser

def customFieldName = "IP"
def customFieldId = "customfield_10402"
def issueKey = "TES-32"

def issueManager = ComponentAccessor.issueManager
def customFieldManager = ComponentAccessor.customFieldManager
def issueIndexingService = ComponentAccessor.getComponent(IssueIndexingService)

def ipCustomField = customFieldManager.getCustomFieldObjectsByName(customFieldName).first()
def ipCustomFieldById = customFieldManager.getCustomFieldObject(customFieldId)

def issue = issueManager.getIssueObject(issueKey)
logger.info("${issueKey} ${customFieldName} értéke: ${issue.getCustomFieldValue(ipCustomField)}")

//IP customfield értéke TES-32 feladaton
def cfValue = issue.getCustomFieldValue(ipCustomFieldById)
logger.info("cfValue type: ${cfValue.class.name}")

//IP customfield érték módosítás
issue.setCustomFieldValue(ipCustomField, "IP mező új értéke konzolból")
issueManager.updateIssue(
        currentUser,
        issue,
        EventDispatchOption.DO_NOT_DISPATCH,
        false
)
issueIndexingService.reIndex(issue, IssueIndexingParams.INDEX_ALL)