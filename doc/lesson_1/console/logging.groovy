package com.jira.lessons.console

import org.apache.log4j.Logger
import groovy.util.logging.Log4j

//1
log.info("Log bejegyzés")

//2
def logger = Logger.getLogger("com.jira.lessons.logger")
logger.info("Info szintű bejegyzés")
logger.warn("Warn szintű bejegyzés")

//3
@Log4j
class SimpleClass {

    String helloWorld() {
        log.info "Info - Hello World"
        log.warn "Warn - Hello World"

        return "Hello World"
    }
}

def instance = new SimpleClass()
instance.helloWorld()