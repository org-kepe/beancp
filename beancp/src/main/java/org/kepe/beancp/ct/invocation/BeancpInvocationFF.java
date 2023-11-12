package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFF extends BeancpInvocation {
	float proceed(BeancpContext context,float fromObj, float toObj);
	Class<Float> getFromClass();
	Class<Float> getToClass();
}