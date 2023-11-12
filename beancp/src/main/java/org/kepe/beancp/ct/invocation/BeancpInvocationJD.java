package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJD extends BeancpInvocation {
	double proceed(BeancpContext context,long fromObj, double toObj);
	Class<Long> getFromClass();
	Class<Double> getToClass();
}