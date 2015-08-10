package com.grails.plugin.navbuilder


import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsClass
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware

import javax.annotation.PostConstruct

/**
 * Created by nenad on 4/17/15.
 */
class NavMenuContext {
    GrailsApplication grailsApplication
    List<ControllerWithAnnotation> navEntries = []


    def init() {
        print "Initializing nav menu context"
        List<ControllerWithAnnotation> controllers = []
        // Extract needed information
        grailsApplication.controllerClasses.collect { controller ->
            def showInNavMenuAnnotation = controller.clazz.annotations.find { ann -> ann instanceof ShowInNavMenu }
            if (showInNavMenuAnnotation) {
                controllers << new ControllerWithAnnotation(controller: controller, showInNavMenu: showInNavMenuAnnotation)
            }
        }

        // sort by the order of controllers
        navEntries = controllers.sort { it -> it.showInNavMenu.order() }
        print "Prepared nav entries ${navEntries}"
    }
}

class ControllerWithAnnotation {
    GrailsControllerClass controller
    ShowInNavMenu showInNavMenu


    @Override
    public String toString() {
        return "ControllerWithAnnotation{" +
                "controller= ${controller}, showInNavMenu = ${showInNavMenu}}"
    }
}