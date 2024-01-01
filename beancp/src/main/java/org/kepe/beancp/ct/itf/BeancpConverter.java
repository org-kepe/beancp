package org.kepe.beancp.ct.itf;

import java.lang.reflect.Type;

import org.kepe.beancp.config.BeancpCompareFlag;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.invocation.BeancpInvocationOO;

/**
 * Hello world!
 *
 */
public interface BeancpConverter
{
    default int distance(BeancpFeature feature,Class fromClass,Class toClass){
        return 100;
    }
    default int distance(BeancpFeature feature,Type fromType,Class fromClass,Type toType,Class toClass){
        return distance(feature,fromClass,toClass);
    }
    
   
}
