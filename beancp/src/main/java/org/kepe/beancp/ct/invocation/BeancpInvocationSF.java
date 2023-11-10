package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSF extends BeancpInvocation<Short,Float> {
	float proceed(BeancpContext context,short fromObj, float toObj);
}