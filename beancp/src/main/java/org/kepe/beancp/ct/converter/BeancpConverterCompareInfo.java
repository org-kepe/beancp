package org.kepe.beancp.ct.converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpCompare;
import org.kepe.beancp.ct.itf.BeancpCompareCreator;
import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.ct.itf.BeancpInfoRelMatcher;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConverterCompareInfo
{
    //private final static Map<Integer,Map<Integer,BeancpConverterInfo>> C_MAP=new ConcurrentHashMap<>();
    private BeancpCompareCreator converter;
    private int priority;
    private BeancpInfoMatcher fromMatcher;
    private BeancpInfoMatcher toMatcher;
    private BeancpInfoRelMatcher relMatcher;
    private Map<BeancpInfo,Map<BeancpInfo,BeancpCompare>> C_MAP=new ConcurrentHashMap<>();
    public boolean matches(BeancpInfo fromInfo,BeancpInfo toInfo){
        return this.fromMatcher.matches(fromInfo)&&this.toMatcher.matches(toInfo)&&relMatcher.matches(fromInfo, toInfo);
    }
    public BeancpCompare getConverter(BeancpInfo fromInfo,BeancpInfo toInfo) {
    	return C_MAP.computeIfAbsent(fromInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(toInfo, key->converter.create( fromInfo, toInfo));
    }

    public int getPriority() {
        return priority;
    }
    private BeancpConverterCompareInfo(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpCompareCreator converter,int priority){
    	this(fromMatcher, toMatcher, (fromInfo,toInfo)->true, converter, priority);
    }
    private BeancpConverterCompareInfo(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher,BeancpCompareCreator converter,int priority){
        this.toMatcher=toMatcher;
        this.fromMatcher=fromMatcher;
        this.converter=converter;
        this.priority=priority;
        this.relMatcher=relMatcher;
    }
    
    public static BeancpConverterCompareInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpCompare converter,int priority){
        return new BeancpConverterCompareInfo(fromMatcher,toMatcher,(fromInfo,toInfo)->converter ,priority);
    }
    public static BeancpConverterCompareInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher,BeancpCompare converter,int priority){
        return new BeancpConverterCompareInfo(fromMatcher,toMatcher,relMatcher,(fromInfo,toInfo)->converter,priority);
    }
    public static BeancpConverterCompareInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpCompareCreator converter,int priority){
        return new BeancpConverterCompareInfo(fromMatcher,toMatcher,converter,priority);
    }
    public static BeancpConverterCompareInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher,BeancpCompareCreator converter,int priority){
        return new BeancpConverterCompareInfo(fromMatcher,toMatcher,relMatcher,converter,priority);
    }
}
