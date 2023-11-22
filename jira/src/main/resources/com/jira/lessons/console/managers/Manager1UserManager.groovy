package com.jira.lessons.console.managers

import com.adaptavist.hapi.jira.users.Users
import com.atlassian.jira.component.ComponentAccessor
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.managers.Logger")
def currentUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser
def notifierCustomerUserName = "Teszt Felhasznalo"

def userManager = ComponentAccessor.userManager

def userByName = userManager.getUserByName(currentUser.name)
logger.info("CurrenUser query: ${userByName}")

def notifierCustomer = userManager.getUserByName(notifierCustomerUserName)
logger.info("Query by name: ${notifierCustomer}")
logger.info("Query by name - displayName: ${notifierCustomer.displayName}")

//HAPI: https://docs.adaptavist.com/sr4js/latest/hapi
//HAPI - Users: https://docs.adaptavist.com/sr4js/latest/hapi/work-with-users

def notifierCustomerHAPI = Users.getByName(notifierCustomerUserName)
logger.info("HAPI - Query by name: ${notifierCustomerHAPI}")
logger.info("HAPI - Query by name - displayName: ${notifierCustomerHAPI.displayName}")