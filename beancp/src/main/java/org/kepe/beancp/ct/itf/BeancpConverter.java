package org.kepe.beancp.ct.itf;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpFeature;

/**
 * Hello world!
 *
 */
public interface BeancpConverter
{
    default int distance(BeancpFeature flag,Class fromClass,Class toClass){
        return 1;
    }
    default int distance(BeancpFeature flag,Type fromType,Class fromClass,Type toType,Class toClass){
        return distance(flag,fromClass,toClass);
    }
   
}
