package com.jira.lessons.console.managers

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.security.JiraAuthenticationContext
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.managers.Logger")

def jiraAuthenticationContext = ComponentAccessor.jiraAuthenticationContext
JiraAuthenticationContext jiraAuthenticationContext2 = ComponentAccessor.getJiraAuthenticationContext()

def loggedInUser = jiraAuthenticationContext.loggedInUser //jiraAuthenticationContext.getLoggedInUser()

logger.info("LoggedInUser key: ${loggedInUser.key}")
logger.info("LoggedInUser name: ${loggedInUser.name}")
logger.info("LoggedInUser e-mail address: ${loggedInUser.emailAddress}")

logger.info("User is logged in? ${jiraAuthenticationContext.isLoggedInUser()}")

loggedInUser