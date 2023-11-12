package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBF extends BeancpInvocation {
	float proceed(BeancpContext context,byte fromObj, float toObj);
	Class<Byte> getFromClass();
	Class<Float> getToClass();
}