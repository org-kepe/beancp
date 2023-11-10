package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationID extends BeancpInvocation<Integer,Double> {
	double proceed(BeancpContext context,int fromObj, double toObj);
}