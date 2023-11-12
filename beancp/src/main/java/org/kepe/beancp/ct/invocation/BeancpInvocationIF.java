package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIF extends BeancpInvocation {
	float proceed(BeancpContext context,int fromObj, float toObj);
	Class<Integer> getFromClass();
	Class<Float> getToClass();
}