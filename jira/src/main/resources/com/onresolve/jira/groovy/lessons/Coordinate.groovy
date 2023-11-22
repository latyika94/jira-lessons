package com.onresolve.jira.groovy.lessons

class Coordinate {
    Integer x
    Integer y

    @Override
    String toString() {
        return "{x=${x}, y=${y}}"
    }
}
