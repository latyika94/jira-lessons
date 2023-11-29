package com.jira.lessons.console.solutions

import com.atlassian.jira.bc.project.ProjectService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.Project
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.solutions.Logger")

def projectService = ComponentAccessor.getComponent(ProjectService)

def projects = projectService.getAllProjects(ComponentAccessor.jiraAuthenticationContext.loggedInUser).get()

String transform(Project project) {
    return "Projekt: [${project.key} - ${project.name}]. Vezető: [${project.projectLead.name} (${project.projectLead.emailAddress})]"
}

/**
 * Logger
 */
projects.each {
    logger.info(transform(it))
}

/**
 * Return
 */
projects.collect{
    transform(it)
}.join("<br>")