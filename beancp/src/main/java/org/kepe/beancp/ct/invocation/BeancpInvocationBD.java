package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBD extends BeancpInvocation {
	double proceed(BeancpContext context,byte fromObj, double toObj);
	Class<Byte> getFromClass();
	Class<Double> getToClass();
}