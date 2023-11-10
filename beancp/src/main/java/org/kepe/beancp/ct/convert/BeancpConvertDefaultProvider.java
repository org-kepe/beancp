package org.kepe.beancp.ct.convert;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConvertDefaultProvider extends BeancpConvertProvider
{
    
    public BeancpConvertDefaultProvider(BeancpConvertProvider parent,BeancpFeature flag, BeancpInfo fromInfo,
            BeancpInfo toInfo) {
        super(parent,flag, null, fromInfo, toInfo);
    }
    @Override
    public <T,R> R convert(BeancpContext context,T fromObj, R toObj) {
    	return null;
    }
    @Override
	public int getDistance() {
		return -1;
	}
   
}
