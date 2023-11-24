package org.kepe.beancp.ct.converter;

import org.kepe.beancp.ct.itf.BeancpInfoMatcher;
import org.kepe.beancp.ct.itf.BeancpInfoRelMatcher;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConverterInfoGroup
{
    //private final static Map<Integer,Map<Integer,BeancpConverterInfo>> C_MAP=new ConcurrentHashMap<>();
    
    private BeancpInfoMatcher fromMatcher;
    private BeancpInfoMatcher toMatcher;
    private BeancpInfoRelMatcher relMatcher;

    public boolean matches(BeancpInfo fromInfo,BeancpInfo toInfo){
        return this.fromMatcher.matches(fromInfo)&&this.toMatcher.matches(toInfo)&&relMatcher.matches(fromInfo, toInfo);
    }
    
    private BeancpConverterInfoGroup(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher){
    	this(fromMatcher, toMatcher, (fromInfo,toInfo)->true);
    }
    private BeancpConverterInfoGroup(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher){
        this.toMatcher=toMatcher;
        this.fromMatcher=fromMatcher;
        this.relMatcher=relMatcher;
    }
    
    public static BeancpConverterInfoGroup of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher){
        return new BeancpConverterInfoGroup(fromMatcher,toMatcher);
    }
    public static BeancpConverterInfoGroup of(BeancpInfoMatcher fromMatcher,BeancpInfoMatcher toMatcher,BeancpInfoRelMatcher relMatcher){
        return new BeancpConverterInfoGroup(fromMatcher,toMatcher,relMatcher);
    }
    
}
