package com.jira.lessons.console

import org.apache.log4j.Logger
import groovy.util.logging.Log4j

//1
log.info("INFO - 1. Log bejegyzés")
log.info("WARN - 1. Log bejegyzés")

//2
def logger = Logger.getLogger("com.jira.lessons.logger")
logger.info("INFO - 2. Log bejegyzés")
logger.warn("WARN - 2. Log bejegyzés")

//3
@Log4j
class SimpleClass {

    String helloWorld() {
        log.info "INFO - Hello World (3.)"
        log.warn "WARN - Hello World (3.)"

        return "Hello World"
    }
}

def instance = new SimpleClass()
instance.helloWorld()