import com.grails.plugin.navbuilder.NavMenuContext

beans = {
    navMenuContext(NavMenuContext) { navInMenuContextBean ->
        //grailsApplication = ref('grailsApplication')
        navInMenuContextBean.autowire = 'byName'
        navInMenuContextBean.initMethod = 'init'
    }
}