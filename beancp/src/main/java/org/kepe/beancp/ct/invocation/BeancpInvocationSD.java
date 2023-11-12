package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSD extends BeancpInvocation {
	double proceed(BeancpContext context,short fromObj, double toObj);
	Class<Short> getFromClass();
	Class<Double> getToClass();
}