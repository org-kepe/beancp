package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOF<T> extends BeancpInvocation<T,Float> {
	float proceed(BeancpContext context,T fromObj, float toObj);
}