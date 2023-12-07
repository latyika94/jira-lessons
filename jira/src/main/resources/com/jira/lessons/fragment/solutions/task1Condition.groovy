package com.jira.lessons.fragment.solutions

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue

def issueObj = issue as Issue

def customFieldManager = ComponentAccessor.customFieldManager

def totalWorklogCf = customFieldManager.getCustomFieldObjectsByName("totalWorklog").first()
def deletedWorklogCf = customFieldManager.getCustomFieldObjectsByName("deletedWorklog").first()

def totalWorklogCfv = issueObj.getCustomFieldValue(totalWorklogCf)
def deletedWorklogCfv = issueObj.getCustomFieldValue(deletedWorklogCf)

(totalWorklogCfv != null && totalWorklogCfv > 0.0d) || (deletedWorklogCfv != null && deletedWorklogCfv > 0.0d)