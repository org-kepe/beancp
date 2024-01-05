package org.kepe.beancp.ct.converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.config.BeancpPropertyGetAndSet;
import org.kepe.beancp.ct.itf.BeancpCompareCreator;
import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.ct.itf.BeancpInfoRelMatcher;
import org.kepe.beancp.ct.itf.BeancpPropertyGetAndSetCreator;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpMapperPropertyInfo
{
    //private final static Map<Integer,Map<Integer,BeancpConverterInfo>> C_MAP=new ConcurrentHashMap<>();
    private BeancpPropertyGetAndSetCreator creater;
    private int priority;
    private BeancpInfoMatcher matcher;
    private Map<BeancpInfo,BeancpPropertyGetAndSet> C_MAP=new ConcurrentHashMap<>();
    public boolean matches(BeancpInfo info){
        return this.matcher.matches(info);
    }
    public BeancpPropertyGetAndSet getConverter(BeancpInfo info) {
    	return C_MAP.computeIfAbsent(info, key->creater.create(info));
    }

    public int getPriority() {
        return priority;
    }
   
    private BeancpMapperPropertyInfo(BeancpInfoMatcher matcher,BeancpPropertyGetAndSetCreator creater,int priority){
        this.matcher=matcher;
        this.creater=creater;
        this.priority=priority;
    }
    
    public static BeancpMapperPropertyInfo of(BeancpInfoMatcher matcher,BeancpPropertyGetAndSet converter,int priority){
        return new BeancpMapperPropertyInfo(matcher,(info)->converter ,priority);
    }
    
    public static BeancpMapperPropertyInfo of(BeancpInfoMatcher matcher,BeancpPropertyGetAndSetCreator creater,int priority){
        return new BeancpMapperPropertyInfo(matcher,creater,priority);
    }
    
}
