package com.jira.lessons.console.managers

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.UpdateProjectParameters
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.managers.Logger")
def loggedInUser = ComponentAccessor.jiraAuthenticationContext.loggedInUser

def projectManager = ComponentAccessor.projectManager

def C1SP = projectManager.getProjectByCurrentKey("C1SP")
logger.info("C1SP projekt: ${C1SP}")
logger.info("C1SP projekt - name: ${C1SP.name}")
logger.info("C1SP projekt - description: ${C1SP.description}")
logger.info("C1SP projekt - lead: ${C1SP.projectLead}")

def C1SPLead = C1SP.projectLead

projectManager.updateProject(
        UpdateProjectParameters.forProject(C1SP.id)
                .leadUserKey(loggedInUser.key)
)

def C1SPupdated = projectManager.getProjectByCurrentKey("C1SP")
logger.info("C1SP projekt - updated - lead: ${C1SPupdated.projectLead}")

projectManager.updateProject(
        UpdateProjectParameters.forProject(C1SP.id)
                .leadUserKey(C1SPLead.key)
)

def C1SProllbacked = projectManager.getProjectByCurrentKey("C1SP")
logger.info("C1SP projekt - rollbacked - lead: ${C1SProllbacked.projectLead}")