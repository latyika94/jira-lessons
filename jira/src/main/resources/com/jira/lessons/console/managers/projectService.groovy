package com.jira.lessons.console.managers

import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.type.ProjectTypeKeys

def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser

def projectService = ComponentAccessor.getComponent(ProjectService)

projectService.getAllProjects(loggedInUser).get().findAll {
    it.projectTypeKey == ProjectTypeKeys.SOFTWARE
}.collect {
    "${it.key} - ${it.name}"
}.join("<br>")