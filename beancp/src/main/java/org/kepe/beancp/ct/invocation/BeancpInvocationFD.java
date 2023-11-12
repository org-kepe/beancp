package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFD extends BeancpInvocation {
	double proceed(BeancpContext context,float fromObj, double toObj);
	Class<Float> getFromClass();
	Class<Double> getToClass();
}