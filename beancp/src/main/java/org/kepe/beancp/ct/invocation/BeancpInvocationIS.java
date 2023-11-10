package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationIS extends BeancpInvocation<Integer,Short> {
	short proceed(BeancpContext context,int fromObj, short toObj);
}