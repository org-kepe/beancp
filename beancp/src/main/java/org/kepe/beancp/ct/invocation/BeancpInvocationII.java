package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationII extends BeancpInvocation<Integer,Integer> {
	int proceed(BeancpContext context,int fromObj, int toObj);
}