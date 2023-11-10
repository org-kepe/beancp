package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDD extends BeancpInvocation<Double,Double> {
	double proceed(BeancpContext context,double fromObj, double toObj);
}