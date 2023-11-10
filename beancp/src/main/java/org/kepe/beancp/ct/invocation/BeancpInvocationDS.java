package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationDS extends BeancpInvocation<Double,Short> {
	short proceed(BeancpContext context,double fromObj, short toObj);
}