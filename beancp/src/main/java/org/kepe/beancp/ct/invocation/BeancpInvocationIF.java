package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIF extends BeancpInvocation<Integer,Float> {
	float proceed(BeancpContext context,int fromObj, float toObj);
}