package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationJI extends BeancpInvocation<Long,Integer> {
	int proceed(BeancpContext context,long fromObj, int toObj);
}