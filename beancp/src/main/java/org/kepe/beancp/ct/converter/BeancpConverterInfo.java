package org.kepe.beancp.ct.converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.itf.BeancpConverter;
import org.kepe.beancp.ct.itf.BeancpConverterCreator;
import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.ct.itf.BeancpInfoRelMatcher;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConverterInfo
{
    //private final static Map<Integer,Map<Integer,BeancpConverterInfo>> C_MAP=new ConcurrentHashMap<>();
    private BeancpConverterCreator converter;
    private int priority;
    private BeancpInfoMatcher fromMatcher;
    private BeancpInfoMatcher toMatcher;
    private BeancpInfoRelMatcher relMatcher;
    private Map<BeancpFeature,Map<BeancpInfo,Map<BeancpInfo,BeancpConverter>>> C_MAP=new ConcurrentHashMap<>();
    public boolean matches(BeancpInfo fromInfo,BeancpInfo toInfo){
        return this.fromMatcher.matches(fromInfo)&&this.toMatcher.matches(toInfo)&&relMatcher.matches(fromInfo, toInfo);
    }
    public BeancpConverter getConverter(BeancpFeature feature,BeancpInfo fromInfo,BeancpInfo toInfo) {
    	return C_MAP.computeIfAbsent(feature, key->new ConcurrentHashMap<>()).computeIfAbsent(fromInfo, key->new ConcurrentHashMap<>()).computeIfAbsent(toInfo, key->converter.create(feature, fromInfo, toInfo));
    }

    public int getPriority() {
        return priority;
    }
    private BeancpConverterInfo(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpConverterCreator converter,int priority){
    	this(fromMatcher, toMatcher, (fromInfo,toInfo)->true, converter, priority);
    }
    private BeancpConverterInfo(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher,BeancpConverterCreator converter,int priority){
        this.toMatcher=toMatcher;
        this.fromMatcher=fromMatcher;
        this.converter=converter;
        this.priority=priority;
        this.relMatcher=relMatcher;
    }
    
    public static BeancpConverterInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpConverter converter,int priority){
        return new BeancpConverterInfo(fromMatcher,toMatcher,(feature,fromInfo,toInfo)->converter ,priority);
    }
    public static BeancpConverterInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher,BeancpConverter converter,int priority){
        return new BeancpConverterInfo(fromMatcher,toMatcher,relMatcher,(feature,fromInfo,toInfo)->converter,priority);
    }
    public static BeancpConverterInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpConverterCreator converter,int priority){
        return new BeancpConverterInfo(fromMatcher,toMatcher,converter,priority);
    }
    public static BeancpConverterInfo of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher,BeancpConverterCreator converter,int priority){
        return new BeancpConverterInfo(fromMatcher,toMatcher,relMatcher,converter,priority);
    }
}
