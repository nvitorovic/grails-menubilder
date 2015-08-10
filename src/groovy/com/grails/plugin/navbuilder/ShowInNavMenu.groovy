package com.grails.plugin.navbuilder

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Target(ElementType.TYPE)
public @interface ShowInNavMenu {
    String icon();

    int order() default 1000
}
