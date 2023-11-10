package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSD extends BeancpInvocation<Short,Double> {
	double proceed(BeancpContext context,short fromObj, double toObj);
}