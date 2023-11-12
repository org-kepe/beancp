package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationOD<T> extends BeancpInvocation {
	double proceed(BeancpContext context,T fromObj, double toObj);
	Class<T> getFromClass();
	Class<Double> getToClass();
}