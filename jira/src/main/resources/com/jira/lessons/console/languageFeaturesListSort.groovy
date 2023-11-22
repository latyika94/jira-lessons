package com.jira.lessons.console

import com.onresolve.jira.groovy.lessons.Coordinate
import org.apache.log4j.Logger

def logger = Logger.getLogger("com.jira.lessons.console.Logger")

//https://docs.groovy-lang.org/docs/groovy-2.4.0/html/groovy-jdk/java/util/Collection.htmlw

/* Lista sorrendezés */
def coordinateList = [
        new Coordinate(x: 1, y:1),
        new Coordinate(x: -2, y:10),
        new Coordinate(x: 0, y:-8),
        new Coordinate(x: 12, y:6)
]
logger.info("Coordinate list: ${coordinateList}")

def positiveXCoordinates = coordinateList.findAll {
    it.x > 0
}
logger.info("Positive X coordinates: ${positiveXCoordinates}")

def positiveXCoordinate = coordinateList.find {
    it.x > 0
}
logger.info("Positive X coordinate: ${positiveXCoordinate}")

def sortedCoordinateByX = coordinateList.sort {
    it.x
}
logger.info("Sort by X: ${sortedCoordinateByX}")
def sortedCoordinateByX2 = coordinateList.sort {a,b ->
    a.x <=> b.x //a.x.compareTo(b.x)
}
logger.info("Sort by X: ${sortedCoordinateByX2}")
def sortedCoordinateByXDesc = coordinateList.sort {a,b ->
    b.x <=> a.x //b.x.compareTo(a.x)
}
logger.info("Sort by X descending: ${sortedCoordinateByXDesc}")