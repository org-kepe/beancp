package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFD extends BeancpInvocation<Float,Double> {
	double proceed(BeancpContext context,float fromObj, double toObj);
}