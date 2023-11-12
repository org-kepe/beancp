package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBJ extends BeancpInvocation {
	long proceed(BeancpContext context,byte fromObj, long toObj);
	Class<Byte> getFromClass();
	Class<Long> getToClass();
}