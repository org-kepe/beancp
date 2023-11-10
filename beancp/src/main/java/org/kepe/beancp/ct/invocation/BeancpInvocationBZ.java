package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationBZ extends BeancpInvocation<Byte,Boolean> {
	boolean proceed(BeancpContext context,byte fromObj, boolean toObj);
}