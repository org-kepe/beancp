package org.kepe.beancp.ct.convert;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public abstract class BeancpConvertASMProvider extends BeancpConvertProvider
{
    
    public BeancpConvertASMProvider(BeancpFeature flag, BeancpConverterInfo info, BeancpInfo fromInfo,
            BeancpInfo toInfo) {
        super(flag, info, fromInfo, toInfo);
    }
    
    @Override
	public <T,R> R convert(BeancpContext context,T fromObj, R toObj) {
		if(toObj==null){
            return convert0(context,fromObj);
        }else{
        	return convert0(context,fromObj,toObj);
        }
	}
	public abstract <T,R> R convert0(BeancpContext context,T fromObj);
	public abstract <T,R> R convert0(BeancpContext context,T fromObj, R toObj);

}
