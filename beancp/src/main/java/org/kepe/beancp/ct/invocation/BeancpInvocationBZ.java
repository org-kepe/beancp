package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,byte fromObj, boolean toObj);
	Class<Byte> getFromClass();
	Class<Boolean> getToClass();
}