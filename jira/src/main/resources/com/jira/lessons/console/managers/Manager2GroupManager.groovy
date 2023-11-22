package com.jira.lessons.console.managers

import com.atlassian.jira.component.ComponentAccessor
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.managers.Logger")
def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def notifierCustomerUserName = "Teszt Felhasznalo"

def groupManager = ComponentAccessor.groupManager

def groupList = groupManager.getGroupsForUser(notifierCustomerUserName)
def groupListStr = groupList.collect {
    it.name
}.join(", ")
logger.info("${notifierCustomerUserName} user groups: [${groupListStr}]")

def groupListCurrent = groupManager.getGroupsForUser(loggedInUser)
def groupListStrCurrent = groupListCurrent.collect {
    it.name
}.join(", ")
logger.info("${loggedInUser.name} user groups: [${groupListStrCurrent}]")