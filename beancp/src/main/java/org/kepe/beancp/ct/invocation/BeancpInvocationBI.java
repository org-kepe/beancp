package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBI extends BeancpInvocation {
	int proceed(BeancpContext context,byte fromObj, int toObj);
	Class<Byte> getFromClass();
	Class<Integer> getToClass();
}