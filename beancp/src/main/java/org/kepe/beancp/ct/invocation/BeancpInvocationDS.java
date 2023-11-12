package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDS extends BeancpInvocation {
	short proceed(BeancpContext context,double fromObj, short toObj);
	Class<Double> getFromClass();
	Class<Short> getToClass();
}