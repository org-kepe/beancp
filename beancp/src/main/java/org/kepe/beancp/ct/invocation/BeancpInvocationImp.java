package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;
import org.kepe.beancp.ct.BeancpConvertProvider;

public class BeancpInvocationImp implements BeancpInvocationOI,BeancpInvocationOO {
	private BeancpConvertProvider provider;
	public BeancpInvocationImp(BeancpConvertProvider provider) {
		this.provider=provider;
	}
	@Override
	public int proceed(BeancpContext context, Object fromObj, int toObj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object proceed(BeancpContext context, Object fromObj, Object toObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getFromClass() {
		// TODO Auto-generated method stub
		return null;
	}
	 
}

