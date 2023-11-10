package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDF extends BeancpInvocation<Double,Float> {
	float proceed(BeancpContext context,double fromObj, float toObj);
}