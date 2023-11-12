package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSF extends BeancpInvocation {
	float proceed(BeancpContext context,short fromObj, float toObj);
	Class<Short> getFromClass();
	Class<Float> getToClass();
}