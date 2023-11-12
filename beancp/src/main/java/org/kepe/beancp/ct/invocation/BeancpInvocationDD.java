package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDD extends BeancpInvocation {
	double proceed(BeancpContext context,double fromObj, double toObj);
	Class<Double> getFromClass();
	Class<Double> getToClass();
}