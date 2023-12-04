package org.kepe.beancp.tool;

import org.kepe.beancp.config.BeancpTypeMatcher;
import org.kepe.beancp.config.BeancpTypeRelMatcher;
import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.ct.itf.BeancpInfoRelMatcher;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpInfoMatcherTool
{
	
	public static BeancpInfoRelMatcher createRelMatcher(BeancpTypeRelMatcher typeRelMatcher){
        return (fromInfo,toInfo)->{
            return typeRelMatcher.matches(fromInfo.getBType(), fromInfo.getBClass(),toInfo.getBType(), toInfo.getBClass());
        };
    }
	public static BeancpInfoMatcher createMatcher(BeancpTypeMatcher typeMatcher){
        return info1->{
            return typeMatcher.matches(info1.getBType(), info1.getBClass());
        };
    }
	
    public static BeancpInfoMatcher createEqualMatcher(BeancpInfo info){
        return info1->{
            return info1.equals(info)||info1.getBType().equals(info.getBType());
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

