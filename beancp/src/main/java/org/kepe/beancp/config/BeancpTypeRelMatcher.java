package org.kepe.beancp.config;

import java.lang.reflect.Type;

import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public interface BeancpTypeRelMatcher	
{
    public boolean matches(Type fromType,Class<?> fromClass,Type toType,Class<?> toClass);
    
}
