package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDZ extends BeancpInvocation {
	boolean proceed(BeancpContext context,double fromObj, boolean toObj);
	Class<Double> getFromClass();
	Class<Boolean> getToClass();
}