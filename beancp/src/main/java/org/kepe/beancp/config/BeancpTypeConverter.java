package org.kepe.beancp.config;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;

/**
 * Hello world!
 *
 */
public interface BeancpTypeConverter<T,R> extends BeancpCustomConverter<T, R>
{
	R convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,R toObj,Type toType);

    default R convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,R toObj,Type toType,Class<R> toClass){
        return convert(feature,context,fromObj,fromType,toObj,toType);
    };
    
}
