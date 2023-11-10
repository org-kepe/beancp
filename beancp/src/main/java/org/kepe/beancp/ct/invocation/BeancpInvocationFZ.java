package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFZ extends BeancpInvocation<Float,Boolean> {
	boolean proceed(BeancpContext context,float fromObj, boolean toObj);
}