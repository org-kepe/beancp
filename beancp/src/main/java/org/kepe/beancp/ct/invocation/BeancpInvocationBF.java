package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBF extends BeancpInvocation<Byte,Float> {
	float proceed(BeancpContext context,byte fromObj, float toObj);
}