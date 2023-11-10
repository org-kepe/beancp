package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBI extends BeancpInvocation<Byte,Integer> {
	int proceed(BeancpContext context,byte fromObj, int toObj);
}