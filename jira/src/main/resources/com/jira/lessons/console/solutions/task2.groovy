package com.jira.lessons.console.solutions

import com.atlassian.jira.component.ComponentAccessor

def projectManager = ComponentAccessor.projectManager
def issueManager = ComponentAccessor.issueManager
def worklogManager = ComponentAccessor.worklogManager

def projectC1SP = projectManager.getProjectByCurrentKey("C1SP")

issueManager.getIssueIdsForProject(projectC1SP.id).collect {
    issueManager.getIssueObject(it)
}.findAll {
    !worklogManager.getByIssue(it).isEmpty()
}.collect {
    it.key
}.join("<br>")