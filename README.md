# Nav menu builder

This Grails plugin is used to easily create menus in administrative panels. It's based on annotating controllers, using custom `ShowInNavMenu` annotation. A controller can be annotated and you can specify which icon is to be used (e.g. `fa-home`), as well as `order`, indicating relative position of that item. It is recommended order of items is specified in large numbers (thousands) for easier rearrangement.

The Nav menu is displayed using the `<navb:nav/>` tag, from `NavMenuBuilderTaglib`. This library will generate a menu when first invoked, after which it will cache it.

All controllers and textual contents for menu is gathered in the application initiation phase. This is during Spring's creation of `NavMenuContext` bean (configured in `grails-app/conf/spring/resources.groovy`).


Further improvements:
- Write tests for `NavMenuContext`;
- Provide `NavMenuContext` read and store information on controllers, so taglib wouldn't have to be responsible of extracting it;
- Provide integration with (optional) Spring Security Plugin for grails, so menu items are conditionally displayed, based on role of current user and role required for controller execution.
- Provide default icon set used in menus
- Provide additional tooling, such as custom menu builder