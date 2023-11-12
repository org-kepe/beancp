package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBS extends BeancpInvocation {
	short proceed(BeancpContext context,byte fromObj, short toObj);
	Class<Byte> getFromClass();
	Class<Short> getToClass();
}