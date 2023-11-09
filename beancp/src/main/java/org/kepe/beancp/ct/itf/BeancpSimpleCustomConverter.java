package org.kepe.beancp.ct.itf;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;

import org.kepe.beancp.ct.itf.BeancpCustomConverter;

/**
 * Hello world!
 *
 */
public interface BeancpSimpleCustomConverter<T,R> extends BeancpCustomConverter<T, R>
{
	R convert(BeancpFeature feature,BeancpContext context,T fromObj,Class<T> fromClass,R toObj,Class<R> toClass);
//    default <R,T> R convert(BeancpFeature feature,BeancpContext context,T fromObj,Class<T> fromClass,Class<R> toClass){
//        return convert(feature,context,fromObj,fromClass,BeancpInfo.of(toClass,toClass).defaultInstance(),toClass);
//    };
    default R convert(BeancpFeature feature,BeancpContext context,T fromObj,Type fromType,Class<T> fromClass,R toObj,Type toType,Class<R> toClass){
        return convert(feature,context,fromObj,fromClass,toObj,toClass);
    };
    
}
