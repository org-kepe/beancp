package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationID extends BeancpInvocation {
	double proceed(BeancpContext context,int fromObj, double toObj);
	Class<Integer> getFromClass();
	Class<Double> getToClass();
}