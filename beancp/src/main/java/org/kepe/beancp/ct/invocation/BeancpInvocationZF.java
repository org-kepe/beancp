package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZF extends BeancpInvocation<Boolean,Float> {
	float proceed(BeancpContext context,boolean fromObj, float toObj);
}