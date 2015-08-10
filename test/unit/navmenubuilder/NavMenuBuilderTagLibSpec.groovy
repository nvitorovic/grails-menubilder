package navmenubuilder

import com.grails.plugin.navbuilder.ControllerWithAnnotation
import com.grails.plugin.navbuilder.NavMenuContext
import com.grails.plugin.navbuilder.ShowInNavMenu
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsControllerClass
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(NavigationTagLib)
class NavMenuBuilderTagLibSpec extends Specification {

    def setup() {
        def navMenuContext = Mock(NavMenuContext)
        navMenuContext.navEntries >> [
                createMockForControllerWithAnnotation("First", 1),
                createMockForControllerWithAnnotation("Second", 2),
                createMockForControllerWithAnnotation("Third", 3)
        ]
        tagLib.navMenuContext = navMenuContext
    }

    private NavMenuContext createNavMenuContext(List data) {
        def navMenuContext = Mock(NavMenuContext)
        def listOfNavEntries = []
        data.each { item ->
            listOfNavEntries << createMockForControllerWithAnnotation(item.name, item.order)
        }
        navMenuContext.navEntries >> listOfNavEntries
        navMenuContext
    }

    private ControllerWithAnnotation createMockForControllerWithAnnotation(String name, int orderNo) {
        Mock(ControllerWithAnnotation) {
            def stub = Mock(GrailsControllerClass) {
                getLogicalPropertyName() >> name
            }

            getController() >> stub
            getShowInNavMenu() >> Mock(ShowInNavMenu) {
                order() >> orderNo
                icon() >> getIconForController(name)
            }
        }
    }

    private GString getIconForController(String name) {
        "ic-${name.toLowerCase()}"
    }

    def cleanup() {
    }

    void "nav menu markup should be reused"() {
        given:
        tagLib.menuMarkup = "Not blank"

        expect:
        tagLib.navMenu() == "Not blank"
    }

    @Unroll("nav menu should be properly wrapped in #wrapperObject")
    void "nav menu should be properly wrapped"() {
        expect:
        tagLib.navMenu(itemWrapper: wrapperObject).toString() startsWith("<$wrapperObject")
        where:
        wrapperObject << ["div", "ul"]
    }

    @Unroll("Testing right controllers #content.name #content.order with #numberOfItems")
    void "nav menu should contain correct number of items"() {
        given: "Nav menu context contains #numberOfItems controllers"

        tagLib.navMenuContext = createNavMenuContext(content)

        expect: "items are present in menu"
        tagLib.navMenu().toString().count('<li class') == numberOfItems

        where:
        content                                                                            | numberOfItems
        []                                                                                 | 0
        [[name: "First", order: 1]]                                                        | 1
        [[name: "First", order: 1], [name: "Second", order: 2]]                            | 2
        [[name: "First", order: 1], [name: "Second", order: 3], [name: "Third", order: 2]] | 3

    }

    @Unroll("Testing right controllers #content.name #content.order with #numberOfItems")
    void "nav menu should link to right controllers"() {
        given: "Nav menu context contains #numberOfItems controllers"

        tagLib.navMenuContext = createNavMenuContext(content)

        expect: "3 items are present in menu"
        tagLib.navMenu().toString().count('<li class') == numberOfItems

        where:
        content                                                                            | numberOfItems
        [[name: "First", order: 1]]                                                        | 1
        [[name: "First", order: 1], [name: "Second", order: 2]]                            | 2
        [[name: "First", order: 1], [name: "Second", order: 3], [name: "Third", order: 2]] | 3

    }
}
