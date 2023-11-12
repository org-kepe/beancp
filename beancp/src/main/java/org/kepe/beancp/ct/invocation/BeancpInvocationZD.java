package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZD extends BeancpInvocation {
	double proceed(BeancpContext context,boolean fromObj, double toObj);
	Class<Boolean> getFromClass();
	Class<Double> getToClass();
}