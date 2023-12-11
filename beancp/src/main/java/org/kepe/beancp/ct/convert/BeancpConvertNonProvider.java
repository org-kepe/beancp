package org.kepe.beancp.ct.convert;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.BeancpConvertProvider;
import org.kepe.beancp.exception.BeancpException;
import org.kepe.beancp.info.BeancpInfo;

/**
 * Hello world!
 *
 */
public class BeancpConvertNonProvider extends BeancpConvertProvider
{
	public final static BeancpConvertNonProvider INSTANCE=new BeancpConvertNonProvider();

	private BeancpConvertNonProvider() {
        super(null,null, null, null, null);
    }
    public BeancpConvertNonProvider(BeancpConvertProvider parent, BeancpFeature flag,
			BeancpInfo fromInfo, BeancpInfo toInfo) {
		super(parent, flag, null, fromInfo, toInfo);
	}
    
    
    @Override
    public <T,R> R convert(BeancpContext context,T fromObj, R toObj) {
    	if(this.fromInfo==null) {
    		throw new BeancpException("can not convert");
    	}else {
    		throw new BeancpException(new StringBuffer("can't convert from ").append(this.fromInfo.getBType().getTypeName()).append(" to ").append(this.toInfo.getBType().getTypeName()).toString());
    	}
    }
    @Override
	public int getDistance() {
		return -1;
	}
	
   
}
