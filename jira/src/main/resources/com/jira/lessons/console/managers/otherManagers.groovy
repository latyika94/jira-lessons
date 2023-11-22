package com.jira.lessons.console.managers

import com.atlassian.jira.bc.group.GroupService
import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.sal.api.user.UserManager

def userManager = ComponentAccessor.userManager
def jiraAuthenticationContext = ComponentAccessor.jiraAuthenticationContext
def groupManager = ComponentAccessor.groupManager
def permissionManager = ComponentAccessor.permissionManager
def projectManager = ComponentAccessor.projectManager
def issueManager = ComponentAccessor.issueManager
def issueService = ComponentAccessor.issueService
def customFieldManager = ComponentAccessor.customFieldManager
def changeHistoryManager = ComponentAccessor.changeHistoryManager
def commentManager = ComponentAccessor.commentManager
def projectService = ComponentAccessor.getComponent(ProjectService)
def projectServiceByOSGI = ComponentAccessor.getOSGiComponentInstanceOfType(ProjectService)

def groupService = ComponentAccessor.getComponent(GroupService)
def userManagerSal = ComponentAccessor.getComponent(UserManager) //Null

def groupServiceOSGI = ComponentAccessor.getOSGiComponentInstanceOfType(GroupService)
def userManagerSalOSGI = ComponentAccessor.getOSGiComponentInstanceOfType(UserManager)

[
        userManager,
        jiraAuthenticationContext,
        commentManager,
        groupManager,
        permissionManager,
        projectManager,
        projectService,
        projectServiceByOSGI,
        issueManager,
        issueService,
        customFieldManager,
        changeHistoryManager,
        groupService,
        groupServiceOSGI,
        userManagerSal, //Null
        userManagerSalOSGI
].join("<br>")