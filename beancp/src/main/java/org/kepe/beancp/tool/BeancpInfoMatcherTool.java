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
            return info1.instanceOf(info);
        };
    }
    
    public static BeancpInfoMatcher createBeanMatcher() {
    	return info1->{
            return info1.isBean;
        };
    }
}