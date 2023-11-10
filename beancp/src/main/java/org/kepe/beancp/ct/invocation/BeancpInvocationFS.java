package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationFS extends BeancpInvocation<Float,Short> {
	short proceed(BeancpContext context,float fromObj, short toObj);
}