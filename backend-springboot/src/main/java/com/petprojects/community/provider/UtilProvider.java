package com.petprojects.community.provider;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class UtilProvider {

    // source: https://stackoverflow.com/questions/5079458/copy-specific-fields-by-using-beanutils-copyproperties
    // modified copy properties
    public static void copyProperties(Object source, Object target, Iterable<String> includedProps) {
        BeanWrapper srcWrap = PropertyAccessorFactory.forBeanPropertyAccess(source);
        BeanWrapper trgWrap = PropertyAccessorFactory.forBeanPropertyAccess(target);

        includedProps.forEach(p -> trgWrap.setPropertyValue(p, srcWrap.getPropertyValue(p)));
    }
}
