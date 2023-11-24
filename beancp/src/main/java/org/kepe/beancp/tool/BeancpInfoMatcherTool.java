package org.kepe.beancp.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpInfoMatcherTool
{

    public static BeancpInfoMatcher createEqualMatcher(BeancpInfo info){
        return info1->{
            return info1.equals(info);
        };
    }

    public static BeancpInfoMatcher createExtendsMatcher(BeancpInfo info){
        return info1->{
            return info1.instanceOf(info.getBClass());
        };
    }
    public static BeancpInfoMatcher createEnumMatcher(){
        return info1->{
            return info1.isEnum;
        };
    }
    public static BeancpInfoMatcher createArrayMatcher(){
        return info1->{
            return info1.isArray;
        };
    }
    public static BeancpInfoMatcher createSuperMatcher(BeancpInfo info){
        return info1->{
            return info1.getBClass().isAssignableFrom(info.getBClass());
        };
    }
    
    public static BeancpInfoMatcher createBeanMatcher() {
    	return info1->{
            return info1.isBean;
        };
    }
    public static BeancpInfoMatcher createAnyMatcher() {
    	return info1->{
            return true;
        };
    }
}

