package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBJ extends BeancpInvocation<Byte,Long> {
	long proceed(BeancpContext context,byte fromObj, long toObj);
}