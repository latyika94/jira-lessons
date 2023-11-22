package com.jira.lessons.console

import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.Logger")

//https://docs.groovy-lang.org/docs/groovy-2.4.0/html/groovy-jdk/java/util/Collection.html

/**
 * Lista, Map
 */
def numberList = [8,23,764,213,312,343,2]

/* Lista bejárás */
numberList.each {
    logger.info("numberList element: ${it}")
}

numberList.eachWithIndex { it, index ->
    logger.info("numberList index: ${index}, element: ${it}")
}

/* Lista transzformálás */
def multipliedList = numberList.collect {
    it * 2
}
logger.info("Original list:   ${numberList}")
logger.info("Multiplied list: ${multipliedList}")