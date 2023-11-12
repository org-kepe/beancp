package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZF extends BeancpInvocation {
	float proceed(BeancpContext context,boolean fromObj, float toObj);
	Class<Boolean> getFromClass();
	Class<Float> getToClass();
}