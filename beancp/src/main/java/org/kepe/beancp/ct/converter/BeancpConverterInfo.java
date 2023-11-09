package org.kepe.beancp.ct.converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConverterInfo
{
    //private final static Map<Integer,Map<Integer,BeancpConverterInfo>> C_MAP=new ConcurrentHashMap<>();
    private BeancpConverter converter;
    private int priority;
    private BeancpInfoMatcher fromMatcher;
    private BeancpInfoMatcher toMatcher;

    public boolean matches(BeancpInfo fromInfo,BeancpInfo toInfo){
        return this.fromMatcher.matches(fromInfo)&&this.toMatcher.matches(toInfo);
    }
    public BeancpConverter getConverter() {
        return converter;
    }

    public int getPriority() {
        return priority;
    }

    private BeancpConverterInfo(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpConverter converter,int priority){
        this.toMatcher=toMatcher;
        this.fromMatcher=fromMatcher;
        this.converter=converter;
        this.priority=priority;
    }
    
    public static BeancpConverterInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpConverter converter,int priority){
        return new BeancpConverterInfo(fromMatcher,toMatcher,converter,priority);
    }

    
}
