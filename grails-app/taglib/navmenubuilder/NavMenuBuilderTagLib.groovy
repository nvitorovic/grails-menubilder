package navmenubuilder

class NavigationTagLib {
    static defaultEncodeAs = [taglib: 'raw']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def navMenuContext
    def menuMarkup = ""
    static namespace = 'navb'

    /**
     * @attr itemWrapper
     * @attr itemWrapperClass
     * @attr iconClassWraper
     * @attr iconClassPrefix
     * @attr shouldRender
     */
    def navMenu = { attrs, body ->
        if (!menuMarkup) {

            def itemWrapper = attrs.remove('itemWrapper') ?: 'li'
            def itemWrapperClass = attrs.remove('itemWrapperClass') ?: 'controller'
            def itemWrapperActiveClass = attrs.remove('itemWrapperActiveClass') ?: 'active'
            def iconWrapper = attrs.remove('iconClassWrapper') ?: 'i'
            def iconClassPrefix = attrs.remove('iconClassPrefix') ?: 'fa'
            def shouldRender = attrs.remove('shouldRender') ?: null
            if (!(shouldRender instanceof Closure)) {
                shouldRender = null
            }

            navMenuContext.navEntries.each { navItem ->
                log.debug "Adding ${navItem}"
                if (!shouldRender || shouldRender && shouldRender(navItem.controller)) {
                    def name = navItem.controller.getLogicalPropertyName()
                    menuMarkup += "<${itemWrapper} class='${itemWrapperClass} ${controllerName == name ? itemWrapperActiveClass : ''}'>"
                    menuMarkup += g.link(controller: name, action: 'index') {
                        "<${iconWrapper} class='${iconClassPrefix} ${iconClassPrefix}-${navItem.showInNavMenu.icon()}'></${iconWrapper}>&nbsp" +
                                g.message(code: "${name}.label",
                                        default: name.capitalize())
                    }
                }
                menuMarkup += "</${itemWrapper}>"

            }
        }
        out << menuMarkup
    }

}
