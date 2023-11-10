package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFF extends BeancpInvocation<Float,Float> {
	float proceed(BeancpContext context,float fromObj, float toObj);
}