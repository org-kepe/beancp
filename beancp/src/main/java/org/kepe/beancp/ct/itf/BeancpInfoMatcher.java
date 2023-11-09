package org.kepe.beancp.ct.itf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public interface BeancpInfoMatcher
{
    public boolean matches(BeancpInfo Info);
    
}
