package org.kepe.beancp.ct;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.config.BeancpFeature;
import org.kepe.beancp.ct.asm.BeancpInfoASMTool;
import org.kepe.beancp.ct.convert.BeancpConvertCustomProvider;
import org.kepe.beancp.ct.convert.BeancpConvertNonProvider;
import org.kepe.beancp.ct.converter.BeancpConverterInfo;
import org.kepe.beancp.ct.itf.BeancpASMConverter;
import org.kepe.beancp.ct.itf.BeancpCustomConverter;
import org.kepe.beancp.info.BeancpInfo;

public class BeancpConvertProviderProxy extends BeancpConvertProvider{
	

	private BeancpConvertProvider provider;
	protected BeancpConvertProviderProxy(BeancpConvertProvider provider) {
		this(null,provider.flag,provider.info,provider.fromInfo,provider.toInfo);
		this.provider=provider;
	}
	public BeancpConvertProviderProxy(BeancpConvertProvider parent, BeancpFeature flag, BeancpConverterInfo info,
			BeancpInfo fromInfo, BeancpInfo toInfo) {
		super(parent, flag, info, fromInfo, toInfo);
	}

	@Override
	public <T, R> R convert(BeancpContext context, T fromObj, R toObj) {
		if(this.getDistance()>=0) {
			return provider.convert(context, fromObj, toObj);
		}
		return null;
	}
	
	

}
