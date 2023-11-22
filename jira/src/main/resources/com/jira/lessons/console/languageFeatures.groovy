package com.jira.lessons.console
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.Logger")

/**
 * Változók
 */
Boolean bool = true
def booleanDef = true

logger.info("bool: " + bool)
logger.info("booleanDef: " + booleanDef)

Integer integer = 3
def integerDef = 12
def longDef = 12L

logger.info("integer: " + integer)
logger.info("integerDef: " + integerDef)
logger.info("longDef: " + longDef)

/**
 * Stringek
 */
String str = "Jira"
def strDef = "Jira"
def strDef2 = "${strDef}"
def strDef3 = '${strDef}'
def strDef4 = """
    Row 1: ${strDef}
    Row 2: ${strDef2.toUpperCase()}
"""

def strDef5 = '''
    Row 1: ${strDef}
    Row 2: ${strDef2.toLowerCase()}
'''

logger.info("str: ${str}")
logger.info("strDef: ${strDef}")
logger.info("strDef2: ${strDef2}")
logger.info("strDef3: ${strDef3}")
logger.info("strDef4: ${strDef4}")
logger.info("strDef5: ${strDef5}")