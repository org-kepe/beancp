package org.kepe.beancp.ct.invocation;

import org.kepe.beancp.config.BeancpContext;

public interface BeancpInvocationSS extends BeancpInvocation<Short,Short> {
	short proceed(BeancpContext context,short fromObj, short toObj);
}