package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationZD extends BeancpInvocation<Boolean,Double> {
	double proceed(BeancpContext context,boolean fromObj, double toObj);
}