package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDZ extends BeancpInvocation<Double,Boolean> {
	boolean proceed(BeancpContext context,double fromObj, boolean toObj);
}